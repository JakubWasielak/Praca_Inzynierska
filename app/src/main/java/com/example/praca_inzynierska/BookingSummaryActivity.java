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

    private AirlineTicketModel airlineTicketModel;

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
                if (airlineTicketModel.isOneWayFlight()) {
                    Intent intent = new Intent(BookingSummaryActivity.this, MainActivity.class);
                    FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(BookingSummaryActivity.this);
                    flyingApplicationDatabaseHelper.addNewTicket(airlineTicketModel.getDepartureAirportCode(), airlineTicketModel.getDepartureAirportName(),
                            airlineTicketModel.getArrivalAirportCode(), airlineTicketModel.getArrivalAirportName(),
                            airlineTicketModel.getFlightDuration(), airlineTicketModel.getDepartureDate(), airlineTicketModel.getDepartureTime(), airlineTicketModel.getFlightNumber()
                            , airlineTicketModel.getTravelClass(),
                            airlineTicketModel.getTicketPrice(), airlineTicketModel.getNumberPassengersAdults(), airlineTicketModel.getNumberPassengersChildren(), airlineTicketModel.isTicketConnecting());
                    int Id = flyingApplicationDatabaseHelper.getNextTickedID();
                    for (int i = 0; i < airlineTicketModel.getpassengerInformation().size(); i++) {
                        flyingApplicationDatabaseHelper.addNewPassager(airlineTicketModel.getpassengerInformation().get(i).getName(), airlineTicketModel.getpassengerInformation().get(i).getLastName(),
                                airlineTicketModel.getpassengerInformation().get(i).getAge(), airlineTicketModel.getpassengerInformation().get(i).getGender(), airlineTicketModel.getReservedSeatsNames().get(i), Id);
                    }

                    startActivity(intent);

                } else {
                    Intent intent = new Intent(BookingSummaryActivity.this, SearchResultsActivity.class);
                    FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(BookingSummaryActivity.this);
                    flyingApplicationDatabaseHelper.addNewTicket(airlineTicketModel.getDepartureAirportCode(), airlineTicketModel.getDepartureAirportName(),
                            airlineTicketModel.getArrivalAirportCode(), airlineTicketModel.getArrivalAirportName(),
                            airlineTicketModel.getFlightDuration(), airlineTicketModel.getDepartureDate(), airlineTicketModel.getDepartureTime(), airlineTicketModel.getFlightNumber()
                            , airlineTicketModel.getTravelClass(),
                            airlineTicketModel.getTicketPrice(), airlineTicketModel.getNumberPassengersAdults(), airlineTicketModel.getNumberPassengersChildren(), airlineTicketModel.isTicketConnecting());

                    int Id = flyingApplicationDatabaseHelper.getNextTickedID();
                    for (int i = 0; i < airlineTicketModel.getpassengerInformation().size(); i++) {
                        flyingApplicationDatabaseHelper.addNewPassager(airlineTicketModel.getpassengerInformation().get(i).getName(), airlineTicketModel.getpassengerInformation().get(i).getLastName(),
                                airlineTicketModel.getpassengerInformation().get(i).getAge(), airlineTicketModel.getpassengerInformation().get(i).getGender(), airlineTicketModel.getReservedSeatsNames().get(i), Id);
                    }

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

    private void getIntentData() {
        if (getIntent().hasExtra("AirlineTicketsModels")) {
            airlineTicketModel = getIntent().getParcelableExtra("AirlineTicketsModels");
            setFlightInformation();
        } else {
            Toast.makeText(BookingSummaryActivity.this, "Brak danych.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setFlightInformation() {
        tvDepCode.setText(airlineTicketModel.getDepartureAirportCode());
        tvDepCity.setText(airlineTicketModel.getDepartureAirportName());
        tvFlightDuration.setText(airlineTicketModel.getFlightDuration());
        tvArrCode.setText(airlineTicketModel.getArrivalAirportCode());
        tvArrCity.setText(airlineTicketModel.getArrivalAirportName());
        tvDepDate.setText(airlineTicketModel.getDepartureDate());
        tvTravelClass.setText(airlineTicketModel.getTravelClass());
        tvFlightNumber.setText(airlineTicketModel.getFlightNumber());
        tvDepTime.setText(airlineTicketModel.getDepartureTime());
        tvNumberPassengers.setText(String.valueOf(airlineTicketModel.getNumberPassengersAdults() + airlineTicketModel.getNumberPassengersChildren()));
        tvSeatNumber.setText(String.valueOf(airlineTicketModel.getReservedSeatsNames()));
        DecimalFormat formatter = new DecimalFormat("#0.00");
        String PriceTicket = formatter.format(airlineTicketModel.getTicketPrice() * (airlineTicketModel.getNumberPassengersAdults() + airlineTicketModel.getNumberPassengersChildren())) + " zł";
        tvTicketPrice.setText(PriceTicket);
        PassengerDetailsListAdapter adapter = new PassengerDetailsListAdapter(BookingSummaryActivity.this, R.layout.new_passenger_item, airlineTicketModel.getpassengerInformation());
        lvPassengerInformation.setAdapter(adapter);
    }

}