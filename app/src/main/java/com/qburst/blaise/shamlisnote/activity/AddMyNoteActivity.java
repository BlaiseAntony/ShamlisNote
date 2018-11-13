package com.qburst.blaise.shamlisnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.qburst.blaise.shamlisnote.databasehelper.Database;
import com.qburst.blaise.shamlisnote.model.MyNote;
import com.qburst.blaise.shamlisnote.R;

import static com.qburst.blaise.shamlisnote.activity.MainActivity.noteCount;

public class AddMyNoteActivity extends AppCompatActivity{

    EditText head;
    EditText content;
    int id;
    String body;
    String heading;
    Database db;
    MenuItem save;
    MenuItem edit;
    MenuItem delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        head = findViewById(R.id.heading);
        content = findViewById(R.id.body);
        db = new Database(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_toolbar_menu, menu);
        save = menu.findItem(R.id.save);
        edit = menu.findItem(R.id.edit);
        delete = menu.findItem(R.id.delete);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        if(id != -1) {
            MyNote n = db.getNote(id);
            if(n != null) {
                heading = n.getHead();
                body = n.getBody();
                head.setText(heading);
                content.setText(body);
            }
            head.setInputType(InputType.TYPE_NULL);
            content.setInputType(InputType.TYPE_NULL);
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void storeData(MenuItem item) {
        if(head.getText().toString().equals("")) {
            Toast.makeText(this, "The Title cannot be empty",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            int c = 0;
            if(id == -1) {
                c = noteCount++;
            }
            else {
                c = id;
            }
            MyNote n = new MyNote(c,head.getText().toString(),content.getText().toString());
            db.insert(n);
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void deleteNote(MenuItem item) {
        db.delete(id);
        onBackPressed();
    }

    public void editNote(MenuItem item) {
        head.setInputType(InputType.TYPE_CLASS_TEXT);
        content.setInputType(InputType.TYPE_CLASS_TEXT);
        save.setVisible(true);
        delete.setVisible(false);
        edit.setVisible(false);
    }
}