package com.example.praca_inzynierska;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AirlineTicketModel implements Parcelable {

    AirportModel departureAirport;
    AirportModel arrivalAirport;
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
    int isConnecting;
    ArrayList<String> reservedSeatsNames;
    ArrayList<PassengerModel> passengerInformation;

    public AirlineTicketModel(AirportModel departureAirport, AirportModel arrivalAirport, String flightDuration, String departureDate, String departureTime, String flightNumber, String travelClass, double ticketPrice, int numberPassengersAdults, int numberPassengersChildren, boolean oneWayFlight, int ticketConnecting) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.flightDuration = flightDuration;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.flightNumber = flightNumber;
        this.travelClass = travelClass;
        this.ticketPrice = ticketPrice;
        this.numberPassengersAdults = numberPassengersAdults;
        this.numberPassengersChildren = numberPassengersChildren;
        this.oneWayFlight = oneWayFlight;
        this.isConnecting = ticketConnecting;
    }

    public AirlineTicketModel(AirportModel departureAirport, AirportModel arrivalAirport, String flightDuration, String departureDate, String departureTime, String departureDateReturn, String flightNumber, String travelClass, double ticketPrice, int numberPassengersAdults, int numberPassengersChildren, boolean oneWayFlight, int isConnecting, ArrayList<String> reservedSeatsNames, ArrayList<PassengerModel> passengerInformation) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.flightDuration = flightDuration;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.departureDateReturn = departureDateReturn;
        this.flightNumber = flightNumber;
        this.travelClass = travelClass;
        this.ticketPrice = ticketPrice;
        this.numberPassengersAdults = numberPassengersAdults;
        this.numberPassengersChildren = numberPassengersChildren;
        this.oneWayFlight = oneWayFlight;
        this.isConnecting = isConnecting;
        this.reservedSeatsNames = reservedSeatsNames;
        this.passengerInformation = passengerInformation;
    }

    protected AirlineTicketModel(Parcel in) {
        departureAirport = in.readParcelable(AirportModel.class.getClassLoader());
        arrivalAirport = in.readParcelable(AirportModel.class.getClassLoader());
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
        isConnecting = in.readInt();
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

    public AirportModel getDepartureAirport() {
        return departureAirport;
    }

    public AirportModel getArrivalAirport() {
        return arrivalAirport;
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

    public int getIsConnecting() {
        return isConnecting;
    }

    public ArrayList<String> getReservedSeatsNames() {
        return reservedSeatsNames;
    }

    public ArrayList<PassengerModel> getPassengerInformation() {
        return passengerInformation;
    }

    public void setDepartureDateReturn(String departureDateReturn) {
        this.departureDateReturn = departureDateReturn;
    }

    public void setReservedSeatsNames(ArrayList<String> reservedSeatsNames) {
        this.reservedSeatsNames = reservedSeatsNames;
    }

    public void setPassengerInformation(ArrayList<PassengerModel> passengerInformation) {
        this.passengerInformation = passengerInformation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(departureAirport, i);
        parcel.writeParcelable(arrivalAirport, i);
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
        parcel.writeInt(isConnecting);
        parcel.writeStringList(reservedSeatsNames);
        parcel.writeTypedList(passengerInformation);
    }
}
