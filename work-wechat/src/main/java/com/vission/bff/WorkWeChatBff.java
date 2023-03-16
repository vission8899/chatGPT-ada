package com.vission.bff;

import com.vission.chatGPT.service.ChatGPTService;
import com.vission.dto.WechatXmlDTO;
import com.vission.model.ReceiveMsg;
import com.vission.model.aes.WXBizMsgCrypt;
import com.vission.service.WeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vission
 * @date 2023/3/7 6:29 下午
 * @description 微信消息接收
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class WorkWeChatBff {

    private final WXBizMsgCrypt wXBizMsgCrypt;
    private final WeChatService weChatService;
    private final ChatGPTService chatGptService;

    public String receiveMsg(String msg_signature, String timestamp, String nonce, String echostr) {
        String sEchoStr;
        try {
            sEchoStr = wXBizMsgCrypt.VerifyURL(msg_signature, timestamp, nonce, echostr);
            WorkWeChatBff.log.info("verifyurl echostr: " + sEchoStr);
            // 验证URL成功，将sEchoStr返回
            return sEchoStr;
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            WorkWeChatBff.log.error("verifyUrl error,e:{}", e);
            return "";
        }
    }


    public void receiveMsg(WechatXmlDTO wechatXmlDTO) {
        //解析
        ReceiveMsg receiveMsg = weChatService.parseMsg(wechatXmlDTO);
        WorkWeChatBff.log.info("接收到的消息：{}", receiveMsg.toString());
        String content = receiveMsg.getContent();
        String fromUserName = receiveMsg.getFromUserName();
        // 调openai
        String result = chatGptService.chatCompletionByContext(content, fromUserName);
        //给微信发消息
        weChatService.sendMsg(result, fromUserName);
    }

}
