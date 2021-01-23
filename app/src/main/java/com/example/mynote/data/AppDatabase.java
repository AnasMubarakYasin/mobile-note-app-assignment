package com.example.mynote.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {NoteData.class, LabelData.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDAO getNoteDao();
    public abstract LabelDAO getLabelDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    private static final RoomDatabase.Callback INIT_CALLBACK = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            executorService.execute(() -> {
                NoteDAO noteDao = INSTANCE.getNoteDao();
                LabelDAO labelDao = INSTANCE.getLabelDao();

                noteDao.deleteAll();
                labelDao.deleteAll();

                NoteData[] noteData = {
                        new NoteData("Title 1", "Content 1", "", "", ""),
                        new NoteData("Title 2", "Content 2", "", "", ""),
                        new NoteData("Title 3", "Content 3", "", "", ""),
                        new NoteData("Title 4", "Content 4", "", "", "")
                };

                List<LabelData> labelData = Arrays.asList(
                        new LabelData("Label 1"),
                        new LabelData("Label 2"),
                        new LabelData("Label 3")
                );

                for (NoteData data: noteData) {
                    noteDao.insert(data);
                }

                labelDao.insertAll(labelData);

            });
        }
    };

    public static final ExecutorService executorService = Executors
            .newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,"app_database"
                            )
                            .addCallback(INIT_CALLBACK)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
