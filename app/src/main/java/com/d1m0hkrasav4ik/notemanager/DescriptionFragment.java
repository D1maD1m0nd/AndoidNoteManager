package com.d1m0hkrasav4ik.notemanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;


public class DescriptionFragment extends Fragment {
    public static final String ARG_NOTE = "note";
    TextView date;
    Calendar dateAndTime = Calendar.getInstance();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_name_description, container, false);
        // найти в контейнере элемент текст вью
        TextView textView = view.findViewById(R.id.description);
        // Получить из ресурсов массив cmрок
        String[] descriptions = getResources().getStringArray(R.array.descriptions);
        // Выбрать по индексу подходящий
        textView.setText(descriptions[note.getDescriptionIndex()]);
        // Установить название заметки
        TextView noteNameView = view.findViewById(R.id.textView);
        noteNameView.setText(note.getName());

        date = view.findViewById(R.id.timeNow);
        date.setText(note.getDate().toString());

        Button button = view.findViewById(R.id.buttonSetTime);
        button.setOnClickListener(v -> {
            setDate();
        });
        return view;
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