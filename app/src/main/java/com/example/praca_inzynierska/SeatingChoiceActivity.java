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

import java.util.Objects;
import java.util.Random;

public class SeatingChoiceActivity extends AppCompatActivity {
    private int selectedSeats = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_seating_choice);

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
                Intent intent = new Intent(SeatingChoiceActivity.this, PassengerDetailsActivity.class);
                intent.putExtra("AirlineTicketsModels",foundAirlineTicketsModel);
                startActivity(intent);
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
                            selectedSeats++;
                        } else if (imageButton.getBackground().getConstantState() == getResources().getDrawable(R.drawable.bg_seat_selected).getConstantState()) {
                            imageButton.setBackgroundResource(R.drawable.bg_seat_free);
                            selectedSeats--;
                        }
                    } else if (selectedSeats == numberOfPassengers) {
                        if (imageButton.getBackground().getConstantState() == getResources().getDrawable(R.drawable.bg_seat_selected).getConstantState()) {
                            imageButton.setBackgroundResource(R.drawable.bg_seat_free);
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