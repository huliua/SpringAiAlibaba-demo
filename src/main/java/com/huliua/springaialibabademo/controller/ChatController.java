package com.huliua.springaialibabademo.controller;

import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatController {

    private final ChatClient chatClient;

    @RequestMapping("/chat")
    public String chat(String message) {
        return chatClient.prompt(message).call().content();
    }
}
