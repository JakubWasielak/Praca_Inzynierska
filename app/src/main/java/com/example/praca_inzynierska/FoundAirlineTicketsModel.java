package com.example.praca_inzynierska;

import android.os.Parcel;
import android.os.Parcelable;

public class FoundAirlineTicketsModel implements Parcelable {
    String departureAirportCode;
    String departureAirportCityName;
    String arrivalAirportCode;
    String arrivalAirportCityName;
    String flightDuration;
    String departureDateAndTime;
    String flightNumber;
    double ticketPrice;
    int numberPassengers;
    String travelClass;

    public FoundAirlineTicketsModel(String departureAirportCode, String departureAirportCityName, String arrivalAirportCode, String arrivalAirportCityName, String flightDuration, String departureDateAndTime, String flightNumber, double ticketPrice, int numberPassengers, String travelClass) {
        this.departureAirportCode = departureAirportCode;
        this.departureAirportCityName = departureAirportCityName;
        this.arrivalAirportCode = arrivalAirportCode;
        this.arrivalAirportCityName = arrivalAirportCityName;
        this.flightDuration = flightDuration;
        this.departureDateAndTime = departureDateAndTime;
        this.flightNumber = flightNumber;
        this.ticketPrice = ticketPrice;
        this.numberPassengers = numberPassengers;
        this.travelClass = travelClass;
    }

    protected FoundAirlineTicketsModel(Parcel in) {
        departureAirportCode = in.readString();
        departureAirportCityName = in.readString();
        arrivalAirportCode = in.readString();
        arrivalAirportCityName = in.readString();
        flightDuration = in.readString();
        departureDateAndTime = in.readString();
        flightNumber = in.readString();
        ticketPrice = in.readDouble();
        numberPassengers = in.readInt();
        travelClass = in.readString();
    }

    public static final Creator<FoundAirlineTicketsModel> CREATOR = new Creator<FoundAirlineTicketsModel>() {
        @Override
        public FoundAirlineTicketsModel createFromParcel(Parcel in) {
            return new FoundAirlineTicketsModel(in);
        }

        @Override
        public FoundAirlineTicketsModel[] newArray(int size) {
            return new FoundAirlineTicketsModel[size];
        }
    };

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public String getDepartureAirportCityName() {
        return departureAirportCityName;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public String getArrivalAirportCityName() {
        return arrivalAirportCityName;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public String getDepartureDateAndTime() {
        return departureDateAndTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getNumberPassengers() {
        return numberPassengers;
    }

    public String getTravelClass() {
        return travelClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(departureAirportCode);
        parcel.writeString(departureAirportCityName);
        parcel.writeString(arrivalAirportCode);
        parcel.writeString(arrivalAirportCityName);
        parcel.writeString(flightDuration);
        parcel.writeString(departureDateAndTime);
        parcel.writeString(flightNumber);
        parcel.writeDouble(ticketPrice);
        parcel.writeInt(numberPassengers);
        parcel.writeString(travelClass);
    }
}
