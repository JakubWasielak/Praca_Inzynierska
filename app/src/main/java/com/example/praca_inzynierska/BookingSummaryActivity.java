package com.example.praca_inzynierska;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Objects;

public class BookingSummaryActivity extends AppCompatActivity {
    private TextView tvDepCode;
    private TextView tvDepCity;
    private TextView tvFlightDuration;
    private TextView tvArrCode;
    private TextView tvArrCity;
    private TextView tvDepDate;
    private TextView tvTravelClass;
    private TextView tvFlightNumber;
    private TextView tvDepTime;
    private TextView tvNumberPassengers;
    private TextView tvSeatNumber;
    private TextView tvTicketPrice;
    private ListView lvPassengerInformation;

    private AirlineTicketModel selectedTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_booking_summary);

        tvDepCode = findViewById(R.id.depCode_TextView);
        tvDepCity = findViewById(R.id.depCity_TextView);
        tvFlightDuration = findViewById(R.id.flightDuration_TextView);
        tvArrCode = findViewById(R.id.arrCode_TextView);
        tvArrCity = findViewById(R.id.arrCity_TextView);
        tvDepDate = findViewById(R.id.depDate_TextView);
        tvTravelClass = findViewById(R.id.travelClass_TextView);
        tvFlightNumber = findViewById(R.id.flightNumber_TextView);
        tvDepTime = findViewById(R.id.departureTime_TextView);
        tvNumberPassengers = findViewById(R.id.numberPassengers_TextView);
        tvSeatNumber = findViewById(R.id.seatNumber_TextView);
        tvTicketPrice = findViewById(R.id.ticketPrice_TextView);

        lvPassengerInformation = findViewById(R.id.passengerInformation_ListView);

        getIntentData();

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
                if (selectedTicket.isOneWayFlight()) {
                    Intent intent = new Intent(BookingSummaryActivity.this, MainActivity.class);
                    FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(BookingSummaryActivity.this);
                    flyingApplicationDatabaseHelper.addTicket(selectedTicket.getDepartureAirport(),selectedTicket.getArrivalAirport(),selectedTicket.getDepartureDate(),
                            selectedTicket.getDepartureTime(),selectedTicket.getFlightDuration(),selectedTicket.getFlightNumber(),selectedTicket.getTravelClass(),
                            selectedTicket.getTicketPrice(),selectedTicket.getIsConnecting());

                    for (int i = 0; i < selectedTicket.getPassengerInformation().size(); i++) {
                        if(selectedTicket.getPassengerInformation().get(i).isAdult()){
                            flyingApplicationDatabaseHelper.addPassenger(selectedTicket.getPassengerInformation().get(i).getName(), selectedTicket.getPassengerInformation().get(i).getLastName(),
                                    selectedTicket.getPassengerInformation().get(i).getAge(), selectedTicket.getPassengerInformation().get(i).getGender(), selectedTicket.getReservedSeatsNames().get(i),1);
                        }else if(!selectedTicket.getPassengerInformation().get(i).isAdult()){
                            flyingApplicationDatabaseHelper.addPassenger(selectedTicket.getPassengerInformation().get(i).getName(), selectedTicket.getPassengerInformation().get(i).getLastName(),
                                    selectedTicket.getPassengerInformation().get(i).getAge(), selectedTicket.getPassengerInformation().get(i).getGender(), selectedTicket.getReservedSeatsNames().get(i),0);
                        }
                    }
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(BookingSummaryActivity.this, SearchResultsActivity.class);
                    FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(BookingSummaryActivity.this);
                    flyingApplicationDatabaseHelper.addTicket(selectedTicket.getDepartureAirport(),selectedTicket.getArrivalAirport(),selectedTicket.getDepartureDate(),
                            selectedTicket.getDepartureTime(),selectedTicket.getFlightDuration(),selectedTicket.getFlightNumber(),selectedTicket.getTravelClass(),
                            selectedTicket.getTicketPrice(),selectedTicket.getIsConnecting());

                    for (int i = 0; i < selectedTicket.getPassengerInformation().size(); i++) {
                        if(selectedTicket.getPassengerInformation().get(i).isAdult()){
                            flyingApplicationDatabaseHelper.addPassenger(selectedTicket.getPassengerInformation().get(i).getName(), selectedTicket.getPassengerInformation().get(i).getLastName(), selectedTicket.getPassengerInformation().get(i).getAge(), selectedTicket.getPassengerInformation().get(i).getGender(), selectedTicket.getReservedSeatsNames().get(i),1);
                        }else if(!selectedTicket.getPassengerInformation().get(i).isAdult()){
                            flyingApplicationDatabaseHelper.addPassenger(selectedTicket.getPassengerInformation().get(i).getName(), selectedTicket.getPassengerInformation().get(i).getLastName(), selectedTicket.getPassengerInformation().get(i).getAge(), selectedTicket.getPassengerInformation().get(i).getGender(), selectedTicket.getReservedSeatsNames().get(i),0);
                        }
                    }

                    intent.putExtra("DepartureAirport", selectedTicket.getArrivalAirport());
                    intent.putExtra("ArrivalAirport", selectedTicket.getDepartureAirport());
                    intent.putExtra("SelectedDepartureDate", selectedTicket.getDepartureDateReturn());
                    intent.putExtra("TravelClass", selectedTicket.getTravelClass());
                    intent.putExtra("NumberPassengersAdults", selectedTicket.getNumberPassengersAdults());
                    intent.putExtra("NumberPassengersChildren", selectedTicket.getNumberPassengersChildren());
                    intent.putExtra("OneWayFlight", true);
                    startActivity(intent);
                }
            }
        });
    }

    private void getIntentData() {
        if (getIntent().hasExtra("SelectedTicket")) {
            selectedTicket = getIntent().getParcelableExtra("SelectedTicket");
            setFlightInformation();
        } else {
            Toast.makeText(BookingSummaryActivity.this, "Brak danych.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setFlightInformation() {
        tvDepCode.setText(selectedTicket.getDepartureAirport().getAirportCode());
        tvDepCity.setText(selectedTicket.getDepartureAirport().getAirportCity());
        tvFlightDuration.setText(selectedTicket.getFlightDuration());
        tvArrCode.setText(selectedTicket.getArrivalAirport().getAirportCode());
        tvArrCity.setText(selectedTicket.getArrivalAirport().getAirportCity());
        tvDepDate.setText(selectedTicket.getDepartureDate());
        tvTravelClass.setText(selectedTicket.getTravelClass());
        tvFlightNumber.setText(selectedTicket.getFlightNumber());
        tvDepTime.setText(selectedTicket.getDepartureTime());
        tvNumberPassengers.setText(String.valueOf(selectedTicket.getNumberPassengersAdults() + selectedTicket.getNumberPassengersChildren()));
        tvSeatNumber.setText(String.valueOf(selectedTicket.getReservedSeatsNames()));
        DecimalFormat formatter = new DecimalFormat("#0.00");
        String PriceTicket = formatter.format(selectedTicket.getTicketPrice() * (selectedTicket.getNumberPassengersAdults() + selectedTicket.getNumberPassengersChildren())) + " zł";
        tvTicketPrice.setText(PriceTicket);
        PassengerDetailsListAdapter adapter = new PassengerDetailsListAdapter(BookingSummaryActivity.this, R.layout.new_passenger_item, selectedTicket.getPassengerInformation());
        lvPassengerInformation.setAdapter(adapter);
    }

}