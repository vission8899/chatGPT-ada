package com.vission.chatGPT.controller;


import com.vission.chatGPT.bff.ChatGPTImageBff;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-gpt/admin")
@RequiredArgsConstructor
public class ChatGPTAdminController {

    private final ChatGPTImageBff chatGPTAdminBff;

    @RequestMapping("/user/{openAiKey}")
    public String find(@PathVariable("openAiKey") String openAiKey) {
//        chatGPTAdminBff.find(openAiKey);
        return null;
    }

}
