package ru.mail.arseniy.chat.action;

import android.app.Notification;

import com.google.gson.JsonObject;

/**
 * Created by Arsedemal on 29.11.2016.
 */

public class ActionMsg implements Action {

    private String mLoginTo;
    private String mLoginFrom;
    private String msg;


    public ActionMsg (String mLoginTo, String mLoginFrom, String msg) {
        this.mLoginFrom = mLoginFrom;
        this.mLoginTo = mLoginTo;
        this.msg = msg;
    }

    @Override
    public String getAction() {
        if (mLoginFrom!=null && mLoginFrom!=null && msg!=null) {
            JsonObject action = new JsonObject();
            action.addProperty("action","send");
            JsonObject data = new JsonObject();
            data.addProperty("loginTo",mLoginTo);
            data.addProperty("loginFrom",mLoginFrom);
            data.addProperty("msg",msg);
            action.add("data",data);
            return action.toString();

        }
        return null;
    }
}
