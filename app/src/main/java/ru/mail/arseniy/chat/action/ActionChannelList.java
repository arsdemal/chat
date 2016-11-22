package ru.mail.arseniy.chat.action;

import com.google.gson.JsonObject;

/**
 * Created by arseniy on 23.06.16.
 */
public class ActionChannelList implements Action {

    private String sessionId;
    private String userId;

    public ActionChannelList(String sessionId, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    @Override
    public String getAction() {
        if (sessionId != null && userId != null) {
            JsonObject action = new JsonObject();
            action.addProperty("action","channellist");
            JsonObject data = new JsonObject();
            data.addProperty("cid",userId);
            data.addProperty("sid", sessionId);
            action.add("data",data);
            return action.toString();
        }
        return null;
    }
}
