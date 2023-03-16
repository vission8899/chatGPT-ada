package com.vission.chatGPT.controller;


import com.vission.chatGPT.bff.ChatGPTImageBff;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-gpt/image")
@RequiredArgsConstructor
@Api(tags = "图片")
public class ChatGPTImageController {

    private final ChatGPTImageBff chatGPTImageBff;

    @ApiOperation(value = "生成图片", httpMethod = "GET")
    @GetMapping("/generations")
    public List<String> generations(
            @ApiParam(value = "图片描述", required = true) @RequestParam("description") String description) {
        return chatGPTImageBff.generations(description);
    }
}
