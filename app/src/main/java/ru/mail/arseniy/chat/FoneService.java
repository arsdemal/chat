package ru.mail.arseniy.chat;

/**
 * Фоновый процесс который делает запрос каждые n секунд и обновляет информацию
 */


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class FoneService extends Service{
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        startLoop();
    }

    @Override
    public void onCreate() {

    }

    public void startLoop() {




        /**
         * Запускаем поток в котором производим запросы
         */
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                while (true) {


                    //Todo Сформировать запрос на получение пользователей


                    //Todo Отправить btoadcast фрагментам на обновление информации


                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

            }
        });

    }


}
