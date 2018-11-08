package com.qburst.blaise.shamlisnote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.qburst.blaise.shamlisnote.R;
import com.qburst.blaise.shamlisnote.databasehelper.MessDatabase;
import com.qburst.blaise.shamlisnote.fragment.BackupFragment;
import com.qburst.blaise.shamlisnote.fragment.MessFragment;
import com.qburst.blaise.shamlisnote.fragment.MessNoteRecyclerViewAdapter;
import com.qburst.blaise.shamlisnote.fragment.MyNoteFragment;
import com.qburst.blaise.shamlisnote.model.MessNote;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int noteCount;
    public static int messNoteCount;
    SharedPreferences preferences;
    public static int fragment_id;
    public static final int MY_NOTE = 99;
    public static final int MESS = 100;
    private FloatingActionButton fab;
    MessDatabase db;
    public static boolean newEntry;
    public static int messNotePosition;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("noteCount", noteCount).apply();
        editor.putInt("messNoteCount", messNoteCount).apply();
    }

    @Override
    protected void onResume() {
        if(fragment_id == MESS) {
            displayMessNotes();
        }
        else if(fragment_id == MY_NOTE) {
            displayNotes();
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences("note", MODE_PRIVATE);
        noteCount = preferences.getInt("noteCount", 0);
        messNoteCount = preferences.getInt("messNoteCount", 0);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragment_id == MY_NOTE) {
                    Intent intent = new Intent(MainActivity.this,
                            AddMyNoteActivity.class);
                    startActivity(intent);
                } else if (fragment_id == MESS) {
                    messFragment.setDialogBoxVisibleWithOutDeleteButton();
                    newEntry = true;
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayNotes();
        navigationView.getMenu().findItem(R.id.mynotes).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mynotes) {
            fab.setVisibility(View.VISIBLE);
            displayNotes();
        }
        else if (id == R.id.messnotes) {
            fab.setVisibility(View.VISIBLE);
            displayMessNotes();
        }
        else if (id == R.id.importexport) {
            fab.setVisibility(View.INVISIBLE);
            importExport();
        }
        else if (id == R.id.settings) {

        }
        else if (id == R.id.contactus) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayNotes() {
        MyNoteFragment f = new MyNoteFragment();
          getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
    }

    private void importExport() {
        BackupFragment f= new BackupFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
    }

    private MessFragment messFragment= null;
    private void displayMessNotes() {
        MessFragment f= new MessFragment();
        this.messFragment = f;
        getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
    }

    public void backup(View view) {
        Toast.makeText(this, "Functionality will be available soon", Toast.LENGTH_SHORT).show();
    }

    public void restore(View view) {
        Toast.makeText(this, "Functionality will be available soon", Toast.LENGTH_SHORT).show();
    }

    public void store(){
        int id = 0;
        if(newEntry){
            id = messNoteCount++;
        }
        else{
            id = messNotePosition;
        }
        String date = messFragment.getDate();
        String item = messFragment.getItem();
        String price = messFragment.getPrice();
        db = new MessDatabase(this);
        if(date.equals("") && item.equals("") && price.equals("")) {
            Toast.makeText(this, "The fields cannot be empty",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            MessNote n = new MessNote(id, date, item, Integer.parseInt(price));
            db.insert(n);
            onResume();
        }
    }

    public void storeData(View view) {
        store();
    }

    public void deleteNote(View view) {
        db = new MessDatabase(this);
        db.delete(messNotePosition);
        onResume();
    }

    public void discard(View view){
        onResume();
    }
}