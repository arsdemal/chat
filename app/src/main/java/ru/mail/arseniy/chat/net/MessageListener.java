package ru.mail.arseniy.chat.net;

import android.app.Fragment;

import com.google.gson.JsonObject;

public interface MessageListener {
    public void onMessageReceived(String info);
    public void setFragment(String fragment);
    public Fragment getFragment();
    public void Welcomme(JsonObject json);
}
