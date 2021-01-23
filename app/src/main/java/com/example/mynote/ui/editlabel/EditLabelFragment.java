package com.example.mynote.ui.editlabel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynote.MainActivity;
import com.example.mynote.adapter.EditLabeLAdapter;
import com.example.mynote.databinding.FragmentEditLabelBinding;
import com.example.mynote.model.EditLabelViewModel;

import org.jetbrains.annotations.NotNull;

public class EditLabelFragment extends Fragment {

    private EditLabelViewModel viewModel;
    private FragmentEditLabelBinding binding;

    public EditLabelFragment() {
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        viewModel = new ViewModelProvider(this).get(EditLabelViewModel.class);

        binding = FragmentEditLabelBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.contentEditLabel.listLabel;

        EditLabeLAdapter adapter = new EditLabeLAdapter(new EditLabeLAdapter.DiffItemCallback());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getAllLabel().observe(getViewLifecycleOwner(), adapter::submitList);

        binding.contentEditLabel.addLabel.setName(viewModel.getInputName().getValue());
        binding.contentEditLabel.addLabel.setEdit(true);
        binding.contentEditLabel.addLabel.inputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setInputName(s.toString());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) requireActivity();

        mainActivity.setNavigationWithToolbar(binding.toolbar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}