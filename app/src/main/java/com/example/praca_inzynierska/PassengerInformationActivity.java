package com.example.praca_inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class PassengerInformationActivity extends AppCompatActivity {
    private ListView lvPassengerList;
    private ImageButton btnNextActivity;
    private AutoCompleteTextView passengerIsAdult;
    private AutoCompleteTextView passengerGender;
    private TextInputEditText passengerName;
    private TextInputEditText passengerLastName;
    private TextInputEditText passengerAge;

    private AirlineTicketModel selectedTicket;
    private ArrayList<PassengerModel> passengerInfoList;
    private int countAdultPassengers;
    private int countChildPassengers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_passenger_information);

        getIntentData();

        passengerInfoList = new ArrayList<>();
        countAdultPassengers = 0;
        countChildPassengers = 0;
        String[] passengerGenderNames = getResources().getStringArray(R.array.passengerGender);

        lvPassengerList = findViewById(R.id.passengerInfo_passengerList_ListView);
        btnNextActivity = findViewById(R.id.passengerInfo_nextActivity_ImageButton);

        btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countAdultPassengers == selectedTicket.getNumberPassengersAdults() && countChildPassengers==selectedTicket.getNumberPassengersChildren()){
                    Intent intent = new Intent(PassengerInformationActivity.this, BookingSummaryActivity.class);
                    selectedTicket.setPassengerInformation(passengerInfoList);
                    intent.putExtra("SelectedTicket",selectedTicket);
                    startActivity(intent);
                }else {
                    BottomSheetDialog addPassenger_BottomSheetDialog = new BottomSheetDialog(PassengerInformationActivity.this);
                    addPassenger_BottomSheetDialog.setCanceledOnTouchOutside(false);
                    addPassenger_BottomSheetDialog.setContentView(R.layout.passenger_details_sheet_dialog);
                    addPassenger_BottomSheetDialog.show();

                    passengerIsAdult = addPassenger_BottomSheetDialog.findViewById(R.id.addPassenger_isAdult_AutoCompleteTextView);
                    passengerGender = addPassenger_BottomSheetDialog.findViewById(R.id.addPassenger_gender_AutoCompleteTextView);
                    passengerName = addPassenger_BottomSheetDialog.findViewById(R.id.addPassenger_name_TextInputEditText);
                    passengerLastName = addPassenger_BottomSheetDialog.findViewById(R.id.addPassenger_lastName_TextInputEditText);
                    passengerAge = addPassenger_BottomSheetDialog.findViewById(R.id.addPassenger_age_TextInputEditText);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PassengerInformationActivity.this, android.R.layout.simple_list_item_1, passengerGenderNames);
                    passengerGender.setAdapter(arrayAdapter);

                    if(countAdultPassengers<selectedTicket.getNumberPassengersAdults()){
                        passengerIsAdult.setText("Dorosły");
                    }else if(countAdultPassengers==selectedTicket.getNumberPassengersAdults()){
                        passengerIsAdult.setText("Dziecko");
                    }else if(countChildPassengers ==selectedTicket.getNumberPassengersChildren()){
                        btnNextActivity.setBackgroundResource(R.drawable.animation_button_next_activity);
                    }

                    ImageButton btnConfirm = addPassenger_BottomSheetDialog.findViewById(R.id.addPassenger_confirm_ImageButton);
                    btnConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (countAdultPassengers < selectedTicket.getNumberPassengersAdults()) {
                                if (passengerGender.length() == 0) {
                                    Toast.makeText(PassengerInformationActivity.this, "Wybierz płeć", Toast.LENGTH_SHORT).show();
                                } else if (passengerName.length() == 0) {
                                    Toast.makeText(PassengerInformationActivity.this, "Wprowadź imie/imiona.", Toast.LENGTH_SHORT).show();
                                } else if (passengerLastName.length() == 0) {
                                    Toast.makeText(PassengerInformationActivity.this, "Wprowadź nazwisko.", Toast.LENGTH_SHORT).show();
                                } else if (passengerAge.length() == 0) {
                                    Toast.makeText(PassengerInformationActivity.this, "Wprowadź wiek", Toast.LENGTH_SHORT).show();
                                } else {
                                    addNewPassengerAdult();
                                    addPassenger_BottomSheetDialog.dismiss();
                                }
                            } else if (countChildPassengers < selectedTicket.getNumberPassengersChildren()) {
                                if (passengerGender.length() == 0) {
                                    Toast.makeText(PassengerInformationActivity.this, "Wybierz płeć", Toast.LENGTH_SHORT).show();
                                } else if (passengerName.length() == 0) {
                                    Toast.makeText(PassengerInformationActivity.this, "Wprowadź imie/imiona.", Toast.LENGTH_SHORT).show();
                                } else if (passengerLastName.length() == 0) {
                                    Toast.makeText(PassengerInformationActivity.this, "Wprowadź nazwisko.", Toast.LENGTH_SHORT).show();
                                } else if (passengerAge.length() == 0) {
                                    Toast.makeText(PassengerInformationActivity.this, "Wprowadź wiek", Toast.LENGTH_SHORT).show();
                                } else {
                                    addNewPassengerChild();
                                    addPassenger_BottomSheetDialog.dismiss();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void getIntentData() {
        if (getIntent().hasExtra("SelectedTicket")) {
            selectedTicket = getIntent().getParcelableExtra("SelectedTicket");
        } else {
            Toast.makeText(PassengerInformationActivity.this, "Brak danych.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewPassengerChild() {
        String passenger_Gender = String.valueOf(passengerGender.getText());
        String passenger_Name = String.valueOf(passengerName.getText());
        String passenger_LastName = String.valueOf(passengerLastName.getText());
        int passenger_Age = Integer.parseInt(String.valueOf(passengerAge.getText()));

        countChildPassengers++;
        PassengerModel passengerModel = new PassengerModel(passenger_Name, passenger_LastName, passenger_Age, passenger_Gender, false);
        passengerInfoList.add(passengerModel);
        PassengerDetailsListAdapter adapter = new PassengerDetailsListAdapter(PassengerInformationActivity.this, R.layout.new_passenger_item, passengerInfoList);
        lvPassengerList.setAdapter(adapter);
        if(countChildPassengers==selectedTicket.getNumberPassengersChildren()){
            btnNextActivity.setBackgroundResource(R.drawable.animation_button_next_activity);
        }
    }

    private void addNewPassengerAdult() {
        String passenger_Gender = String.valueOf(passengerGender.getText());
        String passenger_Name = String.valueOf(passengerName.getText());
        String passenger_LastName = String.valueOf(passengerLastName.getText());
        int passenger_Age = Integer.parseInt(String.valueOf(passengerAge.getText()));

        countAdultPassengers++;
        PassengerModel passengerModel = new PassengerModel(passenger_Name, passenger_LastName, passenger_Age, passenger_Gender, true);
        passengerInfoList.add(passengerModel);
        PassengerDetailsListAdapter adapter = new PassengerDetailsListAdapter(PassengerInformationActivity.this, R.layout.new_passenger_item, passengerInfoList);
        lvPassengerList.setAdapter(adapter);
    }
}