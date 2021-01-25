package com.example.mynote.ui.editnote;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynote.MainActivity;
import com.example.mynote.R;
import com.example.mynote.data.NoteData;
import com.example.mynote.databinding.FragmentEditNoteBinding;
import com.example.mynote.model.EditNoteViewModel;
import com.example.mynote.model.NoteViewModel;

import java.util.Collections;

public class EditNoteFragment extends Fragment {

    public static final String TAG = "EditNoteFragment";
    public static final String DIVIDER = String.join("", Collections.nCopies(100, "-"));

    private EditNoteViewModel viewModel;
    private NoteViewModel noteViewModel;
    private FragmentEditNoteBinding binding;
    private NoteData noteData = NoteData.create();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        Log.d(TAG, DIVIDER);

        binding = FragmentEditNoteBinding.inflate(inflater, container, false);

        @NonNull EditNoteFragmentArgs args = EditNoteFragmentArgs.fromBundle(getArguments());

        viewModel = new ViewModelProvider(this).get(EditNoteViewModel.class);
        viewModel.initArgs(args);

        binding.contentEditNote.setViewModel(viewModel);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        Log.d(TAG, "onCreateView: args "+ args);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) requireActivity();

        Toolbar toolbar = binding.toolbar;

        mainActivity.setNavigationWithToolbar(toolbar);

        toolbar.post(() -> {
            initOptionMenu(toolbar);
        });

        if (!viewModel.getIsEditMode()) {
            toolbar.setTitle("Add Note");

            createNote();
        } else {
            editNote();
        }
    }

    public void initOptionMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.edit_note);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.delete_item: {
                    deleteNote();

                    return true;
                }
                default: {
                    return false;
                }
            }
        });
    }

    public void createNote() {
        Log.d(TAG, "createNote: "+ noteData);

        bindView();

        viewModel.setIsEditMode(true);
    }

    public void editNote() {
        noteViewModel
                .getById(viewModel.getId())
                .observe(getViewLifecycleOwner(), note -> {
                    noteData = note;

                    Log.d(TAG, "editNote: "+ note);

                    bindView();
                });
    }

    public void deleteNote() {
        Log.d(TAG, "deleteNote: "+ noteData);

        noteViewModel.deleteNote(noteData);

        requireActivity().onBackPressed();
    }

    public void bindView() {
        LifecycleOwner owner = getViewLifecycleOwner();

        viewModel.observerTitle().observe(owner, noteData::setTitle);
        viewModel.observerContent().observe(owner, noteData::setContent);
    }

    public void savingNote() {
        boolean isNotSaved = !viewModel.getIsSaved();

        Log.d(TAG, "savingNote: ! "+ isNotSaved);

        if (isNotSaved) {
            noteViewModel
                    .saveNote(noteData)
                    .thenApplyAsync(fieldId -> {
                        viewModel.setId(fieldId);

                        if (fieldId > 0) {
                            viewModel.setIsSaved(true);
                        } else {
                            if (viewModel.getId() == 0) {
                                viewModel.setIsEditMode(false);
                            }
                        }

                        Log.d(TAG, "saveNote: success "+ fieldId);

                        return fieldId;
                    });
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        savingNote();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: "+ viewModel);

        binding = null;
    }
}