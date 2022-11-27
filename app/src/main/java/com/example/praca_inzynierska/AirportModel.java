package com.example.praca_inzynierska;

import android.os.Parcel;
import android.os.Parcelable;

public class AirportModel implements Parcelable {
    String airportCode;
    String airportName;
    String airportCity;
    String airportCountry;

    public AirportModel(String airportCode, String airportName, String airportCity, String airportCountry) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
    }

    protected AirportModel(Parcel in) {
        airportCode = in.readString();
        airportName = in.readString();
        airportCity = in.readString();
        airportCountry = in.readString();
    }

    public static final Creator<AirportModel> CREATOR = new Creator<AirportModel>() {
        @Override
        public AirportModel createFromParcel(Parcel in) {
            return new AirportModel(in);
        }

        @Override
        public AirportModel[] newArray(int size) {
            return new AirportModel[size];
        }
    };

    public String getAirportCode() {
        return airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public String getAirportCountry() {
        return airportCountry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(airportCode);
        parcel.writeString(airportName);
        parcel.writeString(airportCity);
        parcel.writeString(airportCountry);
    }
}
