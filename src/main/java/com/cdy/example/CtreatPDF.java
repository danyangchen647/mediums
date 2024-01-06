package com.cdy.example;

import com.cdy.example.entity.Information;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by 星尘 on 2024/1/6.
 */
public class CtreatPDF {


    public  void getPDF(Information information) throws Exception{
        //创建文档对象
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        //设置输出流
        information.setRef(information.getRef().replace(":",""));
        File file = new File(information.getRef()+".pdf");
        System.out.println(file.createNewFile());
        PdfWriter.getInstance(document, new FileOutputStream(information.getRef()+".pdf"));
        //打开文档
        document.open();
        //向文档中添加内容
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk(information.getTitle()));
        paragraph.add(new Chunk(information.getContext()));
        document.add(paragraph);
        //关闭文档
        document.close();
    }

    public static void main(String[] args) throws Exception {
        Information information = new Information();
        information.setContext("1111sdf");
        information.setTitle("adfadsf");
        CtreatPDF ctreatPDF = new CtreatPDF();
        ctreatPDF.getPDF(information);
    }

}
