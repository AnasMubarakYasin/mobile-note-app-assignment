package com.example.mynote.model;

import android.os.Bundle;
import android.util.Log;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

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
        if (getIsSaved() == null) {
            setIsSaved(false);
        }
    }

    @Bindable
    public String getTitle() {
        String value = stateHandle.get(TITLE);

        Log.d(TAG, "getTitle: "+ value);

        return value;
    }

    public LiveData<String> observerTitle() {
        return stateHandle.getLiveData(TITLE, "");
    }

    public void setTitle(String value) {
        if (!Objects.equals(stateHandle.get(TITLE), value)) {
            stateHandle.set(TITLE, value);
//            notifyPropertyChanged(BR.title);
        }
        Log.d(TAG, "setTitle: "+ value);
    }

    @Bindable
    public String getContent() {
        String value = stateHandle.get(CONTENT);

        Log.d(TAG, "getContent: "+ value);

        return value;
    }

    public LiveData<String> observerContent() {
        return stateHandle.getLiveData(CONTENT, "");
    }

    public void setContent(String value) {
        if (!Objects.equals(stateHandle.get(CONTENT), value)) {
            stateHandle.set(CONTENT, value);
//            notifyPropertyChanged(BR.content);
        }

        Log.d(TAG, "setContent: "+ value);
    }

    public Boolean getIsSaved() {
        Boolean value = stateHandle.get(IS_SAVED);

        Log.d(TAG, "getIsSaved: "+ value);

        return value;
    }

    public LiveData<Boolean> observerIsSaved() {
        return stateHandle.getLiveData(IS_SAVED, false);
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

    public LiveData<Boolean> observerIsEditMode() {
        return stateHandle.getLiveData(IS_EDIT_MODE, false);
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

    public LiveData<Long> observerId() {
        return stateHandle.getLiveData(ID);
    }

    @NotNull
    @Override
    public String toString() {
        return "EditNoteViewModelObservable{" +
                "stateHandle=" + stateHandle +
                '}';
    }
}
