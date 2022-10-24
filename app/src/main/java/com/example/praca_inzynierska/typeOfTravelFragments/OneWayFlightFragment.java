package com.example.praca_inzynierska.typeOfTravelFragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;

import android.widget.Toast;

import com.example.praca_inzynierska.R;
import com.example.praca_inzynierska.SearchResultsActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class OneWayFlightFragment extends Fragment {
    private TextInputEditText tvDepCode;
    private TextInputEditText tvArrCode;
    private TextInputEditText tvNumPassengers;
    private AutoCompleteTextView tvDepDate;
    private AutoCompleteTextView tvSelectTravelClass;

    private final String[] TravelClassName = {"Ekonomiczna", "Biznesowa", "Pierwsza"};
    private LocalDate selectedDepDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_way_flight, container, false);

        tvDepCode = view.findViewById(R.id.departureCode_TextInputEditText);
        tvArrCode = view.findViewById(R.id.arrivalCode_TextInputEditText);
        tvNumPassengers = view.findViewById(R.id.numberOfPassengers_TextInputEditText);
        tvDepDate = view.findViewById(R.id.departureDate_AutoCompleteTextView);
        tvSelectTravelClass = view.findViewById(R.id.selectClassOfTravel_AutoCompleteTextView);

        ImageButton btnOpenNextActivity = view.findViewById(R.id.searchForTickets_ImageButton);
        btnOpenNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvDepCode.length() < 3) {
                    Toast.makeText(getActivity(), "Podaj miejsce wylotu.", Toast.LENGTH_SHORT).show();
                } else if (tvArrCode.length() < 3) {
                    Toast.makeText(getActivity(), "Podaj miejsce przylotu.", Toast.LENGTH_SHORT).show();
                } else if (tvDepDate.length() == 0) {
                    Toast.makeText(getActivity(), "Podaj date wylotu.", Toast.LENGTH_SHORT).show();
                } else if (tvNumPassengers.length() < 1) {
                    Toast.makeText(getActivity(), "Podaj ilość pasażerów.", Toast.LENGTH_SHORT).show();
                } else if (tvSelectTravelClass.length() == 0) {
                    Toast.makeText(getActivity(), "Wybierz klase podrózy.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                    intent.putExtra("DepartureCode", String.valueOf(tvDepCode.getText()));
                    intent.putExtra("ArrivalCode", String.valueOf(tvArrCode.getText()));
                    intent.putExtra("SelectedDepartureDate", selectedDepDate);
                    intent.putExtra("NumberPassengers", String.valueOf(tvNumPassengers.getText()));
                    intent.putExtra("TravelClass", String.valueOf(tvSelectTravelClass.getText()));
                    intent.putExtra("OneWayFlight", true);
                    startActivity(intent);
                }
            }
        });

        setDepartureDate();
        setTravelClassPicker();
        return view;
    }

    private void setTravelClassPicker() {
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(getActivity(), R.layout.drop_down_menu_list_item, TravelClassName);
        tvSelectTravelClass.setAdapter(adapterItems);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDepartureDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LocalDate todayDate = LocalDate.now();

        tvDepDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        selectedDepDate = LocalDate.of(year, month + 1, dayOfMonth);

                        if (selectedDepDate.isAfter(todayDate) || selectedDepDate.isEqual(todayDate)) {
                            String formattedDate = selectedDepDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            tvDepDate.setText(formattedDate);
                        }
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
    }

}