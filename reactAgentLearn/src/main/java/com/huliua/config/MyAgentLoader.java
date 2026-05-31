package com.huliua.config;

import com.alibaba.cloud.ai.agent.studio.loader.AgentLoader;
import com.alibaba.cloud.ai.graph.agent.Agent;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * MyAgentLoader
 *
 * @author tigerl
 * @version 1.0
 * @datetime 2026/5/31 15:41
 **/
@Component
public class MyAgentLoader implements AgentLoader {

    @Autowired
    private Map<String, ReactAgent> reactAgentMap;


    @Override
    public @NonNull List<String> listAgents() {
        return reactAgentMap.keySet().stream().toList();
    }

    @Override
    public Agent loadAgent(String name) {
        return reactAgentMap.getOrDefault(name, reactAgentMap.get(reactAgentMap.keySet().stream().findFirst().get()));
    }
}
