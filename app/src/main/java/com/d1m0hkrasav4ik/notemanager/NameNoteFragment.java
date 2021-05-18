package com.d1m0hkrasav4ik.notemanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;


public class NameNoteFragment extends Fragment {
    Note currentNote;//текущая заметка
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
                showDescriptionNotePort(currentNote);
            });
        }
    }

    private void showDescriptionNotePort(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DescriptionActivity.class);

        intent.putExtra(DescriptionFragment.ARG_NOTE, currentNote);
        startActivity(intent);
    }
}