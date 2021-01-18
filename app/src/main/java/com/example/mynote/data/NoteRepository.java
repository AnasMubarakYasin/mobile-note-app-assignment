package com.example.mynote.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDAO dao;
    private LiveData<List<NoteData>> allNote;

    public NoteRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);

        this.dao = database.getNoteDao();
        this.allNote = this.dao.getAll();
    }

    public LiveData<List<NoteData>> getAll() {
        return this.allNote;
    }

    public void insert(NoteData noteData) {
        AppDatabase.executorService.execute(() -> {
            dao.insert(noteData);
        });
    }
}
