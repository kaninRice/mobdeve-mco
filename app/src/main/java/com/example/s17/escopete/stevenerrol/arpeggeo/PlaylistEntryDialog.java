package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PlaylistEntryDialog extends BottomSheetDialogFragment {
    AppCompatButton buttonAdd;
    AppCompatButton buttonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_playlist_entry, container, false);
        getDialog().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        initializeDialog(v);
        return v;
    }

    public void initializeDialog(View v) {
        buttonAdd = v.findViewById(R.id.buttonAdd);
        buttonCancel = v.findViewById(R.id.buttonCancel);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEntry(view);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelEntry(view);
            }
        });
    }

    public void addEntry(View v) {
        /* TODO */
        dismiss();
    }

    public void cancelEntry(View v) {
        dismiss();
    }
}
