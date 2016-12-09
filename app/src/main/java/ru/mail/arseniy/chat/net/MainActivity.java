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
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Logger;

import okhttp3.internal.framed.FrameReader;
import retrofit2.Retrofit;
import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.ui.AuthFragment;
import ru.mail.arseniy.chat.ui.WeclomeFragment;


public class MainActivity extends AppCompatActivity {

    FragmentTransaction fTrans;

    private FragmentManager fm;
    private String userName;
    private MessageSender sender;
    private MessageReceiver receiver;

    private static final String TAG = "myLogs";
    public static final String HOST = "192.168.0.105";
    public static final int PORT = 7777;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(HOST, PORT);

                    sender = new MessageSender(socket);
                    receiver = new MessageReceiver(socket);
                    //receiver.setMessageListener(messageListener);
                    if (receiver != null) {
                        Log.d(TAG,"Good");
                    }

                    Thread senderT = new Thread(sender);
                    Thread receiverT = new Thread(receiver);

                    receiverT.start();
                    senderT.start();
                    receiverT.join();
                    senderT.join();

                }

                catch (IOException e) {
                    System.out.println("Connection aborted due to exception " + e.getMessage() );
                }

                catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Abnormal interruption. Good bye");
                }
            }
        }).start();

        fm = getFragmentManager();
        fTrans = fm.beginTransaction();
        while (receiver == null && sender == null) {
        }
        fTrans.add(R.id.frgmCont, new AuthFragment(fm,receiver,sender));
        fTrans.commit();
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


