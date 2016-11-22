package ru.mail.arseniy.chat.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.mail.arseniy.chat.R;

public class AdapterChannel extends BaseAdapter {

    private Context ctx;
    private List<Channel> channels;
    private LayoutInflater lInflater;

    public AdapterChannel (Context context, List<Channel> products) {
        ctx = context;
        channels = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Override
    public Object getItem(int position) {
        return channels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.channel, parent, false);
        }

        Channel channel = (Channel)getItem(position);

        ((TextView) view.findViewById(R.id.nameChannel)).setText(channel.nameChannel);
        ((TextView) view.findViewById(R.id.countUser)).setText(channel.countUser);
        ((TextView) view.findViewById(R.id.descChannel)).setText(channel.descChannel);

        return view;
    }
}
