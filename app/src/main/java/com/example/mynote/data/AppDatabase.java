package com.example.mynote.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = NoteData.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDAO getNoteDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    private static final RoomDatabase.Callback INIT_CALLBACK = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            executorService.execute(() -> {
                NoteDAO dao = INSTANCE.getNoteDao();

                dao.deleteAll();

                Log.d("Room", "onCreate: AppDatabase");

                NoteData[] dataset = {
                        new NoteData("Title 1", "Content 1", "", "", ""),
                        new NoteData("Title 2", "Content 2", "", "", ""),
                        new NoteData("Title 3", "Content 3", "", "", ""),
                        new NoteData("Title 4", "Content 4", "", "", "")
                };

                for (NoteData data: dataset) {
                    dao.insert(data);
                }
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
