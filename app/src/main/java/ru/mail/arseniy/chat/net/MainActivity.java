package ru.mail.arseniy.chat.net;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.ui.RegFragment;
import ru.mail.arseniy.chat.ui.WeclomeFragment;


public class MainActivity extends AppCompatActivity implements MessageListener {

    FragmentTransaction fTrans;
    TextView infoText;

    private HashMap<String, Fragment> mFragment = new HashMap<>();

    public static final String HOST = "188.166.49.215";
    public static final int PORT = 7777;

    @Override
    public void onMessageReceived(String info) {
        infoText.setText(info);
    }

    @Override
    public void setFragment(String fragment) {
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    public void Welcomme(JsonObject json) {
        fTrans.remove(mFragment.get("welcome"));
        fTrans.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFragment.put("welcome", new WeclomeFragment());

        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCont, mFragment.get("welcome"));
        fTrans.commit();

        try {
            Socket socket = new Socket(HOST, PORT);
            System.out.println("Hello");
            MessageSender sender = new MessageSender(socket);
            Thread senderT = new Thread(sender);

            mFragment.put("register", new RegFragment(sender));


            MessageReceiver messageReceiver = new MessageReceiver(socket);
            messageReceiver.setMessageListener(this);
            Thread receiver = new Thread(messageReceiver);


            receiver.start();
            senderT.start();
            receiver.join();
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
}


