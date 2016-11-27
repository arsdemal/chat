package ru.mail.arseniy.chat.ui;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.db.DBHelperUsers;
import ru.mail.arseniy.chat.net.APIService;

/**
 * фрагмент отображающий переписку с пользователем
 */
public class UserChatFragment extends Fragment {

    private static final String TAG = "UserChatFragment";

    private Button sendButton;
    private Button cancelButton;
    private TextView tw;
    private TextView windowChat;
    private EditText editText;
    private APIService service;
    private String senderName;
    private DBHelperUsers dbHelperUsers;

    public UserChatFragment (APIService service, String senderName) {
        this.service = service;
        this.senderName = senderName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);

        tw = (TextView) view.findViewById(R.id.nameSender);
        tw.setText(senderName);
        windowChat = (TextView) view.findViewById(R.id.chatWindow);
        editText = (EditText) view.findViewById(R.id.editText);
        sendButton = (Button) view.findViewById(R.id.sendMsg);
        cancelButton = (Button) view.findViewById(R.id.cancelMsg);

        /**
         *  Отправляем сообщение шифруя его с помощью закрытого ключа и открытого из базы данных
         */
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //TODO Сделать отправку сообщения по сети


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        } );

        return view;



    }

    /**
     * Получаем сообщения из локальной бд
     */
    public void updateMessages() {
        //TODO
        SQLiteDatabase database = dbHelperUsers.getReadableDatabase();
        Cursor cursor = database.rawQuery("",null);
    }

    public class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"update list of messages");
            updateMessages();
        }
    }

}
