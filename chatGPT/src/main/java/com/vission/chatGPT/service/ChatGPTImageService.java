package com.vission.chatGPT.service;

import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;
import com.theokanning.openai.service.OpenAiService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatGPTImageService {

    private final OpenAiService openAiService;


    public List<String> generations(String description) {
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(description)
                .size("1024x1024")
                .responseFormat("url")
                .user("vission")
                .n(1)
                .build();
        ImageResult image = openAiService.createImage(request);
        return image.getData().stream().map(Image::getUrl).collect(Collectors.toList());
    }
}
