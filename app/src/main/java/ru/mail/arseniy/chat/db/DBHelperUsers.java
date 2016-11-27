package ru.mail.arseniy.chat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Локальная база данных для хранения юзеров и пришедших сообщений
 */

public class DBHelperUsers extends SQLiteOpenHelper {
    public DBHelperUsers(Context context) {
        super(context, "users", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE USERS (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "login TEXT" +
                "status_online BOOLEAN" +
                "open_key INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
