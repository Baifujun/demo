package com.baixs.demo.controller;

import com.baixs.demo.mongo.dao.DemoDao;
import com.baixs.demo.mongo.document.DemoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
public class MongodbController {
    @Autowired
    private DemoDao demoDao;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    @GetMapping("/mongodb/save")
    public String save() {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setId(1L);
        demoEntity.setTitle("Spring Boot 中使用 MongoDB");
        demoEntity.setDescription("关注公众号，搜云库，专注于开发技术的研究与知识分享");
        demoEntity.setBy("souyunku");
        demoEntity.setUrl("http://www.souyunku.com");
        demoDao.saveDemo(demoEntity);
        demoEntity = new DemoEntity();
        demoEntity.setId(2L);
        demoEntity.setTitle("Spring Boot 中使用 MongoDB");
        demoEntity.setDescription("关注公众号，搜云库，专注于开发技术的研究与知识分享");
        demoEntity.setBy("souyunku");
        demoEntity.setUrl("http://www.souyunku.com");
        demoDao.saveDemo(demoEntity);
        return "ok";
    }

    @GetMapping("/mongodb/remove")
    public String remove() {
        demoDao.removeDemo(2L);
        return "ok";
    }

    @GetMapping("/mongodb/update")
    public String update() {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setId(1L);
        demoEntity.setTitle("Spring Boot 中使用 MongoDB 更新数据");
        demoEntity.setDescription("关注公众号，搜云库，专注于开发技术的研究与知识分享");
        demoEntity.setBy("souyunku");
        demoEntity.setUrl("http://www.souyunku.com");
        demoDao.updateDemo(demoEntity);
        return "ok";
    }

    @GetMapping("/mongodb/find")
    public String find() throws JsonProcessingException {
        DemoEntity demoEntity = demoDao.findDemoById(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(demoEntity));
        return "ok";
    }

    @PostMapping("/mongodb/uploadFile")
    public String uploadFile(MultipartFile file) throws IOException {
        Document document = new Document("from", "web上传");
        gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), document);
        return "ok";
    }

    @GetMapping(value = "/mongodb/downloadFile/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] downloadFile(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
        GridFSFindIterable gridFSFindIterable = gridFsTemplate.find(new Query().addCriteria(Criteria.where("filename").is(name)));
        GridFSFile gridFSFile = gridFSFindIterable.first();
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(gridFSFile.getFilename(), "UTF-8"));
        return IOUtils.toByteArray(gridFsResource.getInputStream());
    }
}
