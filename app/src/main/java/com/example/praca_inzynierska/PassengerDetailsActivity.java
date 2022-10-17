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

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class PassengerDetailsActivity extends AppCompatActivity {
    ArrayList<PassengerModel> passengerList = new ArrayList<>();
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_passenger_details);
        
        //Reading the transmitted value
        Intent intent = getIntent();
        FoundAirlineTicketsModel foundAirlineTicketsModel = intent.getParcelableExtra("AirlineTicketsModels");


        ListView passengerInfo_ListView = (ListView) findViewById(R.id.passengerInformation_ListView);
        TextInputEditText passengerName_TextInputEditText = (TextInputEditText) findViewById(R.id.passengerName_TextInputEditText);
        TextInputEditText passengerLastName_TextInputEditText = (TextInputEditText) findViewById(R.id.passengerLastName_TextInputEditText);
        TextInputEditText passengerAge_TextInputEditText = (TextInputEditText) findViewById(R.id.passengerAge_TextInputEditText);
        RadioGroup gender_RadioGroup = (RadioGroup) findViewById(R.id.gender_RadioGroup);
        ImageButton goTo_ImageButton = (ImageButton) findViewById(R.id.goTo_ImageButton);

        goTo_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassengerModel passengerModel = new PassengerModel(String.valueOf(passengerName_TextInputEditText.getText()), String.valueOf(passengerLastName_TextInputEditText.getText()),
                        Integer.parseInt(String.valueOf(passengerAge_TextInputEditText.getText())), gender);
                passengerList.add(passengerModel);
                PassengerDetailsListAdapter adapter = new PassengerDetailsListAdapter(PassengerDetailsActivity.this, R.layout.new_passenger_item, passengerList);
                passengerInfo_ListView.setAdapter(adapter);
            }
        });

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



























































