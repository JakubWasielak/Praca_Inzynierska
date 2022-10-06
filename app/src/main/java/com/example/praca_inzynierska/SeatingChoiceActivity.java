package com.example.praca_inzynierska;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

public class SeatingChoiceActivity extends AppCompatActivity {
    private static final int[] ImageButtonID={R.id.seatA1_ImageButton,R.id.seatA2_ImageButton,R.id.seatA3_ImageButton,R.id.seatA4_ImageButton,
            R.id.seatB1_ImageButton,R.id.seatB2_ImageButton,R.id.seatB3_ImageButton,R.id.seatB4_ImageButton,
            R.id.seatC1_ImageButton,R.id.seatC2_ImageButton,R.id.seatC3_ImageButton,R.id.seatC4_ImageButton,
            R.id.seatD1_ImageButton,R.id.seatD2_ImageButton,R.id.seatD3_ImageButton,R.id.seatD4_ImageButton,
            R.id.seatE1_ImageButton,R.id.seatE2_ImageButton,R.id.seatE3_ImageButton,R.id.seatE4_ImageButton,
            R.id.seatF1_ImageButton,R.id.seatF2_ImageButton,R.id.seatF3_ImageButton,R.id.seatF4_ImageButton};

    private final ImageButton[] Seat_ImageButton = new ImageButton[ImageButtonID.length];

    private TextView showNumberOfSeats;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_seating_choice);

        //Reading the transmitted value
        String departureAirportCode = getIntent().getStringExtra("departureAirportCode");
        String departureAirportCityName = getIntent().getStringExtra("departureAirportCityName");
        String arrivalAirportCode = getIntent().getStringExtra("arrivalAirportCode");
        String arrivalAirportCityName = getIntent().getStringExtra("arrivalAirportCityName");
        String flightDuration = getIntent().getStringExtra("flightDuration");
        String departureDateAndTime = getIntent().getStringExtra("departureDateAndTime");
        String flightNumber = getIntent().getStringExtra("flightNumber");
//        String numberPassengers = getIntent().getStringExtra("NumberPassengers");
        String numberPassengers = "3";
        String travelClass = getIntent().getStringExtra("travelClass");

        TextView tvDepartureAirportCode = findViewById(R.id.departureAirportCode_TextView);
        TextView tvDepartureAirportName = findViewById(R.id.departureCityName_TextView);
        TextView tvArrivalAirportCode = findViewById(R.id.arrivalAirportCode_TextView);
        TextView tvArrivalAirportName = findViewById(R.id.arrivalAirportCityName_TextView);
        TextView tvFlightDuration = findViewById(R.id.flightDuration_TextView);
        TextView tvDepartureDateAndTime = findViewById(R.id.departureDateAndTime_TextView);
        TextView tvFlightNumber = findViewById(R.id.flightNumber_TextView);
        TextView tvNumberOfPassengers = findViewById(R.id.numberOfPassengers_TextView);
        TextView tvTravelClass = findViewById(R.id.travelClass_TextView);

        tvDepartureAirportCode.setText(departureAirportCode);
        tvDepartureAirportName.setText(departureAirportCityName);
        tvArrivalAirportCode.setText(arrivalAirportCode);
        tvArrivalAirportName.setText(arrivalAirportCityName);
        tvFlightDuration.setText(flightDuration);
        tvDepartureDateAndTime.setText(departureDateAndTime);
        tvFlightNumber.setText(flightNumber);
        tvNumberOfPassengers.setText(numberPassengers);
        tvTravelClass.setText(travelClass);

        showNumberOfSeats = findViewById(R.id.seatNumber_TextView);

        for(int i=0; i<ImageButtonID.length; i++){
            Seat_ImageButton[i] = findViewById(ImageButtonID[i]);
        }

        setOccupiedSeats();
        selectionOfSeats(numberPassengers);



    }

    private void setOccupiedSeats(){
        int[] a = new int[10];
        Random random = new Random();
        for(int i=0; i<a.length;i++){
            a[i]=random.nextInt(24);

            for(int j=0; j<i; j++){
                if(a[i]==a[j]){
                    i--;
                    break;
                }
            }
        }
        for (int j : a) {
            Seat_ImageButton[j].setBackgroundResource(R.drawable.bg_seat_occupied);
        }
    }

    private void selectionOfSeats(String numberPassengers){
        int passengersCount = Integer.parseInt(numberPassengers);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void backToSearchResultAcitivity(View view) {
        finish();
    }
}