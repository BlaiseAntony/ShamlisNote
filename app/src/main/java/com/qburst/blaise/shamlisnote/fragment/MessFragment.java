package com.qburst.blaise.shamlisnote.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qburst.blaise.shamlisnote.R;
import com.qburst.blaise.shamlisnote.databasehelper.MessDatabase;
import com.qburst.blaise.shamlisnote.model.MessNote;

import java.util.Collections;
import java.util.List;

import static com.qburst.blaise.shamlisnote.activity.MainActivity.MESS;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.fragment_id;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.messNotePosition;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.newEntry;

public class MessFragment extends Fragment implements MessNoteRecyclerViewAdapter.CustomListener {
    EditText date;
    EditText item;
    EditText price;
    CardView cardView;
    RecyclerView recyclerView;
    TextView textView;
    MessDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mess_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fillRecyclerView(view);
        date = view.findViewById(R.id.date);
        item = view.findViewById(R.id.item);
        price = view.findViewById(R.id.price);
        cardView = view.findViewById(R.id.addMessNoteContainer);
        recyclerView = view.findViewById(R.id.recyclerview);
        textView = view.findViewById(R.id.delete);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragment_id = MESS;
        db = new MessDatabase(context);
    }

    private void fillRecyclerView(View view) {
        Context c = getActivity();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        MessDatabase db = new MessDatabase(c);
        List<MessNote> messNote = db.getAllNotes();
        List<MessNote> messNotes = messNote.subList(0,messNote.size());
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
        cardView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    public void setDialogBoxVisibleWithDeleteButton(MessNote n) {
        cardView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        date.setText(n.getDate());
        item.setText(n.getItem());
        price.setText(String.valueOf(n.getPrice()));
    }

    @Override
    public void clicked(int id) {
        MessNote n = db.getNote(id);
        setDialogBoxVisibleWithDeleteButton(n);
        newEntry = false;
        messNotePosition = id;
    }
}
