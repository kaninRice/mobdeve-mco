package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TagEntryDialog extends BottomSheetDialogFragment {
    AppCompatButton buttonAdd;
    AppCompatButton buttonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_tag_entry, container, false);
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
        buttonAdd = v.findViewById(R.id.button_add);
        buttonCancel = v.findViewById(R.id.button_cancel);

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
        dismiss();
    }

    public void cancelEntry(View v) {
        dismiss();
    }
}
