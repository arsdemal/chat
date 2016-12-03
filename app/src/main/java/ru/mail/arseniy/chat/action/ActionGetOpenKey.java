package ru.mail.arseniy.chat.action;

import com.google.gson.JsonObject;

/**
 *
 */

public class ActionGetOpenKey implements Action {

    private String mLoginTo;
    private String mLoginFrom;

    public ActionGetOpenKey(String mLoginTo, String mLoginFrom) {
        this.mLoginTo = mLoginTo;
        this.mLoginFrom = mLoginFrom;
    }

    @Override
    public String getAction() {

        if (mLoginTo!=null) {
            JsonObject action = new JsonObject();
            action.addProperty("action","getOpenKey");
            JsonObject data = new JsonObject();
            data.addProperty("loginTo",mLoginTo);
            data.addProperty("loginFrom",mLoginFrom);
            action.add("data",data);
            return action.toString();
        }

        return null;
    }
}
