package ru.mail.arseniy.chat.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.action.ActionReg;
import ru.mail.arseniy.chat.net.MessageSender;

public class RegFragment extends Fragment {

    private EditText fLogin;
    private EditText fNickname;
    private EditText fPass;
    private MessageSender messageSender;
    public RegFragment(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg,container,false);

        fLogin = (EditText)view.findViewById(R.id.regLogin);
        fNickname = (EditText)view.findViewById(R.id.regNick);
        fPass = (EditText)view.findViewById(R.id.regPass);
        final Button button = (Button)view.findViewById(R.id.buttonReg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = fLogin.getText().toString();
                String nickname = fNickname.getText().toString();
                String pass = fPass.getText().toString();

                ActionReg reg = new ActionReg();
                reg.setData(login,nickname,pass);
                messageSender.setCurrentAction(reg.getAction());
            }
        });
        return  view;
    }

}
