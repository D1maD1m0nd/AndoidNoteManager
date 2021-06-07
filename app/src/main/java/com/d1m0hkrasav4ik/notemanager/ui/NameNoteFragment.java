package com.d1m0hkrasav4ik.notemanager.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.d1m0hkrasav4ik.notemanager.data.Bridge;
import com.d1m0hkrasav4ik.notemanager.DescriptionActivity;
import com.d1m0hkrasav4ik.notemanager.data.INoteCardSource;
import com.d1m0hkrasav4ik.notemanager.data.Note;
import com.d1m0hkrasav4ik.notemanager.R;

import java.util.Date;


public class NameNoteFragment extends Fragment {
    public static final String CURRENT_NOTE = "CurrentNote";
    public int position;
    public boolean isNewMode = false;
    private Note currentNote;//текущая заметка
    private boolean isLand;
    private NoteAdapter adapter;
    private RecyclerView recyclerView;

    public static NameNoteFragment newInstance() {

        Bundle args = new Bundle();

        NameNoteFragment fragment = new NameNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        if (Bridge.updateBeforeUpdate) {
            adapter.notifyItemChanged(position);
            Bridge.updateBeforeUpdate = false;
        }
        if (isNewMode) {
            isNewMode = false;
            position = Bridge.data.size() - 1;
            adapter.notifyItemInserted(position);
            recyclerView.scrollToPosition(position);
        }
        super.onResume();
    }

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_note,
                container,
                false);
        recyclerView = view.findViewById(R.id.recycler_view_lines);

        initRecyclerView(recyclerView, Bridge.data);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                currentNote = new Note();
                showDescriptionNotePort(currentNote);
                return true;
            case R.id.action_clear:
                Bridge.data.clear();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu,
                                    @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getPositon();
        switch (item.getItemId()) {
            case R.id.action_delete:
                Bridge.data.delete(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);

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
                    getResources().getStringArray(R.array.descriptions)[0]
                    );
        }

        if (isLand) {
            showDescriptionNoteLand(currentNote);
        }

    }

    private void initRecyclerView(RecyclerView recyclerView, INoteCardSource data) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NoteAdapter(data, this);
        recyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentNote = data.getCardData(position);
                NameNoteFragment.this.position = position;
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

    private void showDescriptionNotePort(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DescriptionActivity.class);
        isNewMode = true;
        intent.putExtra(DescriptionFragment.ARG_NOTE, currentNote);
        intent.putExtra(DescriptionFragment.IS_NEW_MODE, isNewMode);
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