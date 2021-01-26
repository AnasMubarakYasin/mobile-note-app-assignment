package com.example.mynote.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mynote.MainActivity;
import com.example.mynote.R;
import com.example.mynote.databinding.FragmentHomeBinding;
import com.example.mynote.model.HomeViewModel;
import com.example.mynote.ui.note.NoteFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    public static final String DIVIDER = String.join("", Collections.nCopies(100, "-"));
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.d(TAG, DIVIDER);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = binding.appBarHome.toolbar;

        Menu menu = toolbar.getMenu();

        MainActivity mainActivity = (MainActivity) requireActivity();

        mainActivity.setNavigationWithToolbar(binding.appBarHome.toolbar);

        NoteFragment noteFragment = (NoteFragment) getChildFragmentManager()
                .findFragmentById(R.id.note_container);

        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);

        int typeLayout = sharedPreferences.getInt(
                HomeViewModel.KEY_NOTE_TYPE_LAYOUT,
                HomeViewModel.DEF_NOTE_TYPE_LAYOUT
        );

        HomeFragmentDirections.ActionNavHomeToEditNote action = HomeFragmentDirections
                .actionNavHomeToEditNote();

        NavController navController = Navigation.findNavController(binding.getRoot());

        binding.appBarHome.fab.setOnClickListener(fab -> {
            navController.navigate(action);
        });

        noteFragment.setOnItemSelected((card, viewHolder) -> {
            Log.d(TAG, "onViewCreated: "+ viewHolder);

            action.setIsEdit(true);
            action.setNoteId(viewHolder.getData().getId());

            navController.navigate(action);
        });

        toolbar.post(() -> {
            initOptionMenu(toolbar);

            homeViewModel.setNoteTypeLayout(typeLayout);
            homeViewModel.getTypeLayout().observe(getViewLifecycleOwner(), integer -> {
                noteFragment.setTypeLayout(integer);

                sharedPreferences
                        .edit()
                        .putInt(HomeViewModel.KEY_NOTE_TYPE_LAYOUT, integer)
                        .apply();
            });
            homeViewModel.getNoteIconId().observe(getViewLifecycleOwner(), iconId -> {
                Log.d(TAG, "onViewCreated: set icon : "+ iconId);

                menu.findItem(R.id.note_type_layout).setIcon(iconId);
            });
        });
    }

    public void initOptionMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.home);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.note_type_layout) {
                homeViewModel.toggleNoteIcon();

                return true;
            }
            return false;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}