package com.example.mynote.ui.note;

import android.content.Context;
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

import com.example.mynote.R;
import com.example.mynote.adapter.NoteListAdapter;
import com.example.mynote.model.NoteViewModel;

/**
 * A fragment representing a list of Items.
 */
public class NoteFragment extends Fragment {

    private static final String ARG_TYPE_LAYOUT = "type-layout-grid";
    private static final int TYPE_lAYOUT_GRID = 1;
    private static final int TYPE_lAYOUT_LIST = 2;

    private int typeLayout = TYPE_lAYOUT_GRID;
    private NoteViewModel noteViewModel;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteFragment() {
    }

    @SuppressWarnings("unused")
    public static NoteFragment newInstance(int typeLayout) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE_LAYOUT, typeLayout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            typeLayout = getArguments().getInt(ARG_TYPE_LAYOUT, TYPE_lAYOUT_GRID);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_note_layout, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Log.d("NoteFragment", "onCreateView: RecyclerView");

            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (typeLayout == TYPE_lAYOUT_GRID) {
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            NoteListAdapter noteListAdapter = new NoteListAdapter(new NoteListAdapter.DiffItemCallback());

            recyclerView.setAdapter(noteListAdapter);

            noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
            noteViewModel.getAllNote().observe(getViewLifecycleOwner(), noteListAdapter::submitList);
        }
        return view;
    }
}