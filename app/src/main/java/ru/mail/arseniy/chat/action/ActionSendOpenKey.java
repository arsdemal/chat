package ru.mail.arseniy.chat.action;

import com.google.gson.JsonObject;

import java.security.Key;

/**
 *
 */

public class ActionSendOpenKey implements Action {

    private String mLoginTo;
    private String mLoginFrom;
    private String openKey;

    public ActionSendOpenKey(String mLoginTo, String mLoginFrom, String openKey) {
        this.mLoginTo = mLoginTo;
        this.mLoginFrom = mLoginFrom;
        this.openKey = openKey;
    }

    @Override
    public String getAction() {

        if (mLoginTo!=null &&  openKey!=null) {
            JsonObject action = new JsonObject();
            action.addProperty("action","sendOpenKey");
            JsonObject data = new JsonObject();
            data.addProperty("loginTo",mLoginTo);
            data.addProperty("loginFrom",mLoginFrom);
            data.addProperty("openKey",openKey);
            action.add("data",data);
            return action.toString();

        }

        return null;
    }
}
