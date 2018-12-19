package com.zoyberlo.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Версия базы данных
    private static final int DATABASE_VERSION = 1;

    // Название базы данных
    private static final String DATABASE_NAME = "active_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Создание таблиц
    @Override
    public void onCreate(SQLiteDatabase db) {

        // создаем таблицу
        db.execSQL(Active.CREATE_TABLE);
    }

    // Обновление базы данных
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Понижаем старую таблицу, если существует
        db.execSQL("DROP TABLE IF EXISTS " + Active.TABLE_NAME);

        // Создаем таблицу снова
        onCreate(db);
    }

    public long insertActive(String active) {
        // получить доступную для записи базу данных, поскольку мы хотим записать туда данные активности
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // id` и` timestamp` будут вставлены автоматически.
        values.put(Active.COLUMN_ACTIVE, active);

        // вставляем строку
        long id = db.insert(Active.TABLE_NAME, null, values);

        // закрываем db
        db.close();

        //вернуть вновь вставленный идентификатор строки
        return id;
    }

    public Active getActive(long id) {
        // получать читаемую базу данных, поскольку мы ничего не вставляем
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Active.TABLE_NAME,
                new String[]{Active.COLUMN_ID, Active.COLUMN_ACTIVE, Active.COLUMN_TIMESTAMP},
                Active.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // подготовить объект
        Active active = new Active(
                cursor.getInt(cursor.getColumnIndex(Active.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Active.COLUMN_ACTIVE)),
                cursor.getString(cursor.getColumnIndex(Active.COLUMN_TIMESTAMP)));

        // закрыть бд
        cursor.close();

        return active;
    }

    public List<Active> getAllActives() {
        List<Active> actives = new ArrayList<>();

        //Выбрать все запросы
        String selectQuery = "SELECT  * FROM " + Active.TABLE_NAME + " ORDER BY " +
                Active.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // циклическое перемещение по всем строкам и добавление в список
        if (cursor.moveToFirst()) {
            do {
                Active active = new Active();
                active.setId(cursor.getInt(cursor.getColumnIndex(Active.COLUMN_ID)));
                active.setActive(cursor.getString(cursor.getColumnIndex(Active.COLUMN_ACTIVE)));
                active.setTimestamp(cursor.getString(cursor.getColumnIndex(Active.COLUMN_TIMESTAMP)));

                actives.add(active);
            } while (cursor.moveToNext());
        }

        db.close();

        // возвращает список активности
        return actives;
    }

    public int getActivesCount() {
        String countQuery = "SELECT  * FROM " + Active.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

}