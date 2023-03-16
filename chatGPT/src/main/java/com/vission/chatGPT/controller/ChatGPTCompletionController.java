package com.vission.chatGPT.controller;


import com.vission.chatGPT.bff.ChatGPTCompletionBff;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "聊天")
@RestController
@RequestMapping("/chat-gpt/chat")
@RequiredArgsConstructor
public class ChatGPTCompletionController {

    private final ChatGPTCompletionBff chatGPTBff;

    @ApiOperation(value = "翻译姬", httpMethod = "GET")
    @RequestMapping("/translation")
    public String translationAssistant(
            @ApiParam(value = "原文", required = true) @RequestParam("original") String original) {
        return chatGPTBff.translation(original);
    }

    @ApiOperation(value = "不带上下文对话", httpMethod = "GET")
    @RequestMapping("/completions")
    public String chatCompletion(
            @ApiParam(value = "问题", required = true) @RequestParam("question") String question) {
        return chatGPTBff.chatCompletion(question);
    }

    @ApiOperation(value = "带上下文对话", httpMethod = "GET")
    @RequestMapping("/completions/context")
    public String chatCompletionByContext(
            @ApiParam(value = "问题", required = true) @RequestParam("question") String question,
            @ApiParam(value = "记录对话唯一标识", required = true) @RequestParam("userUuid") String userUuid) {
        return chatGPTBff.chatCompletionByContext(question, userUuid);
    }
}
