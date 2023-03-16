package com.vission.chatGPT.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "chat-gpt")
public class ChatGPTProperties {

    /**
     * openAPI-key
     */
    private String apiKey;
    /**
     * okhttp 代理 hostName 默认为null
     */
    private String proxyHostName;

    /**
     * okhttp 代理  端口 默认为null
     */
    private Integer proxyPort;

    /**
     * 是否开启OkHTTP的日志
     */
    private Boolean log;
    /**
     * 连续对话次数
     */
    private Integer chatGptFlowNum = 5;

}
