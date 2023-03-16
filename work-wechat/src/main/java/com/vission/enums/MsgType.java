package com.vission.enums;

public enum MsgType {
    TEXT("text"),
    IMAGE("image"),
    VOICE("voice"),
    LOCATION("location"),
    LINK("link");


    private String code;

    MsgType(String code) {
        this.code = code;
    }
}
