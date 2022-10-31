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

public class RoundTripFlightFragment extends Fragment {
    private AutoCompleteTextView tvRoundTrip_departureDate;
    private AutoCompleteTextView tvRoundTrip_departureDateReturn;
    private AutoCompleteTextView tvRoundTrip_classTravel;
    private LocalDate RoundTrip_selectedDepartureDate;
    private LocalDate RoundTrip_selectedDepartureDateReturn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_round_trip_flight, container, false);

        String[] countryNames = getResources().getStringArray(R.array.Countries);
        String[] travelClassNames = getResources().getStringArray(R.array.TravelClasses);
        final AirportDataService airportDataService = new AirportDataService(getActivity());

        //Suggesting a country name -  Departure
        AutoCompleteTextView tvRoundTrip_departureCountry = view.findViewById(R.id.RoundTrip_departureCountry_AutoCompleteTextView);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countryNames);
        tvRoundTrip_departureCountry.setAdapter(arrayAdapter1);

        //Suggesting a country name -  Arrival
        AutoCompleteTextView tvRoundTrip_arrivalCountry = view.findViewById(R.id.RoundTrip_arrivalCountry_AutoCompleteTextView);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countryNames);
        tvRoundTrip_arrivalCountry.setAdapter(arrayAdapter2);

        //Selecting a date - departure
        tvRoundTrip_departureDate = view.findViewById(R.id.RoundTrip_departureDate_AutoCompleteTextView);
        setDatePickerDepartureDate();

        //Selecting a Return date - departure
        tvRoundTrip_departureDateReturn = view.findViewById(R.id.RoundTrip_departureDateReturn_AutoCompleteTextView);
        setDatePickerDepartureDateReturn();

        //Selecting a travel class
        tvRoundTrip_classTravel = view.findViewById(R.id.RoundTrip_classTravel_AutoCompleteTextView);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getActivity(), R.layout.drop_down_menu_list_item, travelClassNames);
        tvRoundTrip_classTravel.setAdapter(arrayAdapter3);

        //Setting the number of passengers - Adult
        TextView tvNumberOfAdults = view.findViewById(R.id.RoundTrip_numberOfAdults_TextView);
        ImageButton btnRoundTrip_minusOneAdult = view.findViewById(R.id.RoundTrip_minusOneAdult_ImageButton);
        btnRoundTrip_minusOneAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfAdults.getText()));
                if (numberOfAdults - 1 > 0) {
                    tvNumberOfAdults.setText(String.valueOf(numberOfAdults - 1));
                }
            }
        });
        ImageButton btnRoundTrip_addOneAdult = view.findViewById(R.id.RoundTrip_addOneAdult_ImageButton);
        btnRoundTrip_addOneAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfAdults.getText()));
                if (numberOfAdults < 8) {
                    tvNumberOfAdults.setText(String.valueOf(numberOfAdults + 1));
                }
            }
        });

        TextView tvNumberOfChildren = view.findViewById(R.id.RoundTrip_numberOfChildren_TextView);
        ImageButton btnRoundTrip_minusOneChild = view.findViewById(R.id.RoundTrip_minusOneChild_ImageButton);
        btnRoundTrip_minusOneChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfChildren.getText()));
                if (numberOfAdults - 1 >= 0) {
                    tvNumberOfChildren.setText(String.valueOf(numberOfAdults - 1));
                }
            }
        });
        ImageButton btnRoundTrip_addOneChild = view.findViewById(R.id.RoundTrip_addOneChild_ImageButton);
        btnRoundTrip_addOneChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfAdults = Integer.parseInt(String.valueOf(tvNumberOfChildren.getText()));
                if (numberOfAdults < 8) {
                    tvNumberOfChildren.setText(String.valueOf(numberOfAdults + 1));
                }
            }
        });

        //Searching for tickets
        ImageButton btnRoundTrip_OpenNextActivity = view.findViewById(R.id.RoundTrip_searchTickets_ImageButton);
        btnRoundTrip_OpenNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                airportDataService.getDepartureCode(String.valueOf(tvRoundTrip_departureCountry.getText()), new AirportDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getActivity(), "Błąd.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String Code) {
                        Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                        intent.putExtra("DepartureCode", Code);
                        airportDataService.getArrivalCode(String.valueOf(tvRoundTrip_arrivalCountry.getText()), new AirportDataService.VolleyResponseListener() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(getActivity(), "Błąd.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String Code) {
                                intent.putExtra("ArrivalCode", Code);
                                intent.putExtra("SelectedDepartureDate", String.valueOf(tvRoundTrip_departureDate.getText()));
                                intent.putExtra("TravelClass", setTravelClass(String.valueOf(tvRoundTrip_classTravel.getText())));
                                intent.putExtra("NumberPassengersAdults", Integer.parseInt((String) tvNumberOfAdults.getText()));
                                intent.putExtra("NumberPassengersChildren", Integer.parseInt((String) tvNumberOfChildren.getText()));
                                intent.putExtra("OneWayFlight", false);
                                intent.putExtra("SelectedDepartureDateReturn", String.valueOf(tvRoundTrip_departureDateReturn.getText()));
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
    private void setDatePickerDepartureDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LocalDate todayDate = LocalDate.now();

        tvRoundTrip_departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        RoundTrip_selectedDepartureDate = LocalDate.of(year, month + 1, dayOfMonth);

                        if (RoundTrip_selectedDepartureDate.isAfter(todayDate) || RoundTrip_selectedDepartureDate.isEqual(todayDate)) {
                            String formattedDate = RoundTrip_selectedDepartureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            tvRoundTrip_departureDate.setText(formattedDate);
                        }
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDatePickerDepartureDateReturn() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvRoundTrip_departureDateReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        RoundTrip_selectedDepartureDateReturn = LocalDate.of(year, month + 1, dayOfMonth);

                        if (RoundTrip_selectedDepartureDateReturn.isAfter(RoundTrip_selectedDepartureDate)) {
                            String formattedDate = RoundTrip_selectedDepartureDateReturn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            tvRoundTrip_departureDateReturn.setText(formattedDate);
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