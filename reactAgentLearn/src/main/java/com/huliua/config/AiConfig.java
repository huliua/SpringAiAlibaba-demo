package com.huliua.config;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.hook.hip.HumanInTheLoopHook;
import com.alibaba.cloud.ai.graph.agent.hook.hip.ToolConfig;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import com.huliua.tool.WeatherTools;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AiConfig {

    @Bean
    public ReactAgent reactAgent(OllamaChatModel chatModel) {
        HumanInTheLoopHook humanInTheLoopHook = HumanInTheLoopHook.builder()
                .approvalOn("getCity", ToolConfig.builder()
                        .description("是否允许获取城市")
                        .build())
                .approvalOn("getCurrentDate", ToolConfig.builder()
                        .description("是否允许获取当前日期")
                        .build())
                .build();
        return ReactAgent
                .builder()
                .name("ollama")
                .saver(new MemorySaver())
                .model(chatModel)
                .methodTools(new WeatherTools())
                .hooks(humanInTheLoopHook)
                .toolContext(Map.of("version", "1.0.0"))
                .build();
    }
}
