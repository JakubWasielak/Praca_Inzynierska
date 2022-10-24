package com.example.praca_inzynierska;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AirlineTicketModel implements Parcelable {
    String departureAirportCode;
    String departureAirportName;
    String arrivalAirportCode;
    String arrivalAirportName;
    String flightDuration;
    String departureDate;
    String departureTime;
    String flightNumber;
    String travelClass;
    double ticketPrice;
    int numberPassengers;
    boolean oneWayFlight;
    ArrayList<String> reservedSeatsNames;
    ArrayList<PassengerModel> passengerInformation;

    public AirlineTicketModel(String departureAirportCode, String departureAirportName, String arrivalAirportCode, String arrivalAirportName, String flightDuration, String departureDate, String departureTime, String flightNumber, String travelClass, double ticketPrice, int numberPassengers, boolean oneWayFlight) {
        this.departureAirportCode = departureAirportCode;
        this.departureAirportName = departureAirportName;
        this.arrivalAirportCode = arrivalAirportCode;
        this.arrivalAirportName = arrivalAirportName;
        this.flightDuration = flightDuration;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.flightNumber = flightNumber;
        this.travelClass = travelClass;
        this.ticketPrice = ticketPrice;
        this.numberPassengers = numberPassengers;
        this.oneWayFlight = oneWayFlight;
    }

    protected AirlineTicketModel(Parcel in) {
        departureAirportCode = in.readString();
        departureAirportName = in.readString();
        arrivalAirportCode = in.readString();
        arrivalAirportName = in.readString();
        flightDuration = in.readString();
        departureDate = in.readString();
        departureTime = in.readString();
        flightNumber = in.readString();
        travelClass = in.readString();
        ticketPrice = in.readDouble();
        numberPassengers = in.readInt();
        oneWayFlight = in.readByte() != 0;
        reservedSeatsNames = in.createStringArrayList();
        passengerInformation = in.createTypedArrayList(PassengerModel.CREATOR);
    }

    public static final Creator<AirlineTicketModel> CREATOR = new Creator<AirlineTicketModel>() {
        @Override
        public AirlineTicketModel createFromParcel(Parcel in) {
            return new AirlineTicketModel(in);
        }

        @Override
        public AirlineTicketModel[] newArray(int size) {
            return new AirlineTicketModel[size];
        }
    };

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getNumberPassengers() {
        return numberPassengers;
    }

    public boolean isOneWayFlight() {
        return oneWayFlight;
    }

    public ArrayList<String> getReservedSeatsNames() {
        return reservedSeatsNames;
    }

    public void setReservedSeatsNames(ArrayList<String> reservedSeatsNames) {
        this.reservedSeatsNames = reservedSeatsNames;
    }

    public ArrayList<PassengerModel> getpassengerInformation() {
        return passengerInformation;
    }

    public void setpassengerInformation(ArrayList<PassengerModel> passengerList) {
        this.passengerInformation = passengerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(departureAirportCode);
        parcel.writeString(departureAirportName);
        parcel.writeString(arrivalAirportCode);
        parcel.writeString(arrivalAirportName);
        parcel.writeString(flightDuration);
        parcel.writeString(departureDate);
        parcel.writeString(departureTime);
        parcel.writeString(flightNumber);
        parcel.writeString(travelClass);
        parcel.writeDouble(ticketPrice);
        parcel.writeInt(numberPassengers);
        parcel.writeByte((byte) (oneWayFlight ? 1 : 0));
        parcel.writeStringList(reservedSeatsNames);
        parcel.writeTypedList(passengerInformation);
    }
}
