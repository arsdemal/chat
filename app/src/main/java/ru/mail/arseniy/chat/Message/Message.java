package ru.mail.arseniy.chat.Message;

import com.google.gson.JsonObject;

/**
 * Created by arseniy on 22.06.16.
 */
public interface Message {
    void parse(JsonObject json);
    String doOutput();
}
