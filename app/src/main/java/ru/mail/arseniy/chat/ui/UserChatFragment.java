package ru.mail.arseniy.chat.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Stack;

import javax.crypto.Cipher;

import ru.mail.arseniy.chat.R;
import ru.mail.arseniy.chat.action.Action;
import ru.mail.arseniy.chat.action.ActionGetOpenKey;
import ru.mail.arseniy.chat.action.ActionMsg;
import ru.mail.arseniy.chat.action.ActionSendOpenKey;
import ru.mail.arseniy.chat.net.APIService;
import ru.mail.arseniy.chat.net.MainActivity;
import ru.mail.arseniy.chat.net.MessageReceiver;
import ru.mail.arseniy.chat.net.MessageSender;

/**
 * фрагмент отображающий переписку с пользователем
 */
public class UserChatFragment extends Fragment {

    private static final String TAG = "UserChatFragment";

    private Button sendButton;
    private Button cancelButton;
    private EditText userNameToE;
    private TextView windowChat;
    private EditText editText;
    private String thisName;
    private String userNameFrom;
    private Handler chatHandler;
    private MessageSender sender;
    private MessageReceiver receiver;

    private Key publicKey = null;
    private Key privateKey = null;
    private Stack<String> messages;

    public UserChatFragment (String thisName, MessageReceiver receiver, MessageSender sender) {
        this.thisName = thisName;
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);

        userNameToE = (EditText) view.findViewById(R.id.nameSender);
        windowChat = (TextView) view.findViewById(R.id.chatWindow);
        editText = (EditText) view.findViewById(R.id.editText);
        sendButton = (Button) view.findViewById(R.id.sendMsg);
        cancelButton = (Button) view.findViewById(R.id.cancelMsg);

        messages = new Stack<>();

        /**
         *
         */


        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        } catch (Exception e) {
            Log.e("Crypto", "RSA key pair error");
        }


        /**
         *  Отправляем сообщение шифруя его с помощью закрытого ключа и открытого из базы данных
         */
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){


                MainActivity mainActivity = (MainActivity) getActivity();
                userNameFrom = mainActivity.getUserName();

                String userNameTo = userNameToE.getText().toString();

                ActionGetOpenKey action;

                if (userNameTo != null) {

                    // Кидаем сообщение в стек
                    messages.push(editText.getText().toString());

                    windowChat.setText(windowChat.getText() + "\n" + thisName + ": " +
                            editText.getText().toString());

                    // Посылаем запрос на ключ
                    Log.d(TAG,"Посылаем запрос на ключ...");
                    action = new ActionGetOpenKey(userNameTo, userNameFrom);
                    sender.setCurrentAction(action.getAction());

                }


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        } );

        chatHandler = new Handler() {
            public void handleMessage(Message message) {



                JsonObject json = (JsonObject) message.obj;
                JsonObject data = json.get("data").getAsJsonObject();

                Log.d(TAG,"Handler: " + json.toString());

                String actionType = json.get("action").getAsString();

                if (actionType.equals("send")) {

                    if (data.get("loginTo").getAsString().equals(thisName)) {

                        String s = data.get("msg").getAsString();

                        byte[] decodedBytes = null;

                        try {
                            Cipher c = Cipher.getInstance("RSA");
                            c.init(Cipher.DECRYPT_MODE, privateKey);
                            decodedBytes = c.doFinal(stringToBytes(s));
                        } catch (Exception e) {
                            Log.e("Crypto", "RSA decryption error");
                        }

                        String userNameFrom = data.get("loginFrom").getAsString();

                        String c = new String(decodedBytes);

                        windowChat.setText(windowChat.getText() + "\n" + userNameFrom +
                                ": " + c );

                    }
                } else if (actionType.equals("sendOpenKey")) {

                    if (data.get("loginTo").getAsString().equals(thisName)) {
                        byte [] byteKey = Base64.decode(data.get("openKey").getAsString().getBytes(),Base64.DEFAULT);

                        Key publicKeyOtherUser = null; // SecretKeySpec(byteKey, 0, byteKey.length, "RSA");

                        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
                        try {
                            KeyFactory kf = KeyFactory.getInstance("RSA");
                            publicKeyOtherUser = kf.generatePublic(X509publicKey);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeySpecException e) {
                            e.printStackTrace();
                        }

                        Log.d(TAG,"Приняли ключ от пользователя" + publicKeyOtherUser.toString());

                        Log.d(TAG, "Кодируем сообщение...");
                        byte[] encodedBytes = null;

                        try {
                            Cipher c = Cipher.getInstance("RSA");
                            c.init(Cipher.ENCRYPT_MODE, publicKeyOtherUser);
                            encodedBytes = c.doFinal(messages.pop().getBytes());

                        } catch (Exception e) {
                            Log.e("Crypto", "RSA encryption error");
                        }

                        String userNameTo = data.get("loginFrom").getAsString();

                        String s = bytesToString(encodedBytes);
                        Action action = new ActionMsg(userNameTo, userNameFrom, s);
                        sender.setCurrentAction(action.getAction());

                    }

                } else if (actionType.equals("getOpenKey")) {

                    if (data.get("loginTo").getAsString().equals(thisName)) {
                        Log.d(TAG, "Приняли сообщение, что нужно отправить ключ. " +
                                "Отправляем...");
                        String name = data.get("loginFrom").getAsString();

                        String publicK = Base64.encodeToString(publicKey.getEncoded(),Base64.DEFAULT);
                        Action action = new ActionSendOpenKey(name, thisName, publicK);
                        sender.setCurrentAction(action.getAction());
                    }

                }
            }
        };

        receiver.setChatHandler(chatHandler);

        return view;

    }

    public  String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }

    public  byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }

}
