package com.example.mynote.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import com.example.mynote.data.LabelData;
import com.example.mynote.data.LabelRepository;

import java.util.List;

public class EditLabelViewModel extends AndroidViewModel {
    public final static String KEY_INPUT_NAME = "input_name";

    private LabelRepository repository;
    private final LiveData<List<LabelData>> ALL_LABEL;

    private final SavedStateHandle stateHandle;

    public EditLabelViewModel(Application application, SavedStateHandle stateHandle) {
        super(application);

        this.stateHandle = stateHandle;

        this.repository = new LabelRepository(application);
        this.ALL_LABEL = repository.getAll();
    }

    public void insert(LabelData data) {
        repository.insert(data);
    }

    public LiveData<List<LabelData>> getAllLabel() {
        return ALL_LABEL;
    }
    public LiveData<String> getInputName() {
        return stateHandle.getLiveData(KEY_INPUT_NAME, "");
    }

    public void setInputName(String value) {
        stateHandle.set(KEY_INPUT_NAME, value);
    }
}
