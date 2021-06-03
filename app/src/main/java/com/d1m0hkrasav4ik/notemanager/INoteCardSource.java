package com.d1m0hkrasav4ik.notemanager;

public interface INoteCardSource {
    Note getCardData(int position);
    int size();
    void clear();
    int add(Note note);
    void delete(int position);
}
