package ru.mail.arseniy.chat.action;

import com.google.gson.JsonObject;

/**
 * Created by arseniy on 22.06.16.
 */
public class ActionAuth implements Action {

    private String mLogin;
    private String mPass;

    public ActionAuth(String mLogin, String mPass) {
        this.mLogin = mLogin;
        this.mPass = mPass;
    }

    @Override
    public String getAction() {
        if (mLogin != null && mPass != null) {
            JsonObject action = new JsonObject();
            action.addProperty("action", "auth");
            JsonObject data = new JsonObject();
            data.addProperty("login", mLogin);
            data.addProperty("pass", mPass);
            action.add("data", data);
            return action.toString();
        }
        return null;
    }
}
