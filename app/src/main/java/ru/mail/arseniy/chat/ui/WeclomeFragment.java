package ru.mail.arseniy.chat.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.mail.arseniy.chat.R;

/**
 * Created by arseniy on 22.06.16.
 */
public class WeclomeFragment extends Fragment {

    private ImageView img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome,container,false);

        img = (ImageView)view.findViewById(R.id.imageWeclome);
        img.setImageResource(R.drawable.welcome);

        return view;
    }
}
