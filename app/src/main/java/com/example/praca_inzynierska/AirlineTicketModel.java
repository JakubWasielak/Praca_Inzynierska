package com.example.praca_inzynierska;

import java.util.ArrayList;

public class AirlineTicketModel {
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
}
