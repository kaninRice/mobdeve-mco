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

/**
 * Dialog for displaying playlist entry function
 */
public class PlaylistEntryDialog extends BottomSheetDialogFragment {

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
        View v = inflater.inflate(R.layout.dialog_playlist_entry, container, false);
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
            // Adjust fragment up when keyboard is opened
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    /**
     * Initializes the dialog; sets on click listeners
     * @param v The view to be initialized
     */
    public void initializeDialog(View v) {
        AppCompatButton buttonAddView = v.findViewById(R.id.button_add);
        AppCompatButton buttonCancelView = v.findViewById(R.id.button_cancel);

        buttonAddView.setOnClickListener(this::addEntry);
        buttonCancelView.setOnClickListener(this::cancelEntry);
    }

    /**
     * Create a {@link com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.Playlist} based on input
     * and add it to the list of tags
     * @param v The view that was clicked
     */
    public void addEntry(View v) {
        // TODO: add playlist to local storage
        dismiss();
    }

    /**
     * Cancel playlist entry
     * @param v The view that was clicked
     */
    public void cancelEntry(View v) {
        dismiss();
    }
}
