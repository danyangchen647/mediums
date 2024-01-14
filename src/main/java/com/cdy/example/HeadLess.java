package com.cdy.example;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by 星尘 on 2024/1/13.
 */
public class HeadLess {
    public static void main(String[] args) throws Exception {
        // 设置Chrome浏览器的无头模式选项
        ChromeOptions options = new ChromeOptions();
//        options.addArgument("--headless");
//        options.addArgument("--no-sandbox"); // （可选）在Windows上使用沙盒时需要此选项
//        options.addArgument("--disable-dev-shm-usage"); // （可选）在Linux上使用沙盒时需要此选项

        // 创建ChromeDriver实例
        System.setProperty("webdriver.chrome.driver", "C:\\work software\\chromedriver_win32\\chromedriver.exe"); // 替换为你的chromedriver路径
        options.addExtensions(new File("chrome-immersive-translate-1.1.4.zip"));
        ChromeDriver driver = new ChromeDriver(options);

        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "9999");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort",  "9999");

        // 打开一个网页
        driver.get("https://medium.com/tag/software-engineering/archive");
        // 定位到特定标签
//        List<WebElement> elementsByCssSelector = driver.findElementsByCssSelector("#root > div > div > div > div > div > div > div > div > div");
        System.out.println("等待======");
        //等待操作
//        Thread.sleep(10000);
        List<WebElement> article = driver.findElementsByTagName("article");
        for (int i = 0; i < article.size() ; i++) {
            article.get(i).click();
            // 创建 Action 对象并模拟右键点击操作
            pringPdf(driver);

        }



        // 在这里可以执行其他操作，例如填写表单、点击元素等
        System.out.println(driver);
        // 关闭浏览器
//        driver.quit();
    }


    public static void  pringPdf(ChromeDriver driver) throws Exception{

        // 将 HTML 内容保存为临时文件
        Path tempHtmlPath = Files.createTempFile("temp_html", ".html");
        Files.write(tempHtmlPath, driver.getPageSource().getBytes());

        // 使用 wkhtmltopdf 将临时 HTML 文件转换为 PDF 文件
        String wkhtmltopdfPath = "C:\\work software\\wkhtmltopdf\\bin\\wkhtmltopdf.exe"; // 替换为 wkhtmltopdf 的实际路径
        Process process = Runtime.getRuntime().exec(wkhtmltopdfPath + " " + tempHtmlPath + " output.pdf");
        process.waitFor(5000L, TimeUnit.MILLISECONDS);
        // 将生成的 PDF 文件保存到本地文件系统中的指定位置
        Path pdfPath = Paths.get("output.pdf"); // 替换为 PDF 文件的实际路径
        Files.move(tempHtmlPath, pdfPath);
    }

}

