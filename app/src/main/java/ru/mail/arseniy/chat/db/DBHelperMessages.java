package ru.mail.arseniy.chat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * База данных сообщений
 */

public class DBHelperMessages extends SQLiteOpenHelper {


    public DBHelperMessages(Context context) {
        super(context, "messages", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE MESSAGES (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "message TEXT," +
                "author TEXT," +
                "recipient TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
