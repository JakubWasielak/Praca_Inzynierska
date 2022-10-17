package com.example.praca_inzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class PassengerDetailsActivity extends AppCompatActivity {
    ArrayList<PassengerModel> passengerList = new ArrayList<>();
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_passenger_details);

        //Reading the transmitted value
        //Intent intent = getIntent();
        //FoundAirlineTicketsModel foundAirlineTicketsModel = intent.getParcelableExtra("AirlineTicketsModels");

        //
        ListView passengerInfo_ListView = findViewById(R.id.passengerInformation_ListView);
        TextInputEditText passengerName_TextInputEditText = findViewById(R.id.passengerName_TextInputEditText);
        TextInputEditText passengerLastName_TextInputEditText = findViewById(R.id.passengerLastName_TextInputEditText);
        TextInputEditText passengerAge_TextInputEditText = findViewById(R.id.passengerAge_TextInputEditText);
        RadioGroup gender_RadioGroup = findViewById(R.id.gender_RadioGroup);

        //Next Activity
        ImageButton goToNext_ImageButton = findViewById(R.id.goToNext_ImageButton);

        goToNext_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passengerName_TextInputEditText.length()==0){
                    Toast.makeText(PassengerDetailsActivity.this,"Podaj Imie pasażera.", Toast.LENGTH_SHORT).show();
                }else if(passengerLastName_TextInputEditText.length()==0){
                    Toast.makeText(PassengerDetailsActivity.this,"Podaj Nazwisko pasażera.", Toast.LENGTH_SHORT).show();
                }else if(passengerAge_TextInputEditText.length()==0){
                    Toast.makeText(PassengerDetailsActivity.this,"Podaj Wiek pasażera.", Toast.LENGTH_SHORT).show();
                }else{
                    if (passengerList.size() < 3) {
                        PassengerModel passengerModel = new PassengerModel(String.valueOf(passengerName_TextInputEditText.getText()), String.valueOf(passengerLastName_TextInputEditText.getText()), Integer.parseInt(String.valueOf(passengerAge_TextInputEditText.getText())), gender);
                        passengerList.add(passengerModel);
                        PassengerDetailsListAdapter adapter = new PassengerDetailsListAdapter(PassengerDetailsActivity.this, R.layout.new_passenger_item, passengerList);
                        passengerInfo_ListView.setAdapter(adapter);
                    } else if (passengerList.size() == 3) {
                        goToNext_ImageButton.setBackgroundResource(R.drawable.animation_button_next_activity);
                        //Intent intent = new Intent(PassengerDetailsActivity.this, PassengerDetailsActivity.class);
                        //intent.putExtra("passengerList",passengerList);
                        //startActivity(intent);

                    }
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
        //
        gender_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonId) {
                switch (checkedButtonId) {
                    case R.id.genderMen_RadioButton:
                        gender = "Mężczyzna";
                        break;
                    case R.id.genderWoman_RadioButton:
                        gender = "Kobieta";
                        break;
                    case R.id.genderOther_RadioButton:
                        gender = "Inna";
                        break;
                }
            }
        });

    }
}



























































