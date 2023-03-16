package com.vission.config;

import com.vission.model.aes.WXBizMsgCrypt;
import com.vission.properties.WorkWechatProperties;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpMessageServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Configuration
@Import(WorkWechatProperties.class)
@RequiredArgsConstructor
public class WorkWechatConfig {

    private final WorkWechatProperties properties;

    @Bean
    public WxCpDefaultConfigImpl wxCpDefaultConfig() {
        WxCpDefaultConfigImpl wxCpDefaultConfig = new WxCpDefaultConfigImpl();
        wxCpDefaultConfig.setCorpId(properties.getCorpID());
        wxCpDefaultConfig.setCorpSecret(properties.getCorpSecret());
        wxCpDefaultConfig.setAgentId(properties.getAgentId());
        wxCpDefaultConfig.setToken(properties.getToken());
        wxCpDefaultConfig.setAesKey(properties.getEncodingAESKey());
        return wxCpDefaultConfig;
    }

    @Bean
    public WxCpService wxCpService(WxCpDefaultConfigImpl wxCpDefaultConfig) {
        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(wxCpDefaultConfig);
        return wxCpService;
    }

    @Bean
    public WxCpMessageServiceImpl wxCpMessageService(WxCpService wxCpService) {
        return new WxCpMessageServiceImpl(wxCpService);
    }

    @Bean
    public WXBizMsgCrypt wXBizMsgCrypt() {
        return new WXBizMsgCrypt(properties.getToken(), properties.getEncodingAESKey(),
                properties.getCorpID());
    }

    @Bean
    public WxCryptUtil wxCryptUtil() {
        return new WxCryptUtil(properties.getToken(), properties.getEncodingAESKey(),
                properties.getCorpID());
    }

    @Bean
    @Scope("prototype")
    public WxCpMessage message() {
        return WxCpMessage.TEXT().agentId(properties.getAgentId()).build();
    }
}
