package com.d1m0hkrasav4ik.notemanager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class NameNoteFragment extends Fragment {
    public static final String CURRENT_NOTE = "CurrentNote";
    private Note currentNote;//текущая заметка
    private boolean isLand;

    public static NameNoteFragment newInstance() {

        Bundle args = new Bundle();

        NameNoteFragment fragment = new NameNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_note,
                container,
                false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);

        initRecyclerView(recyclerView, Bridge.data);
        return view;
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        // Определение, можно ли будет расположить рядом описание
        isLand = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            // Если восстановить не удалось, то сделаем объект с первым индексом
            currentNote = new Note(
                    getResources().getStringArray(R.array.names)[0],
                    new Date(),
                    getResources().getStringArray(R.array.descriptions)[0],
                    0);
        }

        if (isLand) {
            showDescriptionNoteLand(currentNote);
        }

    }

    private void initRecyclerView(RecyclerView recyclerView, INoteCardSource data) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        NoteAdapter adapter = new NoteAdapter(data);
        recyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentNote = data.getCardData(position);
                showDescription(currentNote, position);
            }
        });

    }


    private void showDescription(Note currentNote, int position) {
        if (isLand) {
            showDescriptionNoteLand(currentNote);
        } else {
            showDescriptionNotePort(currentNote, position);
        }
    }

    private void showDescriptionNotePort(Note currentNote, int position) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DescriptionActivity.class);

        intent.putExtra(DescriptionFragment.ARG_NOTE, currentNote);
        intent.putExtra(DescriptionFragment.POSITION, position);
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