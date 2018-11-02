package com.qburst.blaise.shamlisnote.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qburst.blaise.shamlisnote.R;
import com.qburst.blaise.shamlisnote.databasehelper.MessDatabase;
import com.qburst.blaise.shamlisnote.model.MessNote;

import java.util.Collections;
import java.util.List;

import static com.qburst.blaise.shamlisnote.activity.MainActivity.MESS;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.fragment_id;

public class MessFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mess_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fillRecyclerView(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragment_id = MESS;
    }

    private void fillRecyclerView(View view) {
        Context c = getActivity();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        MessDatabase db = new MessDatabase(c);
        List<MessNote> messNote = db.getAllNotes();
        List<MessNote> messNotes = messNote.subList(0,messNote.size());
        Collections.reverse(messNotes);
        MessNoteRecyclerViewAdapter adapter = new MessNoteRecyclerViewAdapter(messNotes,c);
        recyclerView.setAdapter(adapter);
    }

}
