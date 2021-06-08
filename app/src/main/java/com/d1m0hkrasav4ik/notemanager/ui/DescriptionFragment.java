package com.d1m0hkrasav4ik.notemanager.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.d1m0hkrasav4ik.notemanager.data.Bridge;
import com.d1m0hkrasav4ik.notemanager.data.Mapper;
import com.d1m0hkrasav4ik.notemanager.data.Note;
import com.d1m0hkrasav4ik.notemanager.R;

import java.util.Calendar;


public class DescriptionFragment extends Fragment {
    public static final String ARG_NOTE = "note";
    public static final String POSITION = "pos";
    public static final String IS_NEW_MODE = "New mode";
    TextView date;
    Calendar dateAndTime = Calendar.getInstance();
    private AppCompatEditText textView;
    private AppCompatEditText noteNameView;
    private int position;
    private boolean isNewMode;
    private Note note;
    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            note.setDate(dateAndTime.getTime());
            date.setText(note.getDate().toString());
        }
    };

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static DescriptionFragment newInstance(Note note) {
        DescriptionFragment f = new DescriptionFragment();    // создание
        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
            isNewMode = getArguments().getBoolean(IS_NEW_MODE);
            position = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_name_description, container, false);
        // найти в контейнере элемент текст вью
        textView = view.findViewById(R.id.description);
        // Выбрать по индексу подходящий
        textView.setText(note.getDescription());
        // Установить название заметки
        noteNameView = view.findViewById(R.id.textView);
        noteNameView.setText(note.getName());

        date = view.findViewById(R.id.timeNow);
        date.setText(note.getDate().toString());

        Button button = view.findViewById(R.id.buttonSetTime);
        button.setOnClickListener(v -> {
            setDate();
        });

        Button buttonSave = view.findViewById(R.id.saveButton);
        buttonSave.setOnClickListener(v -> {
            save();
        });
        return view;
    }

    //сохранение записи
    public void save() {
        Note mainNote = note;
        if (isNewMode) {
            writeChanged(mainNote);
            Bridge.data.add(mainNote);
            Bridge.updateBeforeUpdate = false;
        } else {
            mainNote = Bridge.data.getCardData(position);
            writeChanged(mainNote);
            Bridge.updateBeforeUpdate = true;
        }
        Toast.makeText(getContext(), "Запись сохранена", Toast.LENGTH_SHORT).show();

    }

    private void writeChanged(Note note){
        note.setDate(dateAndTime.getTime())
                .setName(noteNameView.getText().toString())
                .setDescription(textView.getText().toString());
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate() {
        new DatePickerDialog(getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }


}