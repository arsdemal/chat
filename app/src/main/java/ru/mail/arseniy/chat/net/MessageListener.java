package ru.mail.arseniy.chat.net;

import android.app.Fragment;

import com.google.gson.JsonObject;

public interface MessageListener {
    //public void Welcome(JsonObject json);
    //public void Auth(JsonObject json);
    //public void Register(JsonObject json);
    //public void ChannelList(JsonObject json);
    public void GetMessage(JsonObject json);
}
