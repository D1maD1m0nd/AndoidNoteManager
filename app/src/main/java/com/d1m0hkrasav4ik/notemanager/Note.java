package com.d1m0hkrasav4ik.notemanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {
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
    private final String name;
    private final int descriptionIndex;
    private Date date;

    public Note(String name, int descriptionIndex, Date date) {
        this.name = name;
        this.descriptionIndex = descriptionIndex;
        this.date = date;
    }

    protected Note(Parcel in) {
        name = in.readString();
        descriptionIndex = in.readInt();
        date = (java.util.Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getName());
        dest.writeInt(getDescriptionIndex());
        dest.writeSerializable(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getDescriptionIndex() {
        return descriptionIndex;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }
}
