package com.d1m0hkrasav4ik.notemanager.data;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class Mapper {
    public static class Fields{
        public final static String NAME = "name";
        public final static String DESCRIPTION = "Description";
        public final static String DATE = "date";
    }

    public static Note toCardData(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);
        String name = (String)doc.get(Fields.NAME);
        String description = (String)doc.get(Fields.DESCRIPTION);


        return new Note(name,timeStamp.toDate(),description);
    }

    public static Map<String, Object> toDocument(Note cardData){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.DATE, cardData.getDate());
        answer.put(Fields.NAME, cardData.getName());
        answer.put(Fields.DESCRIPTION,  cardData.getDescription());

        return answer;
    }
}
