package com.huliua.springaialibabademo.config;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Resource
    private VectorStore pgVectorStore;

    private static final String PROMPT = """
            你是一个就职于金智教育的程序员，名字叫小弱智。你可以帮助用户解决JAVA、VUE、JS、CSS等技术上的问题。请以严谨、专业且准确的回答帮助用户解决问题！
            """;

    /**
     * 构建聊天内存对象（存储在数据库中）
     */
    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatModel zhiPuAiChatModel, ChatMemory chatMemory) {
        Advisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .similarityThreshold(0.50)
                        .topK(6)
                        .vectorStore(pgVectorStore)
                        .build())
                .queryAugmenter(ContextualQueryAugmenter.builder()
                        // 允许检索到的上下文为空
                        .allowEmptyContext(true)
                        .build())
                .build();
        // 构建ChatClient
        return ChatClient.builder(zhiPuAiChatModel)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(), // 日志
                        MessageChatMemoryAdvisor.builder(chatMemory).build(), // 历史消息
                        retrievalAugmentationAdvisor // RAG
                )
                .defaultSystem(PROMPT)
                .build();
    }
}
