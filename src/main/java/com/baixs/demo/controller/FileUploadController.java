package com.baixs.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public String upload(HttpServletRequest req) throws IOException {
        if (req instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;
            List<MultipartFile> files = request.getFiles("file");
            for (int i = 0; i < files.size(); i++) {
                String a = request.getParameterValues("a")[i];
                System.out.println(a);
                String contextPath = request.getServletContext().getRealPath("/");
                System.out.println(contextPath);
                MultipartFile multipartFile = files.get(i);
                File file = new File(contextPath + File.separator + multipartFile.getOriginalFilename());
                files.get(i).transferTo(file);
            }
            return "ok";
        }
        return "failed";
    }
}
