package ru.mail.arseniy.chat.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.action.Action;
import ru.mail.arseniy.chat.action.ActionAuth;
import ru.mail.arseniy.chat.net.MessageSender;

public class AuthFragment extends Fragment {

    private EditText fLogin;
    private EditText fPass;
    private Button bEnter;
    private Button bRegister;
    private MessageSender messageSender;
    public AuthFragment (MessageSender messageSender) {this.messageSender = messageSender;}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth,container,false);

        fLogin = (EditText)view.findViewById(R.id.authLogin);
        fPass = (EditText)view.findViewById(R.id.authPass);
        bEnter = (Button)view.findViewById(R.id.authEnterButton);
        bRegister = (Button)view.findViewById(R.id.authRegButton);

        bEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = fLogin.getText().toString();
                String pass = fPass.getText().toString();

                Action auth = new ActionAuth(login,pass);
                messageSender.setCurrentAction(auth.getAction());
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fTrans;
                fTrans = getFragmentManager().beginTransaction();
                fTrans.replace(R.id.frgmCont,new RegFragment(messageSender));
                fTrans.commit();
            }
        });

        return  view;
    }

}
