package ru.mail.arseniy.chat.net;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.action.Action;
import ru.mail.arseniy.chat.action.ActionChannelList;
import ru.mail.arseniy.chat.ui.AuthFragment;
import ru.mail.arseniy.chat.ui.ChannelListFragment;
import ru.mail.arseniy.chat.ui.WeclomeFragment;


public class MainActivity extends AppCompatActivity implements MessageListener {

    FragmentTransaction fTrans;
    TextView infoText;

    private HashMap<String, Fragment> mFragment = new HashMap<>();

    public static final String HOST = "192.168.0.105";
    public static final int PORT = 7777;
    private String sessionId;
    private String userId;
    private MessageSender sender;

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
        } else {
            Log.i("TAG","Success authorization");
            sessionId = data.get("sid").getAsString();
            userId = data.get("cid").getAsString();
            Action action = new ActionChannelList(sessionId,userId);
            sender.setCurrentAction(action.getAction());

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
            Log.i("TAG","Success registration");
        }
    }

    @Override
    public void ChannelList(JsonObject json) {
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

            ChannelListFragment fragment = (ChannelListFragment)mFragment.get("channelList");
            JsonArray channels = data.getAsJsonArray("channels");
            Log.i("TAG", String.valueOf(data.get("channels")));
            fragment.setData(data.getAsJsonArray("channels"));
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frgmCont, fragment);
            fTrans.commit();
        }


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

                    sender = new MessageSender(socket);
                    MessageReceiver receiver = new MessageReceiver(socket);

                    Thread senderT = new Thread(sender);

                    mFragment.put("auth", new AuthFragment(sender));
                    mFragment.put("channelList", new ChannelListFragment());


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


