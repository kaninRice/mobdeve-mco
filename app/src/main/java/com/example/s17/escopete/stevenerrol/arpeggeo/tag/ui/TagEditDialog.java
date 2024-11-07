package com.example.s17.escopete.stevenerrol.arpeggeo.tag.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TagEditDialog extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_tag_edit, container, false);
        initializeDialog(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            /* Adjust fragment up when keyboard is opened */
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    public void initializeDialog(View v) {
        AppCompatButton buttonConfirmView = v.findViewById(R.id.button_confirm);
        AppCompatButton buttonRemoveView = v.findViewById(R.id.button_remove);
        AppCompatButton buttonCancelView = v.findViewById(R.id.button_cancel);

        buttonConfirmView.setOnClickListener(this::confirmEntry);
        buttonRemoveView.setOnClickListener(this::removeEntry);
        buttonCancelView.setOnClickListener(this::cancelEntry);
    }

    public void confirmEntry(View v) {
        /* TODO: update tag based on entry */
        dismiss();
    }

    public void removeEntry(View v) {
        /* TODO: delete tag */
        dismiss();
    }

    public void cancelEntry(View v) {
        dismiss();
    }
}
