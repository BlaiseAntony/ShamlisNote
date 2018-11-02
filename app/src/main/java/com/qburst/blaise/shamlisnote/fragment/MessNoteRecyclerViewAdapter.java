package com.qburst.blaise.shamlisnote.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qburst.blaise.shamlisnote.R;
import com.qburst.blaise.shamlisnote.activity.AddMessNoteActivity;
import com.qburst.blaise.shamlisnote.activity.AddMyNoteActivity;
import com.qburst.blaise.shamlisnote.model.MessNote;

import java.util.List;

class MessNoteRecyclerViewAdapter extends RecyclerView.Adapter<MessNoteRecyclerViewAdapter.ViewHolder1> {

    private Context context;
    private List<MessNote> messNotes;
    private int[] id = new int[10000];

    MessNoteRecyclerViewAdapter(List<MessNote> n, Context c) {
        this.context = c;
        this.messNotes = n;
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messnote, parent, false);
        return new ViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int p) {
        final int position = p;
        MessNote n = messNotes.get(position);
        String date = n.getDate();
        String item = n.getItem();
        String price = Integer.toString(n.getPrice());
        id[position] = n.getId();
        holder.date.setText(date);
        holder.item.setText(item);
        holder.price.setText(price);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddMessNoteActivity.class);
                intent.putExtra("id", id[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messNotes.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView item;
        private TextView price;
        private CardView card;
        public ViewHolder1(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            item = itemView.findViewById(R.id.item);
            price = itemView.findViewById(R.id.price);
            card = itemView.findViewById(R.id.card1);
        }
    }
}