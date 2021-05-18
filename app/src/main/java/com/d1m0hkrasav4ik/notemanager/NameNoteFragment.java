package com.d1m0hkrasav4ik.notemanager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Date;


public class NameNoteFragment extends Fragment {
    public static final String CURRENT_NOTE = "CurrentNote";
    private Note currentNote;//текущая заметка
    private boolean isLand;


    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_name_note, container, false);
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Определение, можно ли будет расположить рядом герб в другом фрагменте
        isLand = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            // Если восстановить не удалось, то сделаем объект с первым индексом
            currentNote = new Note(getResources().getStringArray(R.array.names)[0], 0, new Date());
        }

        if (isLand) {
            showDescriptionNoteLand(currentNote);
        }

    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        final int TEXT_SIZE = 35;
        String[] notes = getResources().getStringArray(R.array.names);
        int len = notes.length;
        for (int i = 0; i < len; i++) {
            String note = notes[i];
            TextView tv = new TextView(getContext());
            tv.setText(note);
            tv.setTextSize(TEXT_SIZE);
            layoutView.addView(tv);

            final int fi = i;
            //назначаем слушателя события
            tv.setOnClickListener(v -> {
                currentNote = new Note(note, fi, new Date());
                showDescription(currentNote);
            });
        }
    }

    private void showDescription(Note currentNote) {
        if (isLand) {
            showDescriptionNoteLand(currentNote);
        } else {
            showDescriptionNotePort(currentNote);
        }
    }

    private void showDescriptionNotePort(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DescriptionActivity.class);

        intent.putExtra(DescriptionFragment.ARG_NOTE, currentNote);
        startActivity(intent);
    }

    private void showDescriptionNoteLand(Note currentNote) {
        // Создаём новый фрагмент с текущей позицией для вывода герба
        DescriptionFragment detail = DescriptionFragment.newInstance(currentNote);

        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.descriptionContainer, detail);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}