package ru.mail.arseniy.chat.net;

import android.app.Fragment;

import com.google.gson.JsonObject;

public interface MessageListener {
    public void Welcomme(JsonObject json);
    public void Auth(JsonObject json);
    public void Register(JsonObject json);
}
