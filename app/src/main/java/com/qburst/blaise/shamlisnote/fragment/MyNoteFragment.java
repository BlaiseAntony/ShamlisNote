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

import com.qburst.blaise.shamlisnote.model.MyNote;
import com.qburst.blaise.shamlisnote.databasehelper.MyNoteDatabase;
import com.qburst.blaise.shamlisnote.R;

import java.util.Collections;
import java.util.List;

import static com.qburst.blaise.shamlisnote.activity.MainActivity.MY_NOTE;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.fragment_id;

public class MyNoteFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragment_id = MY_NOTE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_note_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fillRecyclerView(view);
    }

    private void fillRecyclerView(View view) {
        Context c = getActivity();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        MyNoteDatabase db = new MyNoteDatabase(c);
        List<MyNote> myNote = db.getAllNotes();
        List<MyNote> myNotes = myNote.subList(0, myNote.size());
        Collections.reverse(myNotes);
        MyNoteRecyclerViewAdapter adapter = new MyNoteRecyclerViewAdapter(myNotes,c);
        recyclerView.setAdapter(adapter);
    }
}
