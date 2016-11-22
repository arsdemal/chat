package ru.mail.arseniy.chat.action;

import android.util.Log;

import com.google.gson.JsonObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ActionReg implements Action {

    private String mLogin;
    private String mPass;
    private String mNick;

    public void setData(String login, String pass, String nick) {
        this.mLogin = login;
        this.mPass = pass;
        this.mNick = nick;
    }

    @Override
    public String getAction() {
        if (mLogin != null && mPass != null && mNick != null) {
            JsonObject action = new JsonObject();
            action.addProperty("action", "register");
            JsonObject data = new JsonObject();
            data.addProperty("login", mLogin);
            data.addProperty("pass", mPass);
            data.addProperty("nick", mNick);
            action.add("data", data);
            return action.toString();
        }
        return null;
    }

    public static String md5(String s) {
        String md5sum = null;
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            md5sum = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5sum;
    }
}
