package com.d1m0hkrasav4ik.notemanager.data;

public interface INoteCardSource {


    Note getCardData(int position);

    int size();

    void clear();

    void add(Note note);

    void delete(int position);

    void update(Note note);
}
