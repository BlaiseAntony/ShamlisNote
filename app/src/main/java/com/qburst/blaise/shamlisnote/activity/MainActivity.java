package com.qburst.blaise.shamlisnote.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.qburst.blaise.shamlisnote.R;
import com.qburst.blaise.shamlisnote.databasehelper.Database;
import com.qburst.blaise.shamlisnote.fragment.BackupFragment;
import com.qburst.blaise.shamlisnote.fragment.ContactFragment;
import com.qburst.blaise.shamlisnote.fragment.MessFragment;
import com.qburst.blaise.shamlisnote.fragment.MyNoteFragment;
import com.qburst.blaise.shamlisnote.fragment.SettingsFragment;
import com.qburst.blaise.shamlisnote.model.MessNote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EasyPermissions.PermissionCallbacks {

    public static boolean isDialogueBox = false;
    public static int noteCount;
    public static int messNoteCount;
    SharedPreferences preferences;
    public static int fragment_id;
    public static int messOrNote;
    public static final int MY_NOTE = 99;
    public static final int MESS = 100;
    public static final int BACKUP = 101;
    public static final int CONTACTUS = 102;
    public static final int SETTINGS = 103;
    public static Calendar calendar;
    private FloatingActionButton fab;
    Database db;
    public static boolean newEntry;
    public static int messNotePosition;
    public static int day;
    public static int month;
    public static int year;
    public static int monthforSpinner;


    @Override
    protected void onStop() {
        super.onStop();
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
        else if(fragment_id == BACKUP) {
            importExport();
        }
        else if(fragment_id == CONTACTUS) {
            contactUs();
        }
        else if(fragment_id == SETTINGS) {
            settings();
        }
        super.onResume();
    }

    public void setActionBarTitle(String title) {
        this.setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
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
                    calendar = Calendar.getInstance();
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
        checkPermission();
        displayNotes();
        navigationView.getMenu().findItem(R.id.mynotes).setChecked(true);
    }

    public boolean check(String[] permission) {
        return EasyPermissions.hasPermissions(this, permission);
    }

    public void checkPermission(){
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(!check(permission)) {
            EasyPermissions.requestPermissions(this,
                    "Storage is needed for backing up your data",
                    1, permission);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (isDialogueBox) {
            isDialogueBox = false;
            onResume();
        }
        else if (fragment_id == SETTINGS || fragment_id == BACKUP || fragment_id == CONTACTUS) {
            fragment_id = messOrNote;
            onResume();
        }
        else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mynotes) {
            displayNotes();
        }
        else if (id == R.id.messnotes) {
            monthforSpinner = calendar.get(Calendar.MONTH);
            Log.e("hi", String.valueOf(monthforSpinner));
            displayMessNotes();
        }
        else if (id == R.id.importexport) {
            importExport();
        }
        else if (id == R.id.settings) {
            settings();
        }
        else if (id == R.id.contactus) {
            contactUs();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void settings() {
        fab.setVisibility(View.INVISIBLE);
        setActionBarTitle("Settings");
        SettingsFragment f = new SettingsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
    }

    private void contactUs() {
        fab.setVisibility(View.INVISIBLE);
        setActionBarTitle("Contact Us");
        ContactFragment f = new ContactFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
    }

    private void displayNotes() {
        fab.setVisibility(View.VISIBLE);
        setActionBarTitle("My Notes");
        MyNoteFragment f = new MyNoteFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
    }

    private void importExport() {
        fab.setVisibility(View.INVISIBLE);
        setActionBarTitle("Backup and Restore");
        BackupFragment f= new BackupFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
    }

    private MessFragment messFragment= null;
    private void displayMessNotes() {
        fab.setVisibility(View.VISIBLE);
        setActionBarTitle("Mess Notes");
        MessFragment f= new MessFragment();
        this.messFragment = f;
        getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
    }

    public void backup(View view) {
        try {
            String inFileName = "/data/data/com.qburst.blaise.shamlisnote/databases/db";
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ShamlisNote/Backup";
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();
            String outFileName = path + "/note";
            OutputStream output = new FileOutputStream(outFileName,false);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();fis.close();
            Toast.makeText(this,"Successfully backed up the data",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Backup failed",Toast.LENGTH_SHORT).show();
        }
    }

    public void restore(View view) {
        try {
            String inFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ShamlisNote/Backup/note";
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);
            String path = "/data/data/com.qburst.blaise.shamlisnote/databases";
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();
            String outFileName = path + "/db";
            OutputStream output = new FileOutputStream(outFileName,false);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
            Toast.makeText(this,"Successfully restored the data",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Restore failed",Toast.LENGTH_SHORT).show();
        }
        db = new Database(this);
        db.findCount();
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
        db = new Database(this);
        if(date.equals("") || item.equals("") || price.equals("")) {
            Toast.makeText(this, "The fields cannot be empty",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            MessNote n = new MessNote(id, item, Integer.parseInt(price), year, month, day);
            db.insertMess(n);
            onResume();
        }
    }

    public void storeData(View view) {
        isDialogueBox = false;
        store();
    }

    public void deleteNote(View view) {
        db = new Database(this);
        db.deleteMess(messNotePosition);
        isDialogueBox = false;
        onResume();
    }

    public void discard(View view){
        isDialogueBox = false;
        onResume();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}