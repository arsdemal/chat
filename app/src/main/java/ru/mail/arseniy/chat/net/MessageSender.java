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
    private static final String TAG = "MessageSender";


    public MessageSender(Socket communicationSocket) throws IOException {
        mSocket = communicationSocket;
        mStream = new BufferedOutputStream(communicationSocket.getOutputStream());
    }

    public synchronized void setCurrentAction(String action) {
        this.currentAction = action;
    }

    @Override
    public void run() {
        boolean stop = false;
        currentAction = null;

        Log.d(TAG,"Start loop");

        while (!stop) {

            if (currentAction != null) {
                String actionJson = currentAction;
                Log.d(TAG,"New action");
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
            System.out.println("Connection is closed");
        }
        catch (IOException e) {
            System.out.println("Can not close socket. Application aborted");
        }
    }
}