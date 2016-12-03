package ru.mail.arseniy.chat.net;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import okhttp3.internal.framed.FrameReader;
import retrofit2.Retrofit;
import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.ui.AuthFragment;
import ru.mail.arseniy.chat.ui.WeclomeFragment;


public class MainActivity extends AppCompatActivity {

    FragmentTransaction fTrans;

    private HashMap<String, Fragment> mFragment = new HashMap<>();
    //private Controller controller;
    private Retrofit retrofit;
    private APIService service;
    private FragmentManager fm;
    private String userName;
    //private GAuthHelper

    private static final String TAG = "myLogs";
    public static final String HOST = "93.175.9.247";
    public static final int PORT = 7777;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Устанавливаем вспомогательные модули
        //controller = new Controller(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();
        service = retrofit.create(APIService.class);

        fm = getFragmentManager();
        fTrans = fm.beginTransaction();
        fTrans.add(R.id.frgmCont, new AuthFragment(service,fm));
        fTrans.commit();

        class OnError implements Handler.Callback {

            @Override
            public boolean handleMessage(Message message) {
                return false;
            }
        }

    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "Pressed button back");
        if (fm.getBackStackEntryCount()>0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
}


