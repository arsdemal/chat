package ru.mail.arseniy.chat.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.User;
import ru.mail.arseniy.chat.action.Action;
import ru.mail.arseniy.chat.action.ActionAuth;
import ru.mail.arseniy.chat.net.APIService;
import ru.mail.arseniy.chat.net.MainActivity;
import ru.mail.arseniy.chat.net.MessageReceiver;
import ru.mail.arseniy.chat.net.MessageSender;

public class AuthFragment extends Fragment {

    private EditText fLogin;
    private EditText fPass;
    private Button bEnter;
    private Button bRegister;
    private FragmentManager fm;
    private MessageReceiver receiver;
    private MessageSender sender;
    public AuthFragment (FragmentManager fm, MessageReceiver receiver, MessageSender sender) {
        this.fm = fm;
        this.receiver = receiver;
        this.sender = sender;
    }


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

                //TODO Сделать запрос на авторизацию

                //Заглушка
                if (pass.equals("123")) {

                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.setUserName(login);



                    FragmentTransaction fTrans;
                    fTrans = fm.beginTransaction();
                    fTrans.replace(R.id.frgmCont, new UserChatFragment(login,receiver,sender));
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }

            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fTrans;
                fTrans = fm.beginTransaction();
                fTrans.replace(R.id.frgmCont,new RegFragment());
                fTrans.addToBackStack(null);
                fTrans.commit();
            }
        });

        return  view;
    }

}
