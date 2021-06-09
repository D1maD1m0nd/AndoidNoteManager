package com.d1m0hkrasav4ik.notemanager.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.d1m0hkrasav4ik.notemanager.CardsSourceResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteCardSourceImpl implements INoteCardSource {
    private static final String TAG = "notesRead";
    private static final String COLLECTION_NAME = "notesdb";
    public FirebaseFirestore db;
    private List<Note> dataSource;
    // Коллекция документов
    private final CollectionReference collection;

    public NoteCardSourceImpl() {
        db = FirebaseFirestore.getInstance();
        dataSource = new ArrayList<>(5);
        collection = db.collection(COLLECTION_NAME);
    }

    public NoteCardSourceImpl initDataFireBase() {
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
        for (Note cardData : dataSource) {
            collection.document(cardData.getId()).delete();
        }

        dataSource = new ArrayList<>();
    }


    @Override
    public void add(final Note note) {
        // Добавить документ
        dataSource.add(note);
        collection.add(Mapper.toDocument(note));
    }

    @Override
    public void delete(int position) {
        Note note = dataSource.get(position);
        // Удалить документ с определённым идентификатором
        collection.document(note.getId()).delete();
        dataSource.remove(note);
    }

    @Override
    public void update(Note note) {
        String id = note.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(Mapper.toDocument(note));

    }
}
