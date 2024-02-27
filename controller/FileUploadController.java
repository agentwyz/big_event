package com.fnmain.controller;

import com.fnmain.pojo.Result;
import com.fnmain.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        //把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();

        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        String url = AliOssUtil.upload(filename, file.getInputStream());

        return Result.success(url);
    }
}
