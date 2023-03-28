package com.vission.service;

import com.vission.dto.WechatXmlDTO;
import com.vission.model.ReceiveMsg;
import com.vission.utils.CacheHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import me.chanjar.weixin.cp.api.WxCpMessageService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author vission
 * @date 2023/3/7 11:16 下午
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WeChatService {

    private final ApplicationContext context;
    private final WxCryptUtil wxCryptUtil;
    private final WxCpMessageService wxCpMessageService;
    private final WxCpService wxCpService;
    private static final String WECHAT_TOKEN = "WECHAT_TOKEN";


    public ReceiveMsg parseMsg(WechatXmlDTO body) {
        String agentId = body.getAgentID();
        String toUserName = body.getToUserName();
        //解密
        String plaintext = decrypt(body.getEncrypt());
        String fromUserName = StringUtils.substringBetween(plaintext, "<FromUserName><![CDATA[", "]]></FromUserName>");
        String content = StringUtils.substringBetween(plaintext, "<Content><![CDATA[", "]]></Content>");
        String msgType = StringUtils.substringBetween(plaintext, "<MsgType><![CDATA[", "]]></MsgType>");
        String createTime = StringUtils.substringBetween(plaintext, "<CreateTime>", "</CreateTime>");
        String msgId = StringUtils.substringBetween(plaintext, "<MsgId>", "</MsgId>");
        return ReceiveMsg.builder()
                .toUserName(toUserName)
                .fromUserName(fromUserName)
                .createTime(Long.valueOf(createTime))
                .msgType(msgType)
                .content(content)
                .msgId(Long.valueOf(msgId))
                .agentId(Long.valueOf(agentId))
                .build();
    }

    public void sendMsg(String msg, String toUser) {
        try {
            WxCpMessage message = context.getBean(WxCpMessage.class);
            message.setMsgType(WxConsts.KefuMsgType.MARKDOWN);
            message.setContent(msg);
            message.setToUser(toUser);
            wxCpMessageService.send(message);
        } catch (WxErrorException e) {
            WeChatService.log.error("消息发送失败:{}", e);
        }
    }

    /**
     * 解密
     *
     * @param ciphertext 加密信息
     * @return 明文信息
     */
    private String decrypt(String ciphertext) {
        return wxCryptUtil.decrypt(ciphertext);
    }

    private String getAccessToken() throws Exception {
        String accessToken = wxCpService.getAccessToken();
        String data = CacheHelper.get(WeChatService.WECHAT_TOKEN);
        if (StringUtils.isNotEmpty(data)) {
            return data;
        }
        CacheHelper.set(WeChatService.WECHAT_TOKEN, accessToken);
        return accessToken;
    }

}
