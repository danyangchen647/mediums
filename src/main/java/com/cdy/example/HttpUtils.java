package com.cdy.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class HttpUtils {
        /**
         * httpClient的get请求方式
         * 使用GetMethod来访问一个URL对应的网页实现步骤：
         * 1.生成一个HttpClient对象并设置相应的参数；
         * 2.生成一个GetMethod对象并设置响应的参数；
         * 3.用HttpClient生成的对象来执行GetMethod生成的Get方法；
         * 4.处理响应状态码；
         * 5.若响应正常，处理HTTP响应内容；
         * 6.释放连接。
         *
         * @param url
         * @return
         */
        public static CloseableHttpResponse doPost(String url, String params)  {
            //1.生成HttpClient对象并设置参数
            HttpClient httpClient = HttpClients.createDefault();
            //2.生成GetMethod对象并设置参数
            HttpPost httpPost = new HttpPost(url);
            //设置get请求超时为5秒
            StringEntity s = new StringEntity(params,"utf-8");
            //设置请求重试处理，用的是默认的重试处理：请求三次
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
            httpPost.setEntity(s);
            httpPost.setHeader("content-type","application/json");
           //3.执行HTTP GET 请求
            CloseableHttpResponse response = null;
            try {
                response = (CloseableHttpResponse) httpClient.execute(httpPost);
            } catch (IOException e) {
                //发生网络异常
                System.out.println("发生网络异常!");
            } finally {
                //6.释放连接
                httpPost.releaseConnection();
            }
            return response;
        }

        /**
         * post请求
         *
         * @param url
         * @return
         */
        public static HttpResponse doGet(String url, String params) {
            //1.生成HttpClient对象并设置参数
            HttpClient httpClient = HttpClients.createDefault();
            //2.生成GetMethod对象并设置参数
            HttpGet httpGet = new HttpGet(url);
            StringEntity s = new StringEntity(params,"utf-8");
            HttpResponse response = null;
            String html = "";
            try {
                response =  httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                html = EntityUtils.toString(entity,"utf-8");
                response.setHeader("html",html);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpGet.releaseConnection();
            }
            return response;
        }



    public static void httpOtherGet(String url, String params){
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            String body = EntityUtils.toString(entity);
            System.out.println(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendHttpPost(String url, String JSONBody) throws Exception {
        System.out.println(JSONBody);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(JSONBody));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        httpClient.close();
        return responseContent;
    }

}




