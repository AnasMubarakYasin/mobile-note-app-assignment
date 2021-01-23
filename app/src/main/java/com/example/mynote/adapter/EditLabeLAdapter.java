package com.example.mynote.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mynote.R;
import com.example.mynote.data.LabelData;
import com.example.mynote.databinding.FragmentEditLabelItemBinding;

import org.jetbrains.annotations.NotNull;

public class EditLabeLAdapter extends ListAdapter<LabelData, EditLabeLAdapter.ViewHolder> {

    public EditLabeLAdapter(DiffItemCallback diffItemCallback) {
        super(diffItemCallback);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LabelData data = getItem(position);

        holder.bind(data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private long id;

        private final FragmentEditLabelItemBinding binding;

        public ViewHolder(FragmentEditLabelItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(LabelData data) {
            id = data.getId();

            binding.setName(data.getName());
        }

        public static ViewHolder create(ViewGroup parent) {
            return new ViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.getContext()),
                            R.layout.fragment_edit_label_item,
                            parent,
                            false
                    )
            );
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " {id = "+ id +", content = '" + binding.inputName.getText() + "'}";
        }
    }
    public static class DiffItemCallback extends DiffUtil.ItemCallback<LabelData> {
        @Override
        public boolean areContentsTheSame(@NonNull @NotNull LabelData oldItem, @NonNull @NotNull LabelData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull LabelData oldItem, @NonNull @NotNull LabelData newItem) {
            return oldItem == newItem;
        }
    }
}