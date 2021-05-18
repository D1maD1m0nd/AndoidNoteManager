package com.d1m0hkrasav4ik.notemanager;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DescriptionFragment extends Fragment {
    public static final String ARG_NOTE = "note";
    private Note note;

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
        TextView cityNameView = view.findViewById(R.id.textView);
        cityNameView.setText(note.getName());

        TextView date = view.findViewById(R.id.timeNow);
        date.setText(note.getDate().toString());
        return view;
    }

}