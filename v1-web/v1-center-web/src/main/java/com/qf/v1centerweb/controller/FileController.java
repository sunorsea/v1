package com.qf.v1centerweb.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1centerweb.pojo.WangeditorResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/13
 */
@Controller
@RequestMapping("file")
public class FileController {

    @Value("${image.server}")
    private String imageServer;

    @Autowired
    private FastFileStorageClient client;

    @PostMapping("upload")
    @ResponseBody
    public ResultBean upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        try {
            StorePath storePath = client.uploadFile(file.getInputStream(), file.getSize(), extName, null);
            String path=new StringBuilder(imageServer).append(storePath.getFullPath()).toString();
            return new ResultBean("200", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultBean("404", "失败了,别问");
    }

    @PostMapping("multiUpload")
    @ResponseBody
    public WangeditorResultBean multiUpload(MultipartFile[] files){
        System.out.println(files);
        String[] data = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            String originalFilename = files[i].getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            try {
                StorePath storePath = client.uploadFile(files[i].getInputStream(), files[i].getSize(), extName, null);
                String path=new StringBuilder(imageServer).append(storePath.getFullPath()).toString();
                data[i] = path;
            } catch (IOException e) {
                e.printStackTrace();
                return new WangeditorResultBean("1", null);
            }
        }
        return new WangeditorResultBean("0", data);
    }

}
