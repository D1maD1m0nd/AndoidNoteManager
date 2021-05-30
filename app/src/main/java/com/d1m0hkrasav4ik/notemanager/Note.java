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
    private String description;
    private int descriptionIndex;
    private Date date;

    public Note(String name, Date date,String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public Note(String name, int descriptionIndex, Date date) {
        this.name = name;
        this.descriptionIndex = descriptionIndex;
        this.date = date;
    }

    protected Note(Parcel in) {
        name = in.readString();
        descriptionIndex = in.readInt();
        description = in.readString();
        date = (java.util.Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getName());
        dest.writeInt(getDescriptionIndex());
        dest.writeSerializable(date);
        dest.writeString(getDescription());
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

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
