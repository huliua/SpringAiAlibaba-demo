package com.huliua.springaialibabademo.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Resource
    private ChatClient chatClient;

    @RequestMapping("/chat")
    public Flux<String> chat(String message) {
        return chatClient.prompt(message).stream().content();
    }
}
