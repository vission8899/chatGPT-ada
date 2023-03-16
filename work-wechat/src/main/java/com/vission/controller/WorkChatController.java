package com.vission.controller;

import com.vission.bff.WorkWeChatBff;
import com.vission.dto.WechatXmlDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vission
 * @date 2023/3/7 6:29 下午
 * @description
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/work-wechat")
public class WorkChatController {

    WorkWeChatBff workWeChatBff;

    @GetMapping("/receiveMsg")
    public String receiveMsg(@RequestParam("msg_signature") String msg_signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {
        return workWeChatBff.receiveMsg(msg_signature, timestamp, nonce, echostr);
    }


    @PostMapping("/receiveMsg")
    public void receiveMsg(@RequestBody WechatXmlDTO wechatXmlDTO) {
        workWeChatBff.receiveMsg(wechatXmlDTO);
    }

}
