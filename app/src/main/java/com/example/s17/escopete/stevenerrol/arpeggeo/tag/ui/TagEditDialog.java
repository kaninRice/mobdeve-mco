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

/**
 * Dialog for displaying tag edit function
 */
public class TagEditDialog extends BottomSheetDialogFragment {

    /**
     * Creates a view
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return The {@link View} created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_tag_edit, container, false);
        initializeDialog(v);
        return v;
    }

    /**
     * Called when dialog is started
     */
    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            /* Adjust fragment up when keyboard is opened */
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    /**
     * Initializes the dialog; sets on click listeners
     * @param v The view to be initialized
     */
    public void initializeDialog(View v) {
        AppCompatButton buttonConfirmView = v.findViewById(R.id.button_confirm);
        AppCompatButton buttonRemoveView = v.findViewById(R.id.button_remove);
        AppCompatButton buttonCancelView = v.findViewById(R.id.button_cancel);

        buttonConfirmView.setOnClickListener(this::confirmEntry);
        buttonRemoveView.setOnClickListener(this::removeEntry);
        buttonCancelView.setOnClickListener(this::cancelEntry);
    }

    /**
     * Updates tag based on input
     * @param v The view that was clicked
     */
    public void confirmEntry(View v) {
        /* TODO: update tag based on entry */
        dismiss();
    }

    /**
     * Removes tag from the list of tags
     * @param v The view that was clicked
     */
    public void removeEntry(View v) {
        /* TODO: delete tag */
        dismiss();
    }

    /**
     *  Cancels tag editing
     * @param v The view that was clicked
     */
    public void cancelEntry(View v) {
        dismiss();
    }
}
