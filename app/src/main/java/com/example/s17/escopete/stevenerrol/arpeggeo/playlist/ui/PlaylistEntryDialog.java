package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PlaylistEntryDialog extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_playlist_entry, container, false);
        initializeDialog(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            // Adjust fragment up when keyboard is opened
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    public void initializeDialog(View v) {
        AppCompatButton buttonAddView = v.findViewById(R.id.button_add);
        AppCompatButton buttonCancelView = v.findViewById(R.id.button_cancel);

        buttonAddView.setOnClickListener(this::addEntry);
        buttonCancelView.setOnClickListener(this::cancelEntry);
    }

    public void addEntry(View v) {
        // TODO: add playlist to local storage
        dismiss();
    }

    public void cancelEntry(View v) {
        dismiss();
    }
}
