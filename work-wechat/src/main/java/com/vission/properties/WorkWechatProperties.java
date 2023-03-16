package com.vission.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author vission
 * @date 2023/2/18 9:37 下午
 * @description
 */
@Data
@ConfigurationProperties("work-wechat")
public class WorkWechatProperties {

    // 设置微信企业号应用的token
    private String token;
    // 设置微信企业号的appid
    private String corpID;
    // 设置微信企业号的app corpSecret
    private String corpSecret;
    // 设置微信企业号应用ID
    private Integer agentId;
    private String encodingAESKey;

}
