package ru.mail.arseniy.chat.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.net.APIService;

/**
 * фрагмент отображающий переписку с пользователем
 */
public class UserChatFragment extends Fragment {

    private Button sendButton;
    private TextView tw;
    private APIService service;
    private String senderName;

    public UserChatFragment (APIService service, String senderName) {
        this.service = service;
        this.senderName = senderName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);

        tw = (TextView) view.findViewById(R.id.nameSender);
        tw.setText(senderName);


        sendButton = (Button) view.findViewById(R.id.sendMsg);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //TODO Сделать отправку сообщения по сети
            }
        });

        return view;
    }

}
