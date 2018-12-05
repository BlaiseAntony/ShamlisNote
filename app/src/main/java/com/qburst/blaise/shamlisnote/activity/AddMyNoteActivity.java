package com.qburst.blaise.shamlisnote.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
        this.setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
            head.setEnabled(false);
            content.setEnabled(false);
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

    public void deleteNote(MenuItem item) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        db.delete(id);
                        onBackPressed();

                    case DialogInterface.BUTTON_NEGATIVE:
                        onBackPressed();
                }
            }
        };
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void editNote(MenuItem item) {
        head.setEnabled(true);
        content.setEnabled(true);
        save.setVisible(true);
        delete.setVisible(false);
        edit.setVisible(false);
    }
}