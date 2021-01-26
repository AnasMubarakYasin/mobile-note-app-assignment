package com.example.mynote.ui.editnote;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.AlarmManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynote.MainActivity;
import com.example.mynote.NoteReminderReceiver;
import com.example.mynote.R;
import com.example.mynote.data.NoteData;
import com.example.mynote.databinding.FragmentEditNoteBinding;
import com.example.mynote.model.EditNoteViewModel;
import com.example.mynote.model.NoteViewModel;
import com.example.mynote.utility.ConverterDate;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class EditNoteFragment extends Fragment {

    public static final String TAG = "CommonEditNoteFragment";
    public static final String DIVIDER = String.join("", Collections.nCopies(100, "-"));

    private EditNoteViewModel viewModel;
    private NoteViewModel noteViewModel;
    private FragmentEditNoteBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        Log.d(TAG, DIVIDER);

        binding = FragmentEditNoteBinding.inflate(inflater, container, false);

        @NonNull EditNoteFragmentArgs args = EditNoteFragmentArgs.fromBundle(getArguments());

        viewModel = new ViewModelProvider(this).get(EditNoteViewModel.class);
        viewModel.initArgs(args);

        binding.contentEditNote.setViewModel(viewModel);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        Log.d(TAG, "onCreateView: args "+ args);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) requireActivity();

        Toolbar toolbar = binding.toolbar;

        mainActivity.setNavigationWithToolbar(toolbar);

        toolbar.post(() -> {
            initOptionMenu(toolbar);
        });

        if (!viewModel.getIsEditMode()) {
            createNote();
        } else {
            editNote();
        }
    }

    public void initOptionMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.edit_note);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.delete: {
                    deleteNote();

                    return true;
                }
                case R.id.add_reminder: {
                    addReminder();

                    return true;
                }
                case R.id.add_picture: {


                    return true;
                }
                default: {
                    return false;
                }
            }
        });
    }

    public void createNote() {
        binding.toolbar.setTitle("Add Note");

        if (viewModel.getCached() == null) {
            viewModel.setNoteData(NoteData.create());
            viewModel.setCached(true);
        }

        viewModel.setIsEditMode(true);

        Log.d(TAG, "createNote: "+ viewModel.getNoteData());
    }

    public void editNote() {
        binding.toolbar.setTitle("Edit Note");

        noteViewModel
                .getById(viewModel.getId())
                .observe(getViewLifecycleOwner(), note -> {
                    viewModel.setNoteData(note);

                    Log.d(TAG, "editNote: "+ viewModel.getNoteData());
                });
    }

    public void deleteNote() {
        NoteData noteData = viewModel.getNoteData();

        Log.d(TAG, "deleteNote: "+ noteData);

        new MaterialAlertDialogBuilder(getContext())
                .setTitle("discard note?")
                .setPositiveButton("yes", (dialog, which) -> {
                    noteViewModel.deleteNote(noteData);

                    requireActivity().onBackPressed();
                })
                .setNegativeButton("cancel", null)
                .show();
    }

    public void savingNote() {
        NoteData noteData = viewModel.getNoteData();

        boolean isNotSaved = !viewModel.getIsSaved();

        Log.d(TAG, "savingNote: "+ !isNotSaved);

        if (isNotSaved) {
            noteViewModel
                    .saveNote(noteData)
                    .thenApplyAsync(fieldId -> {
                        Log.d(TAG, "saveNote: async");

                        viewModel.setId(fieldId);

                        if (fieldId > 0) {
                            viewModel.setIsSaved(true);
                        }
                        if (viewModel.getId() == 0) {
                            viewModel.setIsEditMode(false);
                        }

                        return fieldId;
                    });
        }
    }

    public void addReminder() {
        Log.d(TAG, "addReminder: "+ viewModel.getReminder());

        final int DATE = 0;
        final int TIME = 1;

        Date date;

        if (viewModel.getReminder().isEmpty()) {
            date = new Date();
        } else  {
            date = ConverterDate.stringToDate(viewModel.getReminder());
        }

        String dateString = ConverterDate.toDateString(date);
        String timeString = ConverterDate.toTimeString(date);

        String[] items = new String[]{dateString, timeString};

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext());

        materialAlertDialogBuilder
                .setTitle("Set Reminder")
                .setPositiveButton("save", (dialog, which) -> {
                    viewModel.setReminder(items[DATE] + " " + items[TIME]);
                    setAlarm();
                })
                .setNegativeButton("cancel", null)
                .setItems(items, (dialog, which) -> {
                    if (which == DATE) {
                        MaterialDatePicker datePicker = MaterialDatePicker.Builder
                                .datePicker()
                                .setTitleText("pick date")
                                .build();
                        datePicker.addOnPositiveButtonClickListener(selection -> {
                            Date newDate = new Date((Long) selection);

                            items[which] = ConverterDate.dateToString(date);
                        });
                        datePicker.addOnDismissListener(dateDialog -> {
                            materialAlertDialogBuilder.show();
                        });
                        datePicker.show(getParentFragmentManager(), "");
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);

                        MaterialTimePicker timePicker = new MaterialTimePicker
                                .Builder()
                                .setTitleText("pick time")
                                .setTimeFormat(TimeFormat.CLOCK_12H)
                                .setHour(calendar.get(Calendar.HOUR))
                                .setMinute(calendar.get(Calendar.MINUTE))
                                .build();
                        timePicker.show(getParentFragmentManager(), "");
                        timePicker.addOnDismissListener(timeDialog -> {
                            materialAlertDialogBuilder.show();
                        });
                        timePicker.addOnPositiveButtonClickListener(v -> {
                            items[which] = ConverterDate.toTimeString(
                                    timePicker.getHour(),
                                    timePicker.getMinute(),
                                    calendar.get(Calendar.SECOND)
                                    );
                        });
                    }
                })
                .show();
    }

    public void setAlarm() {
        Activity activity = getActivity();
        Intent intent = new Intent(activity, NoteReminderReceiver.class);

        intent.putExtra(EditNoteViewModel.ID, viewModel.getId());
        intent.putExtra(EditNoteViewModel.TITLE, viewModel.getTitle());
        intent.putExtra(EditNoteViewModel.CONTENT, viewModel.getContent());
        intent.putExtra(EditNoteViewModel.REMINDER, viewModel.getReminder());
        intent.putExtra(EditNoteViewModel.LABEL, "");
        intent.setAction(viewModel.getId().toString());

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Activity.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                activity,
                NoteReminderReceiver.REQUEST_NOTIFY,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        Date date = ConverterDate.stringToDate(viewModel.getReminder());

        Log.d(TAG, "setAlarm: "+ date);
        Log.d(TAG, "setAlarm: broadcast note reminder "+ intent);

        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            date.getTime(),
            pendingIntent
        );
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: ");

        savingNote();
    }
}