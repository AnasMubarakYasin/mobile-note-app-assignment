package com.example.mynote.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LabelRepository {
    private final LabelDAO DAO;
    private final LiveData<List<LabelData>> ALL_LABEL;

    public LabelRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);

        DAO = database.getLabelDao();
        ALL_LABEL = DAO.selctAll();
    }

    public LiveData<List<LabelData>> getAll() {
        return ALL_LABEL;
    }

    public void insert(LabelData data) {
        AppDatabase
                .executorService
                .execute(() -> {
                    DAO.insert(data);
                });
    }
}
