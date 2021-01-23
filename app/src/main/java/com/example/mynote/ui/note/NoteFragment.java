package com.example.mynote.ui.note;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mynote.adapter.NoteListAdapter;
import com.example.mynote.databinding.FragmentNoteBinding;
import com.example.mynote.model.NoteViewModel;

/**
 * A fragment representing a list of Items.
 */
public class NoteFragment extends Fragment {

    public static final String ARG_TYPE_LAYOUT = "type-layout-grid";
    public static final int TYPE_lAYOUT_GRID = 1;
    public static final int TYPE_lAYOUT_LIST = 2;
    public static final int DEF_TYPE_LAYOUT = TYPE_lAYOUT_GRID;

    private NoteViewModel viewModel;
    private FragmentNoteBinding binding;

    public NoteFragment() {
    }

    @SuppressWarnings("unused")
    public static NoteFragment newInstance(int typeLayout) {
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(NoteFragment.createArg(typeLayout));
        return fragment;
    }

    public static NoteFragment newInstance(Bundle arg) {
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    public static Bundle createArg(int typeLayout) {
        Bundle bundle = new Bundle();

        bundle.putInt(ARG_TYPE_LAYOUT, typeLayout);

        return bundle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentNoteBinding.inflate(getLayoutInflater(), container, false);

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        RecyclerView view = binding.noteContainer;

        NoteListAdapter noteListAdapter = new NoteListAdapter(new NoteListAdapter.DiffItemCallback());

        view.setAdapter(noteListAdapter);

        viewModel.getTypeLayout().observe(getViewLifecycleOwner(), this::changeLayout);
        viewModel.getAllNote().observe(getViewLifecycleOwner(), noteListAdapter::submitList);

        return view;
    }

    private void changeLayout(int typeLayout) {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        if (typeLayout == TYPE_lAYOUT_LIST) {
            layoutManager = new LinearLayoutManager(getContext());
        }

        Log.d("NoteFragment", "changeLayout: "+ typeLayout);

        binding.noteContainer.setLayoutManager(layoutManager);
    }

    public void setTypeLayout(int value) {
        viewModel.setTypeLayout(value);
    }
}