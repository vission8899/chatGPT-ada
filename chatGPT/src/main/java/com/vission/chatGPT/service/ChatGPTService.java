package com.vission.chatGPT.service;

import com.google.common.collect.Lists;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.vission.chatGPT.properties.ChatGPTProperties;
import com.vission.chatGPT.utils.BeanUtils;
import com.vission.chatGPT.utils.JsonUtils;
import com.vission.chatGPT.utils.RedisUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatGPTService {

    private final ChatGPTProperties properties;
    private final OpenAiService openAiService;
    private final RedisUtils redisUtils;


    /**
     * 翻译助手
     *
     * @param original 原文
     * @return 翻译结果
     */
    public String translation(String original) {
        StringBuilder completion = new StringBuilder();
        ChatMessage newQuestionMessage = new ChatMessage(ChatMessageRole.USER.value(), original);
        ChatMessage system = new ChatMessage(ChatMessageRole.SYSTEM.value(),
                "你是一个翻译助手，将我说的所有话翻译成中文");
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(Lists.newArrayList(system, newQuestionMessage))
                .build();
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(request);
        List<ChatCompletionChoice> choices = chatCompletion.getChoices();
        for (ChatCompletionChoice choice : choices) {
            completion.append(choice.getMessage().getContent());
        }
        return completion.toString();
    }

    /**
     * 聊天 不会保存上下文聊天
     *
     * @param original 原文
     * @return 翻译结果
     */
    public String chatCompletion(String original) {
        StringBuilder completion = new StringBuilder();
        ChatMessage newQuestionMessage = new ChatMessage(ChatMessageRole.USER.value(), original);
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(Lists.newArrayList(newQuestionMessage))
                .build();
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(request);
        List<ChatCompletionChoice> choices = chatCompletion.getChoices();
        for (ChatCompletionChoice choice : choices) {
            completion.append(choice.getMessage().getContent());
        }
        return completion.toString();
    }

    /**
     * 聊天 会保存上下文聊天
     *
     * @param original 原文
     * @param userUuid 用户唯一标识
     * @return 翻译结果
     */
    public String chatCompletionByContext(String original, String userUuid) {
        List<ChatMessage> messages = findChatMessagesByUuid(userUuid);
        int messageCount = (int) messages.stream().map(ChatMessage::getRole)
                .filter(t -> StringUtils.equals(t, ChatMessageRole.USER.value())).count();
        if (messageCount > properties.getChatGptFlowNum()) {
            redisUtils.del(userUuid);
            return "您的连续对话已超过上限,系统已自动清空上下文";
        }
        StringBuilder result = new StringBuilder();
        ChatMessage newMessage = new ChatMessage(ChatMessageRole.USER.value(), original);
        messages.add(newMessage);
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo").messages(messages).build();
        ChatGPTService.log.info("request:{}", JsonUtils.toJson(request));
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(request);
        List<ChatCompletionChoice> choices = chatCompletion.getChoices();
        for (ChatCompletionChoice choice : choices) {
            messages.add(choice.getMessage());
            result.append(choice.getMessage().getContent());
        }
        redisUtils.set(userUuid, messages, 1800);
        return result.toString();
    }

    private List<ChatMessage> findChatMessagesByUuid(String userUuid) {
        List result = redisUtils.getList(userUuid);
        return BeanUtils.deepCopyList(result, ChatMessage.class);
    }

}
