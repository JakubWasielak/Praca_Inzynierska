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
    String departureDateReturn;
    String flightNumber;
    String travelClass;
    double ticketPrice;
    int numberPassengersAdults;
    int numberPassengersChildren;
    boolean oneWayFlight;
    boolean ticketConnecting;
    ArrayList<String> reservedSeatsNames;
    ArrayList<PassengerModel> passengerInformation;

    public AirlineTicketModel(String departureAirportCode, String departureAirportName, String arrivalAirportCode, String arrivalAirportName, String flightDuration, String departureDate, String departureTime, String flightNumber, String travelClass, double ticketPrice, int numberPassengersAdults, int numberPassengersChildren, boolean oneWayFlight, boolean ticketConnecting) {
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
        this.numberPassengersAdults = numberPassengersAdults;
        this.numberPassengersChildren = numberPassengersChildren;
        this.oneWayFlight = oneWayFlight;
        this.ticketConnecting = ticketConnecting;
    }

    protected AirlineTicketModel(Parcel in) {
        departureAirportCode = in.readString();
        departureAirportName = in.readString();
        arrivalAirportCode = in.readString();
        arrivalAirportName = in.readString();
        flightDuration = in.readString();
        departureDate = in.readString();
        departureTime = in.readString();
        departureDateReturn = in.readString();
        flightNumber = in.readString();
        travelClass = in.readString();
        ticketPrice = in.readDouble();
        numberPassengersAdults = in.readInt();
        numberPassengersChildren = in.readInt();
        oneWayFlight = in.readByte() != 0;
        ticketConnecting = in.readByte() != 0;
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

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setNumberPassengersAdults(int numberPassengersAdults) {
        this.numberPassengersAdults = numberPassengersAdults;
    }

    public void setNumberPassengersChildren(int numberPassengersChildren) {
        this.numberPassengersChildren = numberPassengersChildren;
    }

    public void setOneWayFlight(boolean oneWayFlight) {
        this.oneWayFlight = oneWayFlight;
    }

    public void setPassengerInformation(ArrayList<PassengerModel> passengerInformation) {
        this.passengerInformation = passengerInformation;
    }

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

    public String getDepartureDateReturn() {
        return departureDateReturn;
    }

    public void setDepartureDateReturn(String departureDateReturn) {
        this.departureDateReturn = departureDateReturn;
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

    public int getNumberPassengersAdults() {
        return numberPassengersAdults;
    }

    public int getNumberPassengersChildren() {
        return numberPassengersChildren;
    }

    public boolean isOneWayFlight() {
        return oneWayFlight;
    }

    public boolean isTicketConnecting() {
        return ticketConnecting;
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
        parcel.writeString(departureDateReturn);
        parcel.writeString(flightNumber);
        parcel.writeString(travelClass);
        parcel.writeDouble(ticketPrice);
        parcel.writeInt(numberPassengersAdults);
        parcel.writeInt(numberPassengersChildren);
        parcel.writeByte((byte) (oneWayFlight ? 1 : 0));
        parcel.writeByte((byte) (ticketConnecting ? 1 : 0));
        parcel.writeStringList(reservedSeatsNames);
        parcel.writeTypedList(passengerInformation);
    }
}
