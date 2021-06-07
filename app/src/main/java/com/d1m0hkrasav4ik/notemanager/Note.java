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
    private final int descriptionIndex;
    private String name;
    private String description;
    private Date date;


    public Note(String name, Date date, String description, int descriptionIndex) {

        this.name = name;
        this.date = date;
        this.descriptionIndex = descriptionIndex;
        this.description = description;
    }

    public Note() {
        this.name = "Новая запись";
        this.date = new Date();
        this.description = "";
        this.descriptionIndex = 0;
    }

    protected Note(Parcel in) {

        name = in.readString();
        description = in.readString();
        descriptionIndex = in.readInt();
        date = (Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(descriptionIndex);
        dest.writeSerializable(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public Note setName(String name) {
        this.name = name;
        return this;
    }


    public int getDescriptionIndex() {
        return descriptionIndex;
    }

    public Date getDate() {
        return date;
    }

    public Note setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Note setDescription(String description) {
        this.description = description;
        return this;
    }

}
