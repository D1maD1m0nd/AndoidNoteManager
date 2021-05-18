package com.d1m0hkrasav4ik.notemanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class Note implements Parcelable {
    private String name;
    private int descriptionIndex;
    private Date date;

    public Note(String name, int descriptionIndex, Date date) {
        this.name = name;
        this.descriptionIndex = descriptionIndex;
        this.date = date;
    }

    protected Note(Parcel in) {
        name = in.readString();
        descriptionIndex = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getName());
        dest.writeInt(getDescriptionIndex());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getDescriptionIndex() {
        return descriptionIndex;
    }
}
