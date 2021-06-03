package com.d1m0hkrasav4ik.notemanager;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NoteCardSourceImpl implements INoteCardSource {
    private final List<Note> dataSource;
    private final Resources resources;

    public NoteCardSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(5);
        this.resources = resources;
    }

    public NoteCardSourceImpl initData() {
        String[] names = resources.getStringArray(R.array.names);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        final int len = descriptions.length;
        for (int i = 0; i < len; i++) {
            dataSource.add(new Note(names[i], Calendar.getInstance().getTime(), descriptions[i], i));
        }
        return this;
    }

    @Override
    public Note getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void clear() {
        dataSource.clear();
    }

    @Override
    public int add(Note note) {
        dataSource.add(note);
        return dataSource.indexOf(note);
    }
}
