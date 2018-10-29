package com.qburst.blaise.shamlisnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.qburst.blaise.shamlisnote.MainActivity.count;

public class AddNoteActivity extends AppCompatActivity{

    EditText head;
    EditText content;
    int countPassed;
    FirebaseFirestore db;
    String body;
    String heading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        db = FirebaseFirestore.getInstance();
        head = findViewById(R.id.heading);
        content = findViewById(R.id.body);
        Intent intent = getIntent();
        countPassed = intent.getIntExtra("count",-1);
        if(countPassed != -1) {
            heading = intent.getStringExtra("head");
            body = intent.getStringExtra("body");
            head.setText(heading);
            content.setText(body);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void storeData(MenuItem item) {
        if(head.getText() == null) {
            Toast.makeText(this, "The Title cannot be empty",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Map<String, Object> note = new HashMap<>();
            note.put("title",head.getText().toString());
            note.put("body",content.getText().toString());
            if(countPassed == -1) {
                db.collection("notes").document(String.valueOf((count++))).set(note);
                DocumentReference data = db.collection("count").document("id");
                data.update("count", String.valueOf(count));
            }
            else {
                db.collection("notes").document(String.valueOf((countPassed))).set(note);
            }
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}