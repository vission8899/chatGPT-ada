package com.vission.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiveMsg {

    /**
     * 企业微信CorpID
     */
    private String toUserName;
    /**
     * 成员UserID
     */
    private String fromUserName;
    /**
     * 消息创建时间（整型）
     */
    private Long createTime;
    /**
     * 消息类型，此时固定为：text
     */
    private String msgType;
    /**
     * 文本消息内容
     */
    private String content;
    /**
     * 消息id，64位整型
     */
    private Long msgId;
    /**
     * 企业应用的id，整型。可在应用的设置页面查看
     */
    private Long agentId;


}
