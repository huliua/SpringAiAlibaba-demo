package com.huliua.tool;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * WeatherTools
 *
 * @author tigerl
 * @version 1.0
 **/
public class WeatherTools {

    @Tool(description = "获取天气信息的工具")
    public String getWeather(@ToolParam(description = "查询天气的城市") String city,
                             @ToolParam(description = "需要查询的日期，格式为yyyy-MM-dd") String date,
                             ToolContext toolContext) {
        return "日期: " + date + " 城市:" + city + " 天气：多云转晴 15～28℃ (" + toolContext.getContext().get("version") + ")";
    }

    @Tool(description = "获取当前用户城市的工具")
    public String getCity(ToolContext toolContext) {
        Object userId = toolContext.getContext().getOrDefault("user_id", null);
        if (userId == null) {
            return "日本";
        }
        return "南京";
    }

    @Tool(description = "获取当前日期的工具")
    public String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
