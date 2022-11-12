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

import com.example.praca_inzynierska.AirportDataService;
import com.example.praca_inzynierska.R;
import com.example.praca_inzynierska.SearchResultsActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Objects;

public class OneWayFlightFragment extends Fragment {
    private AutoCompleteTextView tvOneWay_departureDate;
    private AutoCompleteTextView tvOneWay_classTravel;
    private LocalDate OneWay_selectedDepartureDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_way_flight, container, false);

        String[] countryNames = getResources().getStringArray(R.array.Countries);
        String[] travelClassNames = getResources().getStringArray(R.array.TravelClasses);
        final AirportDataService airportDataService = new AirportDataService(getActivity());

        AutoCompleteTextView tvOneWay_departureCountry = view.findViewById(R.id.OneWay_departureCountry_AutoCompleteTextView);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countryNames);
        tvOneWay_departureCountry.setAdapter(arrayAdapter1);

        AutoCompleteTextView tvOneWay_arrivalCountry = view.findViewById(R.id.OneWay_arrivalCountry_AutoCompleteTextView);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countryNames);
        tvOneWay_arrivalCountry.setAdapter(arrayAdapter2);

        tvOneWay_departureDate = view.findViewById(R.id.OneWay_departureDate_AutoCompleteTextView);
        setDepartureDate();

        tvOneWay_classTravel = view.findViewById(R.id.OneWay_classTravel_AutoCompleteTextView);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getActivity(), R.layout.drop_down_menu_list_item, travelClassNames);
        tvOneWay_classTravel.setAdapter(arrayAdapter3);

        TextView tvNumberOfAdults = view.findViewById(R.id.OneWay_numberOfAdults_TextView);
        ImageButton btnOneWay_minusOneAdult = view.findViewById(R.id.OneWay_minusOneAdult_ImageButton);
        btnOneWay_minusOneAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfAdults.getText()));
                if (numberOfAdults - 1 > 0) {
                    tvNumberOfAdults.setText(String.valueOf(numberOfAdults - 1));
                }
            }
        });
        ImageButton btnOneWay_addOneAdult = view.findViewById(R.id.OneWay_addOneAdult_ImageButton);
        btnOneWay_addOneAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfAdults.getText()));
                if (numberOfAdults < 8) {
                    tvNumberOfAdults.setText(String.valueOf(numberOfAdults + 1));
                }
            }
        });

        TextView tvNumberOfChildren = view.findViewById(R.id.OneWay_numberOfChildren_TextView);
        ImageButton btnOneWay_minusOneChild = view.findViewById(R.id.OneWay_minusOneChild_ImageButton);
        btnOneWay_minusOneChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfChildren.getText()));
                if (numberOfAdults - 1 >= 0) {
                    tvNumberOfChildren.setText(String.valueOf(numberOfAdults - 1));
                }
            }
        });
        ImageButton btnOneWay_addOneChild = view.findViewById(R.id.OneWay_addOneChild_ImageButton);
        btnOneWay_addOneChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfChildren.getText()));
                if (numberOfAdults < 8) {
                    tvNumberOfChildren.setText(String.valueOf(numberOfAdults + 1));
                }
            }
        });

        ImageButton btnOneWay_OpenNextActivity = view.findViewById(R.id.OneWay_searchTickets_ImageButton);
        btnOneWay_OpenNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                airportDataService.getDepartureCode(String.valueOf(tvOneWay_departureCountry.getText()), new AirportDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getActivity(), "Błąd.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String Code) {
                        Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                        intent.putExtra("DepartureCode", Code);
                        airportDataService.getArrivalCode(String.valueOf(tvOneWay_arrivalCountry.getText()), new AirportDataService.VolleyResponseListener() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(getActivity(), "Błąd.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String Code) {
                                intent.putExtra("ArrivalCode", Code);
                                intent.putExtra("SelectedDepartureDate", String.valueOf(tvOneWay_departureDate.getText()));
                                intent.putExtra("TravelClass", setTravelClass(String.valueOf(tvOneWay_classTravel.getText())));
                                intent.putExtra("NumberPassengersAdults", Integer.parseInt((String) tvNumberOfAdults.getText()));
                                intent.putExtra("NumberPassengersChildren", Integer.parseInt((String) tvNumberOfChildren.getText()));
                                intent.putExtra("OneWayFlight", true);
                                startActivity(intent);
                            }
                        });
                    }
                });

            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDepartureDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LocalDate todayDate = LocalDate.now();

        tvOneWay_departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        OneWay_selectedDepartureDate = LocalDate.of(year, month + 1, dayOfMonth);

                        if (OneWay_selectedDepartureDate.isAfter(todayDate) || OneWay_selectedDepartureDate.isEqual(todayDate)) {
                            String formattedDate = OneWay_selectedDepartureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            tvOneWay_departureDate.setText(formattedDate);
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
        if (Objects.equals(TravelClass, "ekonomiczna")) {
            SelectedTravelClass = "economy";
        } else if (Objects.equals(TravelClass, "biznesowa")) {
            SelectedTravelClass = "business";
        } else if (Objects.equals(TravelClass, "pierwsza")) {
            SelectedTravelClass = "first";
        }
        return SelectedTravelClass;
    }

}