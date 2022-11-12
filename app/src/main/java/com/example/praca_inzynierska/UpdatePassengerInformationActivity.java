package com.example.praca_inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class UpdatePassengerInformationActivity extends AppCompatActivity {
    private AutoCompleteTextView tvGenderSelection;
    private TextInputEditText tvPassengerName;
    private TextInputEditText tvPassengerLastName;
    private TextInputEditText tvPassengerAge;

    private PassengerModel passengerModel;
    private int passengerModel_PassengerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_update_passenger_information);

        String[] adultGenderNames = getResources().getStringArray(R.array.passengerGender);


        tvGenderSelection = findViewById(R.id.updatePassenger_GenderSelection_AutoCompleteTextView);
        tvPassengerName = findViewById(R.id.updatePassenger_PassengerName_TextInputEditText);
        tvPassengerLastName = findViewById(R.id.updatePassenger_PassengerLastName_TextInputEditText);
        tvPassengerAge = findViewById(R.id.updatePassenger_PassengerAge_TextInputEditText);

        getAndSetIntentData();

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.drop_down_menu_list_item, adultGenderNames);
        tvGenderSelection.setAdapter(arrayAdapter);

        ImageButton btnUpdatePassenger = findViewById(R.id.updatePassenger_UpdatePassenger_ImageButton);
        btnUpdatePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlyingApplicationDatabaseHelper databaseHelper = new FlyingApplicationDatabaseHelper(UpdatePassengerInformationActivity.this);
                databaseHelper.updatePassenger(String.valueOf(passengerModel_PassengerID),String.valueOf(tvPassengerName.getText()),String.valueOf(tvPassengerLastName.getText()),Integer.parseInt(String.valueOf(tvPassengerAge.getText())),String.valueOf(tvGenderSelection.getText()));
                Intent intent = new Intent(UpdatePassengerInformationActivity.this, TicketInformationActivity.class);
                startActivity(intent);
            }
        });
        ImageButton btnPreviousActivityPassenger = findViewById(R.id.updatePassenger_previousActivity_ImageButton);
        btnPreviousActivityPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("passengerModel")) {
            passengerModel = getIntent().getParcelableExtra("passengerModel");
            passengerModel_PassengerID = getIntent().getIntExtra("passenger_id", 0);
            tvGenderSelection.setText(passengerModel.getGender());
            tvPassengerName.setText(passengerModel.getName());
            tvPassengerLastName.setText(passengerModel.getLastName());
            tvPassengerAge.setText(String.valueOf(passengerModel.getAge()));
        } else {
            Toast.makeText(this, "Brak danych.", Toast.LENGTH_SHORT).show();
        }
    }


}