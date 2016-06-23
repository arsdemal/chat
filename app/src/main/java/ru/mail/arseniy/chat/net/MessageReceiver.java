package ru.mail.arseniy.chat.net;

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
import java.util.List;

import ru.mail.arseniy.chat.Message.Message;
import ru.mail.arseniy.chat.Message.MsgReg;
import ru.mail.arseniy.chat.Message.MsgWelcome;

public class MessageReceiver implements Runnable {

    private final Socket mSocket;
    private final InputStream mStream;
    private Boolean flag;

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

    @Override
    public void run() {
        try {
            boolean stop = false;
            boolean cleanup = false;
            byte[] data = new byte[32768];
            //int offset = 0;
            JsonParser parser = new JsonParser();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JSONDataProcessor processor = new JSONDataProcessor();


            do {

                if (cleanup) {
                    outputStream.reset();
                    //offset = 0;
                    cleanup = false;
                }

                // Запись в data производится от 0
                int readBytes = mStream.read(data);
                if (readBytes != -1) {

                    outputStream.write(data, 0, readBytes);
                    //offset += readBytes;
                    outputStream.flush();
                    //outputStream.close();

                    String result = outputStream.toString("utf-8");
                    List<String> parts = processor.process(result);
                    Log.i("TAG", result);

                    for (String i: parts) {
                        if (result.endsWith("}")) {
                            try {

                                JsonElement element = parser.parse(i);
                                JsonObject json = element.getAsJsonObject();
                                String action = json.get("action").getAsString();
                                Log.i("TAG", action);
                                if (action != null) {

                                    switch (action) {
                                        case "welcome":
                                            messageListener.Welcome(json);
                                            break;
                                        case "auth":
                                            messageListener.Auth(json);
                                            break;
                                        case "register":
                                            Log.i("TAG", "led to register");
                                            messageListener.Register(json);
                                            break;
                                        case "channellist":
                                            break;
                                        case "createchannel":
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
                }
                else {
                    stop = true;
                }
            } while (!stop);
            mStream.close();
            mSocket.close();
            Log.i("TAG","Connection closed from server side. Good bye!");
        }
        catch (SocketException e) {
            //connection is closed;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
