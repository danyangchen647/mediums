package com.cdy.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdy.example.entity.Information;
import com.cdy.example.entity.Translate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.*;
import java.security.MessageDigest;
/**
 * Created by 星尘 on 2024/1/6.
 */
@RestController
public class Crawler {

    private static final String   secret = "rR7wNOnTVYP2c1yqv_NN";
    @RequestMapping("/getArticle")
    public String getArticle() throws Exception{
        //设置代理
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "10001");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort",  "10001");
        String url = "https://medium.com/tag/software-engineering/archive";
        startPaChong(url);
        return "OK";
}


    //开始爬虫
    public static void startPaChong(String url) throws  Exception{
            Connection connection = Jsoup.connect(url).timeout(50000);
            connection.request().header("User-Agent","PostmanRuntime/7.26.8");
            Document document = connection.get();
            String data = document.childNodes().get(1).childNodes().get(1).childNodes().get(4).childNodes().get(0).attributes().get("data");
            //取json
            data = data.replace("window.__APOLLO_STATE__","").trim();
            data = data.replace("=","");
            JSONObject  jsonObject = JSON.parseObject(data);
            Object var1 = jsonObject.get("Tag:software-engineering");
            JSONObject var2 = JSON.parseObject(JSON.toJSONString(var1));
            Object var3 = var2.get("posts:{\"sortOrder\":\"MOST_READ\",\"timeRange\":{\"kind\":\"ALL_TIME\"}}");
            JSONArray var4 =  JSONArray.parseArray(JSON.toJSONString(JSON.parseObject(JSON.toJSONString(var3)).get("edges")));
            List<Information> informationList = new ArrayList<>();
            for (int i =0 ;i< var4.size();i++){
                Information information = new Information();
                Object var5 = JSONObject.parseObject(JSONObject.toJSONString(var4.get(i))).get("node");
                Object var6 = JSONObject.parseObject(JSONObject.toJSONString(var5)).get("__ref");
                String ref = (String) var6;
                //拿取其他信息
                String mediumUrl = String.valueOf(JSONObject.parseObject(JSONObject.toJSONString(jsonObject.get(ref))).get("mediumUrl"));
                String clap = String.valueOf(JSONObject.parseObject(JSONObject.toJSONString(jsonObject.get(ref))).get("clapCount"));
                information.setRef(ref);
                information.setClap(clap);
                information.setUrl(mediumUrl);
                informationList.add(information);
            }


            //爬取文章
            for (int i = 0;i< informationList.size();i++) {
                getNextImage(informationList.get(i));

                //翻译标题
                String translate_url = "https://fanyi-api.baidu.com/api/trans/vip/translate";
                Translate translate = new Translate();
                translate.setQ(informationList.get(i).getTitle());
                translate.setSign(MD5Utils.MD5(translate.getAppid()+ translate.getQ()+translate.getSalt()+secret));
                String params = JSONObject.toJSONString(translate);
                String trans =  HttpUtils.sendHttpPost(translate_url, params);
//                informationList.get(i).setTitle(trans);
                //翻译正文
                translate.setQ(informationList.get(i).getContext());
                translate.setSign(MD5Utils.MD5(translate.getAppid()+ translate.getQ()+translate.getSalt()+secret));
                params = JSONObject.toJSONString(translate);
                trans =  HttpUtils.sendHttpPost(translate_url, params);
//                informationList.get(i).setContext(trans);
            }

            //生成pdf文件
            for (int i =0;i<informationList.size();i++){
                if (informationList.get(i).getTitle() == null){
                    continue;
                }
                 CtreatPDF pdf = new CtreatPDF();
                 pdf.getPDF(informationList.get(i));
            }

            // 打包
            CreateZip createZip = new CreateZip();
            createZip.addFile(informationList);


    }






    //爬取英文文章
    public static   void  getNextImage(Information information){
        try {
            Connection connection = Jsoup.connect(information.getUrl()).timeout(50000);
            connection.request().header("User-Agent","PostmanRuntime/7.26.8");
            Document document = connection.get();
            Elements article = document.select("article");
            Elements p = article.select("p");
            String span = "";
            for (int i = 0; i < p.size(); i++) {
                span = span + p.get(i).text();
            }
            information.setContext(span);
            Elements title = document.select("title");
            for (int i = 0; i < title.size(); i++) {
                System.out.println(title.get(i).text());
                information.setTitle(title.get(i).text());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        //翻译
        String translate_url = "https://fanyi-api.baidu.com/api/trans/vip/translate";
        Translate translate = new Translate();
        translate.setQ("apple");
        String input = translate.getAppid()+ translate.getQ()+translate.getSalt()+translate.getSecret();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(input.getBytes("UTF-8"));
        StringBuilder sign = new StringBuilder();
        for (byte b : bytes) {
            sign.append(String.format("%02x", b & 0xff));
        }
        translate.setSign(sign.toString());
        String params = JSONObject.toJSONString(translate);
        String trans =  HttpUtils.sendHttpPost(translate_url, params);
        System.out.println(trans);

    }
}
