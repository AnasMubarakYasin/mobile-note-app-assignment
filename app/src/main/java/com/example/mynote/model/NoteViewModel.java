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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class NoteViewModel extends AndroidViewModel {
    public static final String TAG = "NoteFragment";

    private NoteRepository noteRepository;
    private LiveData<List<NoteData>> allNote;
    private MutableLiveData<Integer> typeLayout;

    public NoteViewModel(@NonNull @NotNull Application application) {
        super(application);

        Log.d("Home", "NoteViewModel: ");

        this.noteRepository = new NoteRepository(application);
        this.allNote = noteRepository.getAll();

        typeLayout = new MutableLiveData<>(NoteFragment.TYPE_lAYOUT_GRID);
    }

    public NoteData createNote() {
        return NoteData.create();
    }

    public CompletableFuture<Long> saveNote(NoteData noteData) {
        Log.d(TAG, "saveNote: "+ noteData);

        long id = noteData.getId();
        if (!noteData.getTitle().isEmpty() && !noteData.getContent().isEmpty()) {
            if (id == 0) {
                return insert(noteData);
            } else {
                update(noteData);
            }
            Log.d(TAG, "saveNote: success");
        } else  {
            Log.d(TAG, "saveNote: failed");
        }
        return CompletableFuture.completedFuture(id);
    }

    public void deleteNote(NoteData noteData) {
        if (noteData.getId() != 0) {
            noteRepository.delete(noteData);
        }
    }

    public LiveData<List<NoteData>> getAllNote() {
        return this.allNote;
    }

    public LiveData<NoteData> getById(long id) {
        return noteRepository.getById(id);
    }

    public void setTypeLayout(int value) {
        typeLayout.postValue(value);

        Log.d("NoteViewModel", "setTypeLayout: to :"+ value);
    }

    public LiveData<Integer> getTypeLayout() {
        return this.typeLayout;
    }

    public CompletableFuture<Long> insert(NoteData noteData) {
        Log.d(TAG, "insert: "+ noteData);

        return this.noteRepository.insert(noteData);
    }
    public void update(NoteData noteData) {
        Log.d(TAG, "update: "+ noteData);

        noteRepository.update(noteData);
    }
}
