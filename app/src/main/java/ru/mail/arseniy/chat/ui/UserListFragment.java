package ru.mail.arseniy.chat.ui;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.net.APIService;

public class UserListFragment extends Fragment {

    private APIService service;
    private ListView users;
    private FragmentManager fm;
    private String login;

    public UserListFragment(APIService service, FragmentManager fm, String login) {
        this.service = service;
        this.fm = fm;
        this.login = login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users,container,false);

        users = (ListView) view.findViewById(R.id.listUsers);
        final String[] nameUser = getResources().getStringArray(R.array.names);

        //TODO Достать данные из локальной БД

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.names, android.R.layout.simple_list_item_1);
        users.setAdapter(adapter);

        users.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {

                //TODO Передать уникальный итендификатор для передачи сообщения ()
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frgmCont, new UserChatFragment(service, login));
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        return view;
    }
}
