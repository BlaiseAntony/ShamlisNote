package com.qburst.blaise.shamlisnote;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context act;
    private List<Note> notes;
    private int[] id = new int[200];

    RecyclerViewAdapter(List<Note> n, MainActivity mainActivity) {
        this.act = mainActivity;
        this.notes = n;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int p) {
        final int position = p;
        Note n = notes.get(position);
        String body = n.getBody();
        String head = n.getHead();
        id[position] = n.getId();
        holder.head.setText(head);
        if (body.length()>101) {
            String str = body.substring(0,100)+"...";
            holder.content.setText(str);
        }
        else {
            holder.content.setText(body);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act, AddNoteActivity.class);
                intent.putExtra("id", id[position]);
                act.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView head;
        private TextView content;
        private CardView cardView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.heading);
            content = itemView.findViewById(R.id.body);
            cardView = itemView.findViewById(R.id.card);
        }
    }
}