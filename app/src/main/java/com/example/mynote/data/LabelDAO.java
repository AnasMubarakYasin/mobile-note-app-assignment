package com.example.mynote.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LabelDAO {

    @Insert
    void insert(LabelData data);

    @Insert
    void insertAll(List<LabelData> list);

    @Query("SELECT * FROM labels_table")
    LiveData<List<LabelData>> selctAll();

    @Query("DELETE FROM notes_table")
    void deleteAll();

    @Update
    void update(LabelData data);

    @Delete
    void delete(LabelData data);
}
