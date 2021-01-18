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


import org.jetbrains.annotations.NotNull;

public class NoteListAdapter extends ListAdapter<NoteData, NoteListAdapter.ViewHolder> {

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

        holder.id = noteData.getId();
        holder.title.setText(noteData.getTitle());
        holder.content.setText(noteData.getContent());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public long id;
        public final TextView title;
        public final TextView content;

        public ViewHolder(FragmentNoteItemBinding binding) {
            super(binding.getRoot());

            title = binding.titleNote;
            content = binding.contentNote;
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
            return super.toString() + " {id = "+ id +", content = '" + content.getText() + "'}";
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