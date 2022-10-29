package com.example.praca_inzynierska;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class PassengerDetailsActivity extends AppCompatActivity {
    private ArrayList<PassengerModel> passengerInfoList;
    private String PassagerGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_passenger_details);

        passengerInfoList = new ArrayList<>();

        AirlineTicketModel airlineTicketModel = getIntent().getParcelableExtra("AirlineTicketsModels");

        ListView lvPassengerInfo = findViewById(R.id.passengerInformation_ListView);
        TextInputEditText tvPassengerName = findViewById(R.id.passengerName_TextInputEditText);
        TextInputEditText tvPassengerLastName = findViewById(R.id.passengerLastName_TextInputEditText);
        TextInputEditText tvPassengerAge= findViewById(R.id.passengerAge_TextInputEditText);
        RadioGroup rgPassagerGender= findViewById(R.id.gender_RadioGroup);


        ImageButton btnNextActivity = findViewById(R.id.goToNext_ImageButton);
        btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (passengerInfoList.size() < (airlineTicketModel.getNumberPassengersAdults()+airlineTicketModel.getNumberPassengersChildren())) {
                        if (tvPassengerName.length() == 0) {
                            Toast.makeText(PassengerDetailsActivity.this, "Podaj Imie pasażera.", Toast.LENGTH_SHORT).show();
                        } else if (tvPassengerLastName.length() == 0) {
                            Toast.makeText(PassengerDetailsActivity.this, "Podaj Nazwisko pasażera.", Toast.LENGTH_SHORT).show();
                        } else if (tvPassengerAge.length() == 0) {
                            Toast.makeText(PassengerDetailsActivity.this, "Podaj Wiek pasażera.", Toast.LENGTH_SHORT).show();
                        } else {
                            PassengerModel passengerModel = new PassengerModel(String.valueOf(tvPassengerName.getText()), String.valueOf(tvPassengerLastName.getText()), Integer.parseInt(String.valueOf(tvPassengerAge.getText())), PassagerGender);
                            passengerInfoList.add(passengerModel);
                            PassengerDetailsListAdapter adapter = new PassengerDetailsListAdapter(PassengerDetailsActivity.this, R.layout.new_passenger_item, passengerInfoList);
                            lvPassengerInfo.setAdapter(adapter);
                            tvPassengerName.getText().clear();
                            tvPassengerLastName.getText().clear();
                            tvPassengerAge.getText().clear();
                            rgPassagerGender.clearCheck();
                        }
                    } else if (passengerInfoList.size() == (airlineTicketModel.getNumberPassengersAdults()+airlineTicketModel.getNumberPassengersChildren())) {
                        tvPassengerName.getText().clear();
                        tvPassengerLastName.getText().clear();
                        tvPassengerAge.getText().clear();
                        rgPassagerGender.clearCheck();
                        btnNextActivity.setBackgroundResource(R.drawable.animation_button_next_activity);
                        Intent intent = new Intent(PassengerDetailsActivity.this, BookingSummaryActivity.class);
                        airlineTicketModel.setpassengerInformation(passengerInfoList);
                        intent.putExtra("AirlineTicketsModels",airlineTicketModel);
                        startActivity(intent);
                    }
            }
        });


        ImageButton btnPreviousActivity = findViewById(R.id.goToPreviousActivity_ImageButton);
        btnPreviousActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rgPassagerGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonId) {

                switch (checkedButtonId) {
                    case R.id.genderMen_RadioButton:
                        PassagerGender = "Mężczyzna";
                        break;
                    case R.id.genderWoman_RadioButton:
                        PassagerGender = "Kobieta";
                        break;
                    case R.id.genderOther_RadioButton:
                        PassagerGender = "Inna";
                        break;
                }
            }
        });
    }
}



























































