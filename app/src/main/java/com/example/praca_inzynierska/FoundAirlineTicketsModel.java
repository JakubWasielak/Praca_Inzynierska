package com.example.praca_inzynierska;

public class FoundAirlineTicketsModel {
    String departureAirportCode;
    String departureAirportCityName;
    String arrivalAirportCode;
    String arrivalAirportCityName;
    String flightDuration;
    String departureDateAndTime;
    String flightNumber;
    String ticketPrice;

    public FoundAirlineTicketsModel(String departureAirportCode, String departureAirportCityName,
                                     String arrivalAirportCode, String arrivalAirportCityName,
                                     String flightDuration, String departureDateAndTime,
                                     String flightNumber, String ticketPrice) {
        this.departureAirportCode = departureAirportCode;
        this.departureAirportCityName = departureAirportCityName;
        this.arrivalAirportCode = arrivalAirportCode;
        this.arrivalAirportCityName = arrivalAirportCityName;
        this.flightDuration = flightDuration;
        this.departureDateAndTime = departureDateAndTime;
        this.flightNumber = flightNumber;
        this.ticketPrice = ticketPrice;
    }

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

    public String getTicketPrice() {
        return ticketPrice;
    }

}
