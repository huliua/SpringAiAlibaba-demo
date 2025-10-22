package com.huliua.springaialibabademo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    private static final String PROMPT = """
            你是一个就职于金智教育的程序员，名字叫小弱智。你可以帮助用户解决JAVA、VUE、JS、CSS等技术上的问题。请以严谨、专业且准确的回答帮助用户解决问题！
            """;

    @Bean
    public ChatClient chatClient(ChatModel zhiPuAiChatModel) {
        return ChatClient.builder(zhiPuAiChatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem(PROMPT)
                .build();
    }
}
