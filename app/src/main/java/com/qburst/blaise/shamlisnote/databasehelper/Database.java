package com.qburst.blaise.shamlisnote.databasehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qburst.blaise.shamlisnote.model.MessNote;
import com.qburst.blaise.shamlisnote.model.MyNote;

import java.util.ArrayList;
import java.util.List;

import static com.qburst.blaise.shamlisnote.activity.MainActivity.messNoteCount;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.noteCount;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context,"db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists mess(id int primary key, date varchar(8), item varchar(20), price int);");
        db.execSQL("create table if not exists note(id int primary key, head varchar(20), body varchar(1000));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertMess(MessNote m) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert or replace into mess values("+ m.getId()+",'"+ m.getDate()+"','"+ m.getItem()+"','"+ m.getPrice()+"')");
    }

    public void deleteMess(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from mess where id="+id);
    }

    public List<MessNote> getAllMessNotes() {
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

    public MessNote getMessNote(int id) {
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

    public void findCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("mess",new String[]{"max(id) as max"},null,null,null,null,null);
        cursor.moveToFirst();
        messNoteCount = cursor.getInt(cursor.getColumnIndex("max"));
        cursor.close();
        Cursor cursor1 = db.query("note",new String[]{"max(id) as max"},null,null,null,null,null);
        cursor1.moveToFirst();
        noteCount = cursor1.getInt(cursor1.getColumnIndex("max"));
        cursor1.close();

    }
}
