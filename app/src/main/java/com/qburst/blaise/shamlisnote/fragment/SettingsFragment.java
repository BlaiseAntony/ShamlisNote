package com.qburst.blaise.shamlisnote.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qburst.blaise.shamlisnote.R;
import com.qburst.blaise.shamlisnote.databasehelper.Database;

import static com.qburst.blaise.shamlisnote.activity.MainActivity.SETTINGS;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.fragment_id;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragment_id = SETTINGS;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Context c = getContext();
        Database db = new Database(c);
        int sum = db.getSum();
        TextView textView = view.findViewById(R.id.textView);
        textView.setText("mess Sum = "+ sum);
    }
}
