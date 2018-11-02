package com.qburst.blaise.shamlisnote.databasehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qburst.blaise.shamlisnote.model.MyNote;

import java.util.ArrayList;
import java.util.List;

public class MyNoteDatabase extends SQLiteOpenHelper {

    public MyNoteDatabase(Context context) {
        super(context,"db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table note(id int primary key, head varchar(50),body varchar(1000))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(MyNote myNote) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert or replace into note values("+ myNote.getId()+",'"+ myNote.getHead()+"','"+ myNote.getBody()+"')");
    }

    public void delete(int count) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from note where id="+count);
    }

    public List<MyNote> getAllNotes() {
        SQLiteDatabase db = getReadableDatabase();
        List<MyNote> myNotes = new ArrayList<>();
        MyNote myNote;
        String query = "select * from note";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                myNote = new MyNote(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("head")),
                        cursor.getString(cursor.getColumnIndex("body")));
                myNotes.add(myNote);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return myNotes;
    }

    public MyNote getNote(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from note where id="+id,null);
        if(cursor.moveToFirst()){
            return new MyNote(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("head")),
                    cursor.getString(cursor.getColumnIndex("body")));
        }
        else {
            return null;
        }
    }
}
