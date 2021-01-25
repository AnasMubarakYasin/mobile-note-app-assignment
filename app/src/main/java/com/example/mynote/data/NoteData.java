package com.example.mynote.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "notes_table")
public class NoteData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "content")
    private String content;

    @NonNull
    @ColumnInfo(name = "picture_path")
    private String picturePath;

    @NonNull
    @ColumnInfo(name = "label")
    private String label;

    @NonNull
    @ColumnInfo(name = "reminder")
    private String reminder;

    public NoteData(
            @NotNull String title,
            @NotNull String content,
            @NotNull String picturePath,
            @NotNull String label,
            @NotNull String reminder
    ) {
        this.title = title;
        this.content = content;
        this.picturePath = picturePath;
        this.label = label;
        this.reminder = reminder;
    }

    public static NoteData create() {
        return new NoteData("", "", "", "", "");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    @NonNull
    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(@NonNull String picturePath) {
        this.picturePath = picturePath;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    public void setLabel(@NonNull String label) {
        this.label = label;
    }

    @NonNull
    public String getReminder() {
        return reminder;
    }

    public void setReminder(@NonNull String reminder) {
        this.reminder = reminder;
    }

    @NotNull
    @Override
    public String toString() {
        return "NoteData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", label='" + label + '\'' +
                ", reminder='" + reminder + '\'' +
                '}';
    }
}
