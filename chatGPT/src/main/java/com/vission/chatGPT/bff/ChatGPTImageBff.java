package com.vission.chatGPT.bff;

import com.vission.chatGPT.service.ChatGPTImageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatGPTImageBff {

    private final ChatGPTImageService chatGPTImageService;


    public List<String> generations(String description) {
        return chatGPTImageService.generations(description);
    }
}
