package com.example.mynote.model;

import android.util.Log;

import androidx.databinding.Bindable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import com.example.mynote.data.NoteData;
import com.example.mynote.ui.editnote.EditNoteFragmentArgs;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EditNoteViewModel extends ObservableViewModel {

    public static final String TAG = "EditNoteFragment";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String PICTURE_PATH = "picture_path";
    public static final String LABEL = "label";
    public static final String REMINDER = "reminder";
    public static final String IS_SAVED = "saved";
    public static final String IS_EDIT_MODE = "edited";
    public static final String CACHED = "cached";

    private final SavedStateHandle stateHandle;

    public EditNoteViewModel(SavedStateHandle stateHandle) {
        this.stateHandle = stateHandle;
    }

    public void initArgs(EditNoteFragmentArgs args) {
        if (getIsEditMode() == null) {
            setIsEditMode(args.getIsEdit());
        }
        if (getId() == null) {
            setId(args.getNoteId());
        }
    }

    @Bindable
    public String getTitle() {
        String value = stateHandle.get(TITLE);

        Log.d(TAG, "getTitle: "+ value);

        return value;
    }

    public void setTitle(String value) {
        if (!Objects.equals(stateHandle.get(TITLE), value)) {
            stateHandle.set(TITLE, value);
            setIsSaved(false);
        }
        Log.d(TAG, "setTitle: "+ value);
    }

    @Bindable
    public String getContent() {
        String value = stateHandle.get(CONTENT);

        Log.d(TAG, "getContent: "+ value);

        return value;
    }

    public void setContent(String value) {
        if (!Objects.equals(stateHandle.get(CONTENT), value)) {
            stateHandle.set(CONTENT, value);
            setIsSaved(false);
        }

        Log.d(TAG, "setContent: "+ value);
    }

    public Boolean getIsSaved() {
        Boolean value = stateHandle.get(IS_SAVED);

        Log.d(TAG, "getIsSaved: "+ value);

        return value;
    }

    public void setIsSaved(boolean value) {
        if (!Objects.equals(stateHandle.get(IS_SAVED), value)) {
            stateHandle.set(IS_SAVED, value);
        }
        Log.d(TAG, "setIsSaved: "+ value);
    }

    public Boolean getIsEditMode() {
        Boolean value = stateHandle.get(IS_EDIT_MODE);

        Log.d(TAG, "getIsEditMode: "+ value);

        return value;
    }

    public void setIsEditMode(boolean value) {
        if (!Objects.equals(stateHandle.get(IS_EDIT_MODE), value)) {
            stateHandle.set(IS_EDIT_MODE, value);
        }
        Log.d(TAG, "setIsEditMode: "+ value);
    }

    public Long getId() {
        Long value = stateHandle.get(ID);
        Log.d(TAG, "getId: "+ value);
        return value;
    }

    public void setId(long value) {
        if (!Objects.equals(stateHandle.get(ID), value)) {
            stateHandle.set(ID, value);
        }
        Log.d(TAG, "setId: "+ value);
    }

    @NotNull
    @Override
    public String toString() {
        return "EditNoteViewModelObservable{" +
                "stateHandle=" + stateHandle +
                "}";
    }

    public NoteData getNoteData() {
        NoteData noteData = new NoteData(
                getTitle(),
                getContent(),
                "",
                "",
                ""
        );
        noteData.setId(getId());
        return noteData;
    }

    public void setNoteData(NoteData noteData) {
        Log.d(TAG, "setNoteData: "+ noteData);

        setId(noteData.getId());
        setTitle(noteData.getTitle());
        setContent(noteData.getContent());

        notifyChange();
    }

    public Boolean getCached() {
        return stateHandle.get(CACHED);
    }

    public void setCached(boolean cached) {
        stateHandle.set(CACHED, cached);
    }
}
