package com.example.mynote.model;

import com.example.mynote.R;
import com.example.mynote.ui.note.NoteFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final SavedStateHandle stateHandle;

    public final static String KEY_ICON_NOTE_LAYOUT = "icon_note_layout";
    public final static String KEY_NOTE_TYPE_LAYOUT = "note_type_layout";
    public final static int DEF_NOTE_TYPE_LAYOUT = NoteFragment.DEF_TYPE_LAYOUT;
    public final static int DEF_ICON_NOTE_LAYOUT = R.drawable.ic_outline_view_agenda_24;

    public HomeViewModel(SavedStateHandle stateHandle) {
        this.stateHandle = stateHandle;
    }

    public void setNoteTypeLayout(int typeLayout) {
        int iconId = R.drawable.ic_outline_dashboard_24;

        if (typeLayout == NoteFragment.TYPE_lAYOUT_GRID) {
            iconId = R.drawable.ic_outline_view_agenda_24;
        }

        stateHandle.set(KEY_NOTE_TYPE_LAYOUT, typeLayout);
        stateHandle.set(KEY_ICON_NOTE_LAYOUT, iconId);
    }

    public LiveData<Integer> getTypeLayout() {
        return stateHandle.getLiveData(KEY_NOTE_TYPE_LAYOUT, DEF_NOTE_TYPE_LAYOUT);
    }

    public LiveData<Integer> getNoteIconId() {
        return stateHandle.getLiveData(KEY_ICON_NOTE_LAYOUT, DEF_ICON_NOTE_LAYOUT);
    }

    public void toggleNoteIcon() {
        int typeLayout = stateHandle.get(KEY_NOTE_TYPE_LAYOUT);
        int iconId;

        if (typeLayout == NoteFragment.TYPE_lAYOUT_GRID) {
            typeLayout = NoteFragment.TYPE_lAYOUT_LIST;
            iconId = R.drawable.ic_outline_dashboard_24;
        } else {
            typeLayout = NoteFragment.TYPE_lAYOUT_GRID;
            iconId = R.drawable.ic_outline_view_agenda_24;
        }

        stateHandle.set(KEY_NOTE_TYPE_LAYOUT, typeLayout);
        stateHandle.set(KEY_ICON_NOTE_LAYOUT, iconId);
    }
}