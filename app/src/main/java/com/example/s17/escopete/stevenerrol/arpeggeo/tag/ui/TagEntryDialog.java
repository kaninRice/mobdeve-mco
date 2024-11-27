package com.example.s17.escopete.stevenerrol.arpeggeo.tag.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistDetailsActivity;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TextColor;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Dialog for displaying tag entry function
 */
@AndroidEntryPoint
public class TagEntryDialog extends BottomSheetDialogFragment {
    @Inject
    TagRepositoryImpl tagRepositoryImpl;

    private long _playlistId;
    private EditText tagNameView;

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
        View v = inflater.inflate(R.layout.dialog_tag_entry, container, false);
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
        tagNameView = v.findViewById(R.id.tag_name);

        Bundle args = getArguments();
        _playlistId = args.getLong("playlistId");

        buttonAddView.setOnClickListener(this::addEntry);
        buttonCancelView.setOnClickListener(this::cancelEntry);
    }

    /**
     * Create a {@link com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag} based on input
     * and add it to the list of tags
     * @param v The view that was clicked
     */
    public void addEntry(View v) {
        /* TODO: add option to change tag color */
        tagRepositoryImpl.insertTag(
                tagRepositoryImpl.getHighestId() + 1,
                tagNameView.getText().toString(),
                "#11AB99",
                TextColor.DARK.name(),
                _playlistId
        );

        dismiss();
    }

    /**
     * Cancel tag entry
     * @param v The view that was clicked
     */
    public void cancelEntry(View v) {
        dismiss();
    }

    /**
     * Called when the dialog is dismissed. Used to update recycler view
     * @param dialog The dialog dismissed
     */
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        if (getActivity() instanceof PlaylistDetailsActivity) {
            ((PlaylistDetailsActivity) getActivity()).updateRecyclerView();
        }
    }
}
