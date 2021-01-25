package com.example.mynote.data;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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

    public LiveData<NoteData> getById(long id) {
        return dao.getById(id);
    }

    public CompletableFuture<Long> insert(NoteData noteData) {
        return CompletableFuture.supplyAsync(() -> dao.insert(noteData), AppDatabase.executorService);
    }

    public void delete(NoteData noteData) {
        AppDatabase.executorService.execute(() -> {
            dao.delete(noteData);
        });
    }
}
