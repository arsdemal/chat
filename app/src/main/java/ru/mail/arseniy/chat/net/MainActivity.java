package ru.mail.arseniy.chat.net;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.ui.AuthFragment;
import ru.mail.arseniy.chat.ui.RegFragment;
import ru.mail.arseniy.chat.ui.WeclomeFragment;


public class MainActivity extends AppCompatActivity implements MessageListener {

    FragmentTransaction fTrans;
    TextView infoText;

    private HashMap<String, Fragment> mFragment = new HashMap<>();

    public static final String HOST = "188.166.49.215";
    public static final int PORT = 7777;

    @Override
    public void Welcome(JsonObject json) {
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCont, mFragment.get("auth"));
        fTrans.commit();
    }

    @Override
    public void Auth(JsonObject json) {
        JsonObject data = json.get("data").getAsJsonObject();
        int status = data.get("status").getAsInt();
        final String error = data.get("error").getAsString();
        if (status!= 0) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
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
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frgmCont, mFragment.get("auth"));
            fTrans.commit();
        }
    }

    @Override
    public void ChannelList(JsonObject json) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFragment.put("welcome", new WeclomeFragment());





        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCont, mFragment.get("welcome"));
        fTrans.commit();

        final MessageListener listener = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(HOST, PORT);

                    MessageSender sender = new MessageSender(socket);
                    MessageReceiver receiver = new MessageReceiver(socket);

                    Thread senderT = new Thread(sender);

                    mFragment.put("auth", new AuthFragment(sender));


                    receiver.setMessageListener(listener);
                    Thread receiverT = new Thread(receiver);

                    Log.i("TAG", "info");


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

    }
}


