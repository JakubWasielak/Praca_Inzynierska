package com.example.praca_inzynierska.typeOfTravelFragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.praca_inzynierska.R;
import com.example.praca_inzynierska.SearchResultsActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Objects;

public class OneWayFlightFragment extends Fragment {
    private TextInputEditText tvDepCode;
    private TextInputEditText tvArrCode;
    private AutoCompleteTextView tvDepDate;
    private AutoCompleteTextView tvSelectTravelClass;
    private TextView tvNumberOfAdults, tvNumberOfChildren;
    private final String[] TravelClassName = {"ekonomiczna", "biznesowa", "pierwsza"};
    private LocalDate selectedDepDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_way_flight, container, false);

        tvDepCode = view.findViewById(R.id.departureCode_TextInputEditText);
        tvArrCode = view.findViewById(R.id.arrivalCode_TextInputEditText);
        tvDepDate = view.findViewById(R.id.departureDate_AutoCompleteTextView);
        tvSelectTravelClass = view.findViewById(R.id.selectClassOfTravel_AutoCompleteTextView);

        ImageButton btnOneWay_minusOneAdult = view.findViewById(R.id.OneWay_minusOneAdult_ImageButton);
        ImageButton btnOneWay_addOneAdult = view.findViewById(R.id.OneWay_addOneAdult_ImageButton);
        tvNumberOfAdults = view.findViewById(R.id.OneWay_numberOfAdults_TextView);

        ImageButton btnOneWay_minusOneChild = view.findViewById(R.id.OneWay_minusOneChild_ImageButton);
        ImageButton btnOneWay_addOneChild = view.findViewById(R.id.OneWay_addOneChild_ImageButton);
        tvNumberOfChildren = view.findViewById(R.id.OneWay_numberOfChildren_TextView);

        btnOneWay_minusOneAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfAdults.getText()));
                if (numberOfAdults - 1 > 0) {
                    tvNumberOfAdults.setText(String.valueOf(numberOfAdults - 1));
                }
            }
        });
        btnOneWay_addOneAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfAdults.getText()));
                if (numberOfAdults < 8) {
                    tvNumberOfAdults.setText(String.valueOf(numberOfAdults + 1));
                }
            }
        });

        btnOneWay_minusOneChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfChildren.getText()));
                if (numberOfAdults - 1 >= 0) {
                    tvNumberOfChildren.setText(String.valueOf(numberOfAdults - 1));
                }
            }
        });
        btnOneWay_addOneChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfChildren.getText()));
                if (numberOfAdults < 8) {
                    tvNumberOfChildren.setText(String.valueOf(numberOfAdults + 1));
                }
            }
        });

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
                } else if (tvSelectTravelClass.length() == 0) {
                    Toast.makeText(getActivity(), "Wybierz klase podrózy.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                    intent.putExtra("DepartureCode", String.valueOf(tvDepCode.getText()));
                    intent.putExtra("ArrivalCode", String.valueOf(tvArrCode.getText()));
                    intent.putExtra("SelectedDepartureDate", selectedDepDate.getYear()+"-"+selectedDepDate.getMonthValue()+"-"+selectedDepDate.getDayOfMonth());
                    intent.putExtra("TravelClass", setTravelClass(String.valueOf(tvSelectTravelClass.getText())));
                    intent.putExtra("NumberPassengersAdults", Integer.parseInt((String) tvNumberOfAdults.getText()));
                    intent.putExtra("NumberPassengersChildren", Integer.parseInt((String) tvNumberOfChildren.getText()));
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

    private String setTravelClass(String TravelClass) {
        String SelectedTravelClass = "";
        if(Objects.equals(TravelClass, "ekonomiczna")){
            SelectedTravelClass = "economy";
        }else if(Objects.equals(TravelClass, "biznesowa")){
            SelectedTravelClass = "business";
        }else if(Objects.equals(TravelClass, "pierwsza")){
            SelectedTravelClass = "first";
        }
        return SelectedTravelClass;
    }
}