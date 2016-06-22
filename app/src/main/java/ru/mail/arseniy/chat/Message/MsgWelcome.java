package ru.mail.arseniy.chat.Message;

import com.google.gson.JsonObject;

import java.util.Date;

public class MsgWelcome implements Message {
    private String message;
    private long time;
    private String action;

    @Override
    public void parse(JsonObject json) {
        message = json.get("message").getAsString();
        time = json.get("time").getAsLong();
        action = json.get("action").getAsString();
    }

    @Override
    public String doOutput() {
        Date serverTime = new Date(time);
        return message;
    }
}
