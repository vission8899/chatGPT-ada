package com.vission.chatGPT.model;

import lombok.Data;

/**
 * @author vission
 * @date 2023/3/7 20:47
 * @description
 */
@Data
public class GptMessageDto {

    private String role;
    private String content;
}
