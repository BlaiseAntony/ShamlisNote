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

import com.qburst.blaise.shamlisnote.R;
import com.qburst.blaise.shamlisnote.databasehelper.MessDatabase;
import com.qburst.blaise.shamlisnote.model.MessNote;

import static com.qburst.blaise.shamlisnote.activity.MainActivity.messNoteCount;

public class AddMessNoteActivity extends AppCompatActivity {

    EditText editTextDate;
    EditText editTextItem;
    EditText editTextPrice;
    int price;
    int id;
    String date;
    String item;
    MessDatabase db;
    MenuItem save;
    MenuItem edit;
    MenuItem delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mess_note_activity);
        editTextDate = findViewById(R.id.date);
        editTextItem = findViewById(R.id.item);
        editTextPrice = findViewById(R.id.price);
        db = new MessDatabase(this);
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
            MessNote n = db.getNote(id);
            if(n != null) {
                date = n.getDate();
                item = n.getItem();
                price = n.getPrice();
                editTextDate.setText(date);
                editTextItem.setText(item);
                editTextPrice.setText(String.valueOf(price));
            }
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void storeData(MenuItem item) {
        if(editTextDate.getText().toString().equals("") &&
                editTextItem.getText().toString().equals("") &&
                editTextPrice.getText().toString().equals("")) {
            Toast.makeText(this, "The fields cannot be empty",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            int c = 0;
            if(id == -1) {
                c = messNoteCount++;
            }
            else {
                c = id;
            }
            MessNote n = new MessNote(c,editTextDate.getText().toString(),
                    editTextItem.getText().toString(),
                    Integer.parseInt(editTextPrice.getText().toString()));
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
        editTextDate.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextItem.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextPrice.setInputType(InputType.TYPE_CLASS_TEXT);
        save.setVisible(true);
        delete.setVisible(false);
        edit.setVisible(false);
    }
}
