package ru.mail.arseniy.chat.net;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;

import ru.mail.arseniy.chat.R;

/**
 * Переключение между фрагментами
 */
public class Controller implements MessageListener {

    private FragmentTransaction fTrans;
    private Activity activity;
    private HashMap<String, Fragment> mFragment = new HashMap<>();

    public Controller(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void Welcome(JsonObject json) {
        fTrans = activity.getFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCont, mFragment.get("auth"));
        fTrans.commit();
    }

    @Override
    public void Auth(JsonObject json) {
        JsonObject data = json.get("data").getAsJsonObject();
        int status = data.get("status").getAsInt();
        final String error = data.get("error").getAsString();
        if (status!= 0) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void Register(JsonObject json) {
        JsonObject data = json.get("data").getAsJsonObject();
        int status = data.get("status").getAsInt();
        final String error = data.get("error").getAsString();
        if (status!= 0) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            fTrans = activity.getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frgmCont, mFragment.get("auth"));
            fTrans.commit();
        }
    }

    @Override
    public void ChannelList(JsonObject json) {

    }

}
