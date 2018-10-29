package com.qburst.blaise.shamlisnote;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.qburst.blaise.shamlisnote.MainActivity.count;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private FirebaseFirestore db;
    private Context act;
    private String body;
    private String head;
    RecyclerViewAdapter(FirebaseFirestore db, MainActivity mainActivity) {
        this.db = db;
        this.act = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.e("position", String.valueOf(position));
        db.collection("notes").document(Integer.toString(position)).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        body = task.getResult().getString("body");
                        head = task.getResult().getString("title");
                        holder.head.setText(head);
                        if (body.length()>101) {
                            String str = body.substring(0,100)+"...";
                            holder.content.setText(str);
                        }
                        else {
                            holder.content.setText(body);
                        }
                    }
                });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act, AddNoteActivity.class);
                intent.putExtra("count",position);
                intent.putExtra("head",head);
                intent.putExtra("body",body);
                act.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("count", String.valueOf(count));
        return count;
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