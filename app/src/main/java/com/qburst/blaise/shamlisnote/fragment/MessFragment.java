package com.qburst.blaise.shamlisnote.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.qburst.blaise.shamlisnote.R;
import com.qburst.blaise.shamlisnote.databasehelper.Database;
import com.qburst.blaise.shamlisnote.model.MessNote;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import static com.qburst.blaise.shamlisnote.activity.MainActivity.MESS;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.calendar;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.fragment_id;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.isDialogueBox;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.messNotePosition;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.messOrNote;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.monthforSpinner;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.newEntry;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.year;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.day;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.month;

public class MessFragment extends Fragment implements MessNoteRecyclerViewAdapter.CustomListener, View.OnClickListener,DatePickerDialog.OnDateSetListener {
    TextView date;
    EditText item;
    EditText price;
    CardView cardView;
    RecyclerView recyclerView;
    TextView textView;
    Database db;
    ConstraintLayout constraintLayout;
    ConstraintLayout header;
    FloatingActionButton fab;
    Context c;
    TextView total;
    Spinner spinner;
    int totalSpent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mess_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        spinner = view.findViewById(R.id.spinner);
        total = view.findViewById(R.id.textView5);
        header = view.findViewById(R.id.header);
        recyclerView = view.findViewById(R.id.recyclerview);
        date = view.findViewById(R.id.date);
        date.setOnClickListener(this);
        item = view.findViewById(R.id.item);
        price = view.findViewById(R.id.price);
        cardView = view.findViewById(R.id.addMessNoteContainer);
        textView = view.findViewById(R.id.delete);
        constraintLayout = view.findViewById(R.id.container);
        fab = getActivity().findViewById(R.id.fab);
        fillRecyclerView(view);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthforSpinner = position;
                totalSpent=0;
                fillRecyclerView(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragment_id = MESS;
        messOrNote = MESS;
        db = new Database(context);
    }

    private void fillRecyclerView(View view) {
        year = calendar.get(Calendar.YEAR);
        c = getActivity();
        spinner.setSelection(monthforSpinner);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        Database db = new Database(c);
        Log.e("hi2", String.valueOf(monthforSpinner));
        List<MessNote> messNote = db.getAllMessNotes(monthforSpinner +1,year);
        List<MessNote> messNotes = messNote.subList(0,messNote.size());
        for(int i=0;i<messNotes.size();i++){
            totalSpent+=messNotes.get(i).getPrice();
        }
        total.setText("Total spent = "+totalSpent);
        Collections.reverse(messNotes);
        MessNoteRecyclerViewAdapter adapter = new MessNoteRecyclerViewAdapter(messNotes,c,this);
        recyclerView.setAdapter(adapter);
    }

    public String getDate() {
        return date.getText().toString();
    }

    public String getItem() {
        return item.getText().toString();
    }

    public String getPrice() {
        return price.getText().toString();
    }

    public void setDialogBoxVisibleWithOutDeleteButton() {
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = (calendar.get(Calendar.MONTH)+1);
        year = calendar.get(Calendar.YEAR);
        cardView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        date.setText(day+"/"+month+"/"+year);
        constraintLayout.setBackgroundColor(getResources().getColor(R.color.tblack));
        fab.setVisibility(View.INVISIBLE);
        header.setVisibility(View.INVISIBLE);
        isDialogueBox = true;
    }

    public void setDialogBoxVisibleWithDeleteButton(MessNote n) {
        setDialogBoxVisibleWithOutDeleteButton();
        textView.setVisibility(View.VISIBLE);
        date.setText(n.getDay()+"/"+n.getMonth()+"/"+n.getYear());
        item.setText(n.getItem());
        price.setText(String.valueOf(n.getPrice()));
    }

    @Override
    public void clicked(int id) {
        MessNote n = db.getMessNote(id);
        setDialogBoxVisibleWithDeleteButton(n);
        newEntry = false;
        messNotePosition = id;
    }

    @Override
    public void onDateSet(DatePicker view, int yea, int mont, int dayOfMonth) {
        year=yea;
        month=mont+1;
        day=dayOfMonth;
        date.setText(dayOfMonth+"/"+month+"/"+year);
    }

    @Override
    public void onClick(View v) {
        calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(c, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
        calendar = Calendar.getInstance();
    }
}
