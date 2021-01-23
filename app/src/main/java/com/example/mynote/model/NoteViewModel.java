package com.example.mynote.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mynote.data.NoteData;
import com.example.mynote.data.NoteRepository;
import com.example.mynote.ui.note.NoteFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<NoteData>> allNote;
    private MutableLiveData<Integer> typeLayout;

    public NoteViewModel(@NonNull @NotNull Application application) {
        super(application);

        this.noteRepository = new NoteRepository(application);
        this.allNote = noteRepository.getAll();

        typeLayout = new MutableLiveData<>(NoteFragment.TYPE_lAYOUT_GRID);
    }

    public LiveData<List<NoteData>> getAllNote() {
        return this.allNote;
    }

    public void setTypeLayout(int value) {
        typeLayout.postValue(value);

        Log.d("NoteViewModel", "setTypeLayout: to :"+ value);
    }

    public LiveData<Integer> getTypeLayout() {
        return this.typeLayout;
    }

    public void insert(NoteData noteData) {
        this.noteRepository.insert(noteData);
    }
}
