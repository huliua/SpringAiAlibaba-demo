package com.huliua.springaialibabademo.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Resource
    private ChatModel zhiPuAiChatModel;

    @RequestMapping("/chat")
    public Flux<String> chat(String message) {
        return zhiPuAiChatModel.stream(message);
    }
}
