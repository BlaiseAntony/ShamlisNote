package com.qburst.blaise.shamlisnote.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qburst.blaise.shamlisnote.R;

import static com.qburst.blaise.shamlisnote.activity.MainActivity.BACKUP;
import static com.qburst.blaise.shamlisnote.activity.MainActivity.fragment_id;

public class BackupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.backup_fragment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragment_id = BACKUP;
    }
}
