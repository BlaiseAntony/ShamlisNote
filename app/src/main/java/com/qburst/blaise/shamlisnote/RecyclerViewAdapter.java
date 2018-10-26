package com.qburst.blaise.shamlisnote;

import android.support.annotation.NonNull;
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

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                        holder.head.setText(task.getResult().getString("title"));
                        holder.content.setText(task.getResult().getString("body"));
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
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.heading);
            content = itemView.findViewById(R.id.body);
        }
    }
}