package com.example.mynote.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mynote.data.NoteData;
import com.example.mynote.data.NoteRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<NoteData>> allNote;

    public NoteViewModel(@NonNull @NotNull Application application) {
        super(application);

        this.noteRepository = new NoteRepository(application);
        this.allNote = noteRepository.getAll();
    }

    public LiveData<List<NoteData>> getAllNote() {
        return this.allNote;
    }

    public void insert(NoteData noteData) {
        this.noteRepository.insert(noteData);
    }
}
