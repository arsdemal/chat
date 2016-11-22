package ru.mail.arseniy.chat.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ru.mail.arseniy.chat.R;

public class ChannelListFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auth,container,false);

        return view;
    }

    public void setData(JsonArray array) {
        for (JsonElement i: array) {
            if (i.isJsonObject()) {
                Log.i("TAG", "ok");
            }
        }
    }
}
