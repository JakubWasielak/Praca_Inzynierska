package com.example.praca_inzynierska;

import android.os.Parcel;
import android.os.Parcelable;

public class PassengerModel  implements Parcelable {
    String name;
    String lastName;
    int age;
    String gender;
    boolean isAdult;

    public PassengerModel(String name, String lastName, int age, String gender, boolean isAdult) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.isAdult = isAdult;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public boolean isAdult() {
        return isAdult;
    }

    protected PassengerModel(Parcel in) {
        name = in.readString();
        lastName = in.readString();
        age = in.readInt();
        gender = in.readString();
        isAdult = in.readByte() != 0;
    }

    public static final Creator<PassengerModel> CREATOR = new Creator<PassengerModel>() {
        @Override
        public PassengerModel createFromParcel(Parcel in) {
            return new PassengerModel(in);
        }

        @Override
        public PassengerModel[] newArray(int size) {
            return new PassengerModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(lastName);
        parcel.writeInt(age);
        parcel.writeString(gender);
        parcel.writeByte((byte) (isAdult ? 1 : 0));
    }
}
