package ru.mail.arseniy.chat.net;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import retrofit2.Retrofit;
import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.ui.AuthFragment;
import ru.mail.arseniy.chat.ui.WeclomeFragment;


public class MainActivity extends AppCompatActivity {

    FragmentTransaction fTrans;

    private HashMap<String, Fragment> mFragment = new HashMap<>();
    private Controller controller;
    private Retrofit retrofit;
    private APIService service;

    public static final String HOST = "188.166.49.215";
    public static final int PORT = 7777;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Устанавливаем вспомогательные модули
        controller = new Controller(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("")
                .build();
        service = retrofit.create(APIService.class);
        

        mFragment.put("welcome", new WeclomeFragment());

        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCont, mFragment.get("welcome"));
        fTrans.commit();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(HOST, PORT);

                    MessageSender sender = new MessageSender(socket);
                    MessageReceiver receiver = new MessageReceiver(socket, controller);

                    Thread senderT = new Thread(sender);

                    mFragment.put("auth", new AuthFragment(sender));


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


