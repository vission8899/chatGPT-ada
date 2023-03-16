package com.vission.chatGPT.bff;

import com.vission.chatGPT.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatGPTCompletionBff {

    private final ChatGPTService chatGPTService;


    public String translation(String original) {
        return chatGPTService.translation(original);
    }

    public String chatCompletion(String question) {
        return chatGPTService.chatCompletion(question);
    }

    public String chatCompletionByContext(String question, String userUuid) {
        return chatGPTService.chatCompletionByContext(question, userUuid);
    }

}
