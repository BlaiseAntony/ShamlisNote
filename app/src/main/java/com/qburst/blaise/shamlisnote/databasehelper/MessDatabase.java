package com.qburst.blaise.shamlisnote.databasehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qburst.blaise.shamlisnote.model.MessNote;

import java.util.ArrayList;
import java.util.List;

public class MessDatabase extends SQLiteOpenHelper {

    public MessDatabase(Context context) {
        super(context,"db1",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mess(id int primary key, date varchar(8),item varchar(20),price int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(MessNote m) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert or replace into mess values("+ m.getId()+",'"+ m.getDate()+"','"+ m.getItem()+"','"+ m.getPrice()+"')");
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from mess where id="+id);
    }

    public List<MessNote> getAllNotes() {
        SQLiteDatabase db = getReadableDatabase();
        List<MessNote> messNotes = new ArrayList<>();
        MessNote messNote;
        String query = "select * from mess";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                messNote = new MessNote(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("item")),
                        cursor.getInt(cursor.getColumnIndex("price")));
                messNotes.add(messNote);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return messNotes;
    }

    public MessNote getNote(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from mess where id="+id,null);
        if(cursor.moveToFirst()){
            return new MessNote(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("item")),
                    cursor.getInt(cursor.getColumnIndex("price")));
        }
        else {
            return null;
        }
    }
}
