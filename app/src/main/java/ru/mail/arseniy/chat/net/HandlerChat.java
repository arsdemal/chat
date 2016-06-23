package ru.mail.arseniy.chat.net;

import android.os.Handler;
import android.os.Message;

import java.util.logging.LogRecord;

public class HandlerChat extends Handler {

    final int STATUS_WELCOME = 0;

    private String host;
    private int port;

    public HandlerChat(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case STATUS_WELCOME:
                break;

        }
        super.handleMessage(msg);
    }


}
