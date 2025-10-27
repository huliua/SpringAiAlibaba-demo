package com.huliua.springaialibabademo.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/doc")
public class DocumentController {

    @jakarta.annotation.Resource
    private VectorStore pgVectorStore;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    // 上传文件并嵌入数据库
    @RequestMapping("/upload")
    @ResponseBody
    public void upload(MultipartFile file) {
        Resource resource = new InputStreamResource(file);
        List<Document> documents = new TokenTextSplitter().transform(new TextReader(resource).read());
        pgVectorStore.add(documents);
    }

}
