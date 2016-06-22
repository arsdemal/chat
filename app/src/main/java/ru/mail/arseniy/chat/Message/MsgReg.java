package ru.mail.arseniy.chat.Message;

import com.google.gson.JsonObject;

public class MsgReg implements Message {
    private int status;
    private String error;

    @Override
    public void parse(JsonObject json) {
        JsonObject data = json.get("data").getAsJsonObject();
        status = data.get("status").getAsInt();
        error = data.get("error").getAsString();
    }

    @Override
    public String doOutput() {
        if (status == 0) {
            return "Registration successful!";
        }
        else {
            return "Error! " + error;
        }
    }
}
