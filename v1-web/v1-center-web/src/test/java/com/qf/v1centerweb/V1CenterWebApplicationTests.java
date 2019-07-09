package com.qf.v1centerweb;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V1CenterWebApplicationTests {

    @Autowired
    private FastFileStorageClient client;

    @Test
    public void uploadTest() throws FileNotFoundException {
        File file = new File("D:\\ideaworkspace\\v1\\v1-web\\v1-center-web\\1.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
//        StorePath storePath = client.uploadFile(fileInputStream, file.length(), "html", null);

        StorePath storePath = client.uploadImageAndCrtThumbImage(fileInputStream, file.length(), "jpg", null);
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getGroup());
        System.out.println(storePath.getPath());
    }

    @Test
    public void delTest(){
        client.deleteFile("group1/M00/00/00/CiSTUV0CLZeAcQdfAAAxTXTZaIs487_100x100.jpg");
        System.out.println("删除成功");
    }

}
