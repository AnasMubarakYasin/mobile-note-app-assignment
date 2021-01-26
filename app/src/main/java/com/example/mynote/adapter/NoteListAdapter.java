package com.example.mynote.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynote.data.NoteData;
import com.example.mynote.databinding.FragmentNoteItemBinding;
import com.google.android.material.card.MaterialCardView;


import org.jetbrains.annotations.NotNull;

public class NoteListAdapter extends ListAdapter<NoteData, NoteListAdapter.ViewHolder> {

    private ViewHolder.OnClickListener onClickListener = (view, viewHolder) -> {};

    public NoteListAdapter(DiffItemCallback diffItemCallback) {
        super(diffItemCallback);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, int position) {
        NoteData noteData = getItem(position);

        holder.bind(noteData);
        holder.setOnClickListener(onClickListener);
    }

    public void setOnClickListener(ViewHolder.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentNoteItemBinding binding;
        private NoteData data;

        public NoteData getData() {
            return data;
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            binding.container.setOnClickListener(v -> {
                onClickListener.call(binding.container, this);
            });
        }

        public ViewHolder(FragmentNoteItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void  bind(NoteData data) {
            this.data = data;

            onBind();
        }

        public void  onBind() {
            binding.titleNote.setText(data.getTitle());
            binding.contentNote.setText(data.getContent());
        }

        public static ViewHolder create(ViewGroup parent) {
            return new ViewHolder(
                    FragmentNoteItemBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " {data = "+ data + " }";
        }

        public interface OnClickListener {
            void call(MaterialCardView view, ViewHolder viewHolder);
        }
    }

    public static class DiffItemCallback extends DiffUtil.ItemCallback<NoteData> {
        @Override
        public boolean areContentsTheSame(@NonNull @NotNull NoteData oldItem, @NonNull @NotNull NoteData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull NoteData oldItem, @NonNull @NotNull NoteData newItem) {
            return oldItem == newItem;
        }
    }
}