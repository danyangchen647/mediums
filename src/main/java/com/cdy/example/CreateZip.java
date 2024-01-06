package com.cdy.example;
import com.cdy.example.entity.Information;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by 星尘 on 2024/1/6.
 */

public class CreateZip {
        public  void addFile(List<Information> informationList) {
            String zipFileName = "medium.zip";
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName))) {
                // 添加文件到压缩包
                for (int i =0;i< informationList.size();i++) {
                        informationList.get(i).setRef(informationList.get(i).getRef().replace(":",""));
                        addToZipFile(informationList.get(i).getRef() + ".pdf", zipOutputStream);
                    }
                System.out.println("Files compressed successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        private  void addToZipFile(String fileName, ZipOutputStream zipOutputStream) throws IOException {
            // 创建ZipEntry对象并设置文件名
            ZipEntry entry = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(entry);
            // 读取文件内容并写入Zip文件
            try (FileInputStream fileInputStream = new FileInputStream("medium.zip")) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    zipOutputStream.write(buffer, 0, bytesRead);
                }
            }
            // 完成当前文件的压缩
            zipOutputStream.closeEntry();
        }
    }
