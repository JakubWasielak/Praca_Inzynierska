package com.example.praca_inzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import kotlin.experimental.BitwiseOperationsKt;

public class BookingSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_booking_summary);


        FoundAirlineTicketsModel foundAirlineTicketsModel = getIntent().getParcelableExtra("AirlineTicketsModels");
        ArrayList<String> seatsNumber = (ArrayList<String>) getIntent().getSerializableExtra("seatsNumber");
        ArrayList<PassengerModel> passengerList = (ArrayList<PassengerModel>) getIntent().getSerializableExtra("passengerList");

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
        TextView tvSeatNumber= findViewById(R.id.seatNumber_TextView);
        TextView tvTicketPrice = findViewById(R.id.ticketPrice_TextView);

        tvDepCode.setText(foundAirlineTicketsModel.getDepartureAirportCode());
        tvDepCity.setText(foundAirlineTicketsModel.getDepartureAirportCityName());
        tvFlightDuration.setText(foundAirlineTicketsModel.getFlightDuration());
        tvArrCode.setText(foundAirlineTicketsModel.getArrivalAirportCode());
        tvArrCity.setText(foundAirlineTicketsModel.getArrivalAirportCityName());
        tvDepDate.setText(returnDate(foundAirlineTicketsModel.getDepartureDateAndTime()));
        tvTravelClass.setText(foundAirlineTicketsModel.getTravelClass());
        tvFlightNumber.setText(foundAirlineTicketsModel.getFlightNumber());
        tvDepTime.setText(returnTime(foundAirlineTicketsModel.getDepartureDateAndTime()));
        tvNumberPassengers.setText(String.valueOf(foundAirlineTicketsModel.getNumberPassengers()));
        tvSeatNumber.setText(String.valueOf(seatsNumber));
        DecimalFormat formatter = new DecimalFormat("#0.00");
        String PriceTicket = formatter.format(foundAirlineTicketsModel.getTicketPrice()*foundAirlineTicketsModel.getNumberPassengers())+" zł";
        tvTicketPrice.setText(PriceTicket);

        ListView lvPassengerInformation = findViewById(R.id.passengerInformation_ListView);
        PassengerDetailsListAdapter adapter = new PassengerDetailsListAdapter(BookingSummaryActivity.this, R.layout.new_passenger_item, passengerList);
        lvPassengerInformation.setAdapter(adapter);

        ImageButton btnPreviousActivity= findViewById(R.id.goToPreviousActivity_ImageButton);
        btnPreviousActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageButton btnOpenMainActivity = findViewById(R.id.confirm_ImageButton);
        btnOpenMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingSummaryActivity.this, MainActivity.class);
                intent.putExtra("AirlineTicketsModels",foundAirlineTicketsModel);
                startActivity(intent);

            }
        });
    }

    private String returnDate(String s){
        String[] words = s.split(",");
        return words[0];
    }
    private String returnTime(String s){
        String[] words = s.split(",");
        String time = words[1];
        time = time.substring(1);
        return time;
    }
}