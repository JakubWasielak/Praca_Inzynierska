package com.example.praca_inzynierska;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Objects;

public class BookingSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_booking_summary);

        AirlineTicketModel airlineTicketModel = getIntent().getParcelableExtra("AirlineTicketsModels");

        TextView tvDepCode = findViewById(R.id.depCode_TextView);
        TextView tvDepCity = findViewById(R.id.depCity_TextView);
        TextView tvFlightDuration = findViewById(R.id.flightDuration_TextView);
        TextView tvArrCode = findViewById(R.id.arrCode_TextView);
        TextView tvArrCity = findViewById(R.id.arrCity_TextView);
        TextView tvDepDate = findViewById(R.id.depDate_TextView);
        TextView tvTravelClass = findViewById(R.id.travelClass_TextView);
        TextView tvFlightNumber = findViewById(R.id.flightNumber_TextView);
        TextView tvDepTime = findViewById(R.id.departureTime_TextView);
        TextView tvNumberPassengers = findViewById(R.id.numberPassengers_TextView);
        TextView tvSeatNumber = findViewById(R.id.seatNumber_TextView);
        TextView tvTicketPrice = findViewById(R.id.ticketPrice_TextView);

        tvDepCode.setText(airlineTicketModel.getDepartureAirportCode());
        tvDepCity.setText(airlineTicketModel.getDepartureAirportName());
        tvFlightDuration.setText(airlineTicketModel.getFlightDuration());
        tvArrCode.setText(airlineTicketModel.getArrivalAirportCode());
        tvArrCity.setText(airlineTicketModel.getArrivalAirportName());
        tvDepDate.setText(airlineTicketModel.getDepartureDate());
        tvTravelClass.setText(airlineTicketModel.getTravelClass());
        tvFlightNumber.setText(airlineTicketModel.getFlightNumber());
        tvDepTime.setText(airlineTicketModel.getDepartureTime());
        tvNumberPassengers.setText(String.valueOf(airlineTicketModel.getNumberPassengersAdults()+airlineTicketModel.getNumberPassengersChildren()));
        tvSeatNumber.setText(String.valueOf(airlineTicketModel.getReservedSeatsNames()));


        DecimalFormat formatter = new DecimalFormat("#0.00");
        String PriceTicket = formatter.format(airlineTicketModel.getTicketPrice() * (airlineTicketModel.getNumberPassengersAdults()+airlineTicketModel.getNumberPassengersChildren())) + " zł";
        tvTicketPrice.setText(PriceTicket);

        ListView lvPassengerInformation = findViewById(R.id.passengerInformation_ListView);
        PassengerDetailsListAdapter adapter = new PassengerDetailsListAdapter(BookingSummaryActivity.this, R.layout.new_passenger_item, airlineTicketModel.getpassengerInformation());
        lvPassengerInformation.setAdapter(adapter);

        ImageButton btnPreviousActivity = findViewById(R.id.goToPreviousActivity_ImageButton);
        btnPreviousActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageButton btnOpenMainActivity = findViewById(R.id.confirm_ImageButton);
        btnOpenMainActivity.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(airlineTicketModel.isOneWayFlight()) {
                    Intent intent = new Intent(BookingSummaryActivity.this, MainActivity.class);
                    FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(BookingSummaryActivity.this);
                    flyingApplicationDatabaseHelper.addTicket(airlineTicketModel.getDepartureAirportCode(), airlineTicketModel.getDepartureAirportName(),
                            airlineTicketModel.getArrivalAirportCode(), airlineTicketModel.getArrivalAirportName(),
                            airlineTicketModel.getFlightDuration(), airlineTicketModel.getDepartureDate(), airlineTicketModel.getDepartureTime()
                            , airlineTicketModel.getTravelClass(), airlineTicketModel.getFlightNumber(),
                            airlineTicketModel.getTicketPrice(), (airlineTicketModel.getNumberPassengersAdults()+airlineTicketModel.getNumberPassengersChildren()));
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(BookingSummaryActivity.this, SearchResultsActivity.class);
                    FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(BookingSummaryActivity.this);
                    flyingApplicationDatabaseHelper.addTicket(airlineTicketModel.getDepartureAirportCode(), airlineTicketModel.getDepartureAirportName(),
                            airlineTicketModel.getArrivalAirportCode(), airlineTicketModel.getArrivalAirportName(),
                            airlineTicketModel.getFlightDuration(), airlineTicketModel.getDepartureDate(), airlineTicketModel.getDepartureTime()
                            , airlineTicketModel.getTravelClass(), airlineTicketModel.getFlightNumber(),
                            airlineTicketModel.getTicketPrice(), (airlineTicketModel.getNumberPassengersAdults()+airlineTicketModel.getNumberPassengersChildren()));

                    intent.putExtra("DepartureCode", airlineTicketModel.getArrivalAirportCode());
                    intent.putExtra("ArrivalCode", airlineTicketModel.getDepartureAirportCode());
                    intent.putExtra("SelectedDepartureDate", airlineTicketModel.getDepartureDateReturn());
                    intent.putExtra("TravelClass", airlineTicketModel.getTravelClass());
                    intent.putExtra("NumberPassengersAdults", airlineTicketModel.getNumberPassengersAdults());
                    intent.putExtra("NumberPassengersChildren", airlineTicketModel.getNumberPassengersChildren());
                    intent.putExtra("OneWayFlight", true);

                    startActivity(intent);

                }
            }
        });
    }
}