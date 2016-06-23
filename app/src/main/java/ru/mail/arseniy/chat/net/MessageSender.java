package ru.mail.arseniy.chat.net;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

public class MessageSender implements Runnable {
    private final Socket mSocket;
    private final OutputStream mStream;
    private String currentAction;


    public MessageSender(Socket communicationSocket) throws IOException {
        mSocket = communicationSocket;
        mStream = new BufferedOutputStream(communicationSocket.getOutputStream());
    }

    public void setCurrentAction(String action) {
        this.currentAction = action;
        Log.i("TAG", "Current action: " + action);
    }

    @Override
    public void run() {
        boolean stop = false;
        currentAction = null;

        while (!stop) {

            if (currentAction != null) {
                String actionJson = currentAction;
                byte[] data = actionJson.getBytes(Charset.forName("UTF-8"));
                try {
                    mStream.write(data);
                    mStream.flush();
                    currentAction = null;
                } catch (IOException e) {
                    //maybe connection lost
                    stop = true;
                }
            }
        }

        try {
            mStream.close();
            mSocket.close();
            Log.i("TAG","Connection is closed");
        }
        catch (IOException e) {
            Log.i("TAG","Can not close socket. Application aborted");
        }
    }
}
