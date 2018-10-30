package com.qburst.blaise.shamlisnote;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context,"note",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table note(id int primary key, head varchar(50),body varchar(1000))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert or replace into note values("+note.getId()+",'"+note.getHead()+"','"+note.getBody()+"')");
    }

    public void delete(int count) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from note where id="+count);
    }

    public List<Note> getAllNotes() {
        SQLiteDatabase db = getReadableDatabase();
        List<Note> notes = new ArrayList<>();
        Note note;
        String query = "select * from note";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                note = new Note(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("head")),
                        cursor.getString(cursor.getColumnIndex("body")));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    public Note getNote(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from note where id="+id,null);
        if(cursor.moveToFirst()){
            return new Note(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("head")),
                    cursor.getString(cursor.getColumnIndex("body")));
        }
        else {
            return null;
        }
    }
}
