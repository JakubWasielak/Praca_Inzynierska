package com.example.praca_inzynierska;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praca_inzynierska.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class SeatingChoiceActivity extends AppCompatActivity {
    private int selectedSeats = 0;
    private final String[] seatNames = {
            "A1","A2","A3","A4",
            "B1","B2","B3","B4",
            "C1","C2","C3","C4",
            "D1","D2","D3","D4",
            "E1","E2","E3","E4",
            "F1","F2","F3","F4",
            "G1","G2","G3","G4"};

    private final int[]seatID={
            R.id.A1,R.id.A2,R.id.A3,R.id.A4,
            R.id.B1,R.id.B2,R.id.B3,R.id.B4,
            R.id.C1,R.id.C2,R.id.C3,R.id.C4,
            R.id.D1,R.id.D2,R.id.D3,R.id.D4,
            R.id.E1,R.id.E2,R.id.E3,R.id.E4,
            R.id.F1,R.id.F2,R.id.F3,R.id.F4,
            R.id.G1,R.id.G2,R.id.G3,R.id.G4};

    ArrayList<String> seatsNumber;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_seating_choice);

        seatsNumber = new ArrayList<>();;

        //Reading the transmitted value
        Intent intent = getIntent();
        FoundAirlineTicketsModel foundAirlineTicketsModel = intent.getParcelableExtra("AirlineTicketsModels");

        //Set flight information
        TextView tvDepartureAirportCode = findViewById(R.id.departureAirportCode_TextView);
        TextView tvDepartureAirportName = findViewById(R.id.departureCityName_TextView);
        TextView tvArrivalAirportCode = findViewById(R.id.arrivalAirportCode_TextView);
        TextView tvArrivalAirportName = findViewById(R.id.arrivalAirportCityName_TextView);
        TextView tvFlightDuration = findViewById(R.id.flightDuration_TextView);
        TextView tvDepartureDateAndTime = findViewById(R.id.departureDateAndTime_TextView);
        TextView tvFlightNumber = findViewById(R.id.flightNumber_TextView);
        TextView tvNumberOfPassengers = findViewById(R.id.numberOfPassengers_TextView);
        TextView tvTravelClass = findViewById(R.id.travelClass_TextView);

        ImageButton imageButton = findViewById(R.id.G1);
        imageButton.getContentDescription();

        tvDepartureAirportCode.setText(foundAirlineTicketsModel.getDepartureAirportCode());
        tvDepartureAirportName.setText(foundAirlineTicketsModel.getDepartureAirportCityName());
        tvArrivalAirportCode.setText(foundAirlineTicketsModel.getArrivalAirportCode());
        tvArrivalAirportName.setText(foundAirlineTicketsModel.getArrivalAirportCityName());
        tvFlightDuration.setText(foundAirlineTicketsModel.getFlightDuration());
        tvDepartureDateAndTime.setText(foundAirlineTicketsModel.getDepartureDateAndTime());
        tvFlightNumber.setText(foundAirlineTicketsModel.getFlightNumber());
        tvNumberOfPassengers.setText(String.valueOf(foundAirlineTicketsModel.getNumberPassengers()));
        tvTravelClass.setText("klasa "+foundAirlineTicketsModel.getTravelClass());

        //Seat management
        GridLayout seatMap_GridLayout = findViewById(R.id.seatMap_GridLayout);
        setSeatReservation(seatMap_GridLayout, foundAirlineTicketsModel.getNumberPassengers());
        setReservedSeats(seatMap_GridLayout);

        //Next Activity
        ImageButton goToPassengerDetailsActivity_ImageButton = findViewById(R.id.goToPassengerDetailsActivity_ImageButton);
        goToPassengerDetailsActivity_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSeats == foundAirlineTicketsModel.getNumberPassengers()){
                    Intent intent = new Intent(SeatingChoiceActivity.this, PassengerDetailsActivity.class);
                    intent.putExtra("AirlineTicketsModels",foundAirlineTicketsModel);
                    intent.putExtra("seatsNumber",seatsNumber);
                    startActivity(intent);
                }else{
                    Toast.makeText(SeatingChoiceActivity.this,"Wybierz miejsce w samolocie dla pasażera", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Previous Activity
        ImageButton goToPreviousActivity_ImageButton = findViewById(R.id.goToPreviousActivity_ImageButton);
        goToPreviousActivity_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setReservedSeats(GridLayout gridLayout) {
        ImageButton imageButton;
        int[] a = new int[8];
        Random random = new Random();
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(gridLayout.getChildCount());

            for (int j = 0; j < i; j++) {
                if (a[i] == a[j]) {
                    i--;
                    break;
                }
            }
        }
        for (int j : a) {
            imageButton = (ImageButton) gridLayout.getChildAt(j);
            imageButton.setBackgroundResource(R.drawable.bg_seat_occupied);
        }
    }

    private void setSeatReservation(GridLayout gridLayout, int numberOfPassengers) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageButton imageButton = (ImageButton) gridLayout.getChildAt(i);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onClick(View view) {
                    if (selectedSeats < numberOfPassengers) {
                        if (imageButton.getBackground().getConstantState() == getResources().getDrawable(R.drawable.bg_seat_free).getConstantState()) {
                            imageButton.setBackgroundResource(R.drawable.bg_seat_selected);
                            for(int i=0; i<seatID.length;i++){
                                if(imageButton.getId()==seatID[i]){
                                    seatsNumber.add(seatNames[i]);
                                }
                            }
                            selectedSeats++;
                        } else if (imageButton.getBackground().getConstantState() == getResources().getDrawable(R.drawable.bg_seat_selected).getConstantState()) {
                            imageButton.setBackgroundResource(R.drawable.bg_seat_free);
                            for(int i=0; i<seatID.length;i++){
                                if(imageButton.getId()==seatID[i]){
                                    seatsNumber.remove(seatNames[i]);
                                }
                            }
                            selectedSeats--;
                        }
                    } else if (selectedSeats == numberOfPassengers) {
                        if (imageButton.getBackground().getConstantState() == getResources().getDrawable(R.drawable.bg_seat_selected).getConstantState()) {
                            imageButton.setBackgroundResource(R.drawable.bg_seat_free);
                            for(int i=0; i<seatID.length;i++){
                                if(imageButton.getId()==seatID[i]){
                                    seatsNumber.remove(seatNames[i]);
                                }
                            }
                            selectedSeats--;
                        }
                    }
                }
            });

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void backToSearchResultAcitivity(View view) {
        finish();
    }
}