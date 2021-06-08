package com.d1m0hkrasav4ik.notemanager.data;

import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;


import com.d1m0hkrasav4ik.notemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class NoteCardSourceImpl implements INoteCardSource {
    private final List<Note> dataSource;
    private final Resources resources;
    private static final String TAG = "notesRead";
    private static  final String COLLECTION_NAME = "notesdb";
    // Коллекция документов
    private CollectionReference collection;

    public FirebaseFirestore db;
    public NoteCardSourceImpl(Resources resources) {
        db = FirebaseFirestore.getInstance();
        dataSource = new ArrayList<>(5);
        this.resources = resources;
        collection = db.collection(COLLECTION_NAME);
    }
    public NoteCardSourceImpl initDataFireBase(){
            db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                Note cardData = Mapper.toCardData(id, doc);
                                dataSource.add(cardData);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return this;
    }
    public NoteCardSourceImpl initData() {
        String[] names = resources.getStringArray(R.array.names);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        final int len = descriptions.length;
        for (int i = 0; i < len; i++) {
            dataSource.add(new Note(names[i], Calendar.getInstance().getTime(), descriptions[i]));
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
    public void add(final Note note) {
        // Добавить документ
        dataSource.add(note);
        collection.add(Mapper.toDocument(note));
    }
    @Override
    public void delete(int position) {
        dataSource.remove(position);
    }
}
