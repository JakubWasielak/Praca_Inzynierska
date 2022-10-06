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

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;

public class OneWayFlightFragment extends Fragment {

    private TextInputEditText departureCode_TextInputEditText;
    private TextInputEditText arrivalCode_TextInputEditText;
    private TextInputEditText numberOfPassengers_TextInputEditText;
    private AutoCompleteTextView departureDate_AutoCompleteTextView;
    private AutoCompleteTextView selectClassOfTravel_AutoCompleteTextView;
    private ImageButton btn_searchForTickets;

    private final String[] items =  {"Ekonomiczna","Biznesowa","Pierwsza"};
    private LocalDate selectedDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_way_flight,container,false);

        departureCode_TextInputEditText = view.findViewById(R.id.departureCode_TextInputEditText);
        arrivalCode_TextInputEditText = view.findViewById(R.id.arrivalCode_TextInputEditText);
        numberOfPassengers_TextInputEditText = view.findViewById(R.id.numberOfPassengers_TextInputEditText);
        departureDate_AutoCompleteTextView = view.findViewById(R.id.departureDate_AutoCompleteTextView);
        selectClassOfTravel_AutoCompleteTextView = view.findViewById(R.id.selectClassOfTravel_AutoCompleteTextView);
        btn_searchForTickets = view.findViewById(R.id.searchForTickets_ImageButton);

        setDepartureDate();
        setClassPicker();
        goToNextActivity();

        return view;
    }

    private void setClassPicker() {
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(getActivity(), R.layout.drop_down_menu_list_item, items);
        selectClassOfTravel_AutoCompleteTextView.setAdapter(adapterItems);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDepartureDate(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LocalDate todayDate = LocalDate.now();

        departureDate_AutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        selectedDate = LocalDate.of(year,month+1,dayOfMonth);

                        if(selectedDate.isAfter(todayDate) ||  selectedDate.isEqual(todayDate)) {
                            String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            departureDate_AutoCompleteTextView.setText(formattedDate);
                        }
                    }
                },year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void goToNextActivity() {
        btn_searchForTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(departureCode_TextInputEditText.length()<3){
                    Toast.makeText(getActivity(),"Podaj miejsce wylotu.",Toast.LENGTH_SHORT).show();
                }else if(arrivalCode_TextInputEditText.length()<3){
                    Toast.makeText(getActivity(),"Podaj miejsce przylotu.",Toast.LENGTH_SHORT).show();
                }else if(departureDate_AutoCompleteTextView.length()==0){
                    Toast.makeText(getActivity(),"Podaj date wylotu.",Toast.LENGTH_SHORT).show();
                }else if(numberOfPassengers_TextInputEditText.length()<1){
                    Toast.makeText(getActivity(),"Podaj ilość pasażerów.",Toast.LENGTH_SHORT).show();
                }else if(selectClassOfTravel_AutoCompleteTextView.length()==0){
                    Toast.makeText(getActivity(),"Wybierz klase podrózy.",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                    intent.putExtra("CodeDeparture",String.valueOf(departureCode_TextInputEditText.getText()));
                    intent.putExtra("CodeArrival",String.valueOf(arrivalCode_TextInputEditText.getText()));
                    intent.putExtra("SelectedDate",selectedDate);
                    intent.putExtra("NumberPassengers",String.valueOf(numberOfPassengers_TextInputEditText.getText()));
                    intent.putExtra("travelClass",String.valueOf(selectClassOfTravel_AutoCompleteTextView.getText()));
                    startActivity(intent);
                }
            }
        });
    }

}