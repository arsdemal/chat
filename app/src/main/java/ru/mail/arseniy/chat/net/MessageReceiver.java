package ru.mail.arseniy.chat.net;

import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import ru.mail.arseniy.chat.Message.Message;
import ru.mail.arseniy.chat.Message.MsgReg;
import ru.mail.arseniy.chat.Message.MsgWelcome;

public class MessageReceiver implements Runnable {

    private static final String TAG = "MessageReceiver";
    private final Socket mSocket;
    private final InputStream mStream;
    private Handler chatHandler;
    private Handler AuthHandler;
    private Handler RegHandler;

    private MessageListener messageListener;


    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public HashMap<String, Message> mMessages = new HashMap<>();

    public MessageReceiver(Socket communicationSocket) throws IOException {
        mSocket = communicationSocket;
        mStream = new BufferedInputStream(communicationSocket.getInputStream());

        mMessages.put("register", new MsgReg());
        mMessages.put("welcome", new MsgWelcome());
    }

    public void setChatHandler(Handler handler) {
        this.chatHandler = handler;
    }

    @Override
    public void run() {


        Log.d(TAG,"Start");

        try {
            boolean stop = false;
            boolean cleanup = false;
            byte[] data = new byte[32768];
            //int offset = 0;
            JsonParser parser = new JsonParser();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


            Log.d(TAG,"start loop");

            do {

                if (cleanup) {
                    outputStream.reset();
                    //offset = 0;
                    cleanup = false;
                }

                // Запись в data производится от 0
                int readBytes = mStream.read(data);
                //Log.d(TAG,String.valueOf(readBytes));
                if (readBytes != -1) {
                    Log.d(TAG,"получили данные");
                    outputStream.write(data, 0, readBytes);
                    //offset += readBytes;
                    outputStream.flush();
                    outputStream.close();

                    String result = outputStream.toString("utf-8");

                    Log.d(TAG,result);

                    if (result.endsWith("}")) {
                        try {

                            JsonElement element = parser.parse(result);
                            JsonObject json = element.getAsJsonObject();
                            String action = json.get("action").getAsString();

                            if (action != null) {

                                switch (action) {
                                    case "auth":
                                        break;
                                    case "register":
                                        break;
                                    case "send":
                                    case "getOpenKey":
                                    case "sendOpenKey":
                                        android.os.Message message = android.os.Message.obtain(chatHandler,0,0,0,json);
                                        chatHandler.sendMessage(message);
                                        break;
                                    case "enter":
                                        break;
                                }

                                cleanup = true;

                            }
                        } catch (JsonSyntaxException e) {
                            //not full json, continue
                        }
                    }
                }
            } while (!stop);
            mStream.close();
            mSocket.close();
            System.out.println("Connection closed from server side. Good bye!");
        }
        catch (SocketException e) {
            //connection is closed;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}