package com.example.mynote.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insert(NoteData noteData);

    @Query("SELECT * FROM notes_table")
    LiveData<List<NoteData>> getAll();

    @Query("DELETE FROM notes_table")
    void deleteAll();

    @Update
    void update(NoteData noteData);

    @Delete
    void delete(NoteData noteData);
}