package com.example.praca_inzynierska.TicketSearchResults;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praca_inzynierska.AirlineTicketModel;
import com.example.praca_inzynierska.AirportModel;
import com.example.praca_inzynierska.FAT_RecyclerViewAdapter;
import com.example.praca_inzynierska.R;
import com.example.praca_inzynierska.RecyclerViewInterface;
import com.example.praca_inzynierska.SearchFlightsDataService;
import com.example.praca_inzynierska.SeatingChoiceActivity;

import java.util.ArrayList;

public class FastestTicketSearchResultsFragment extends Fragment implements RecyclerViewInterface {
    private RecyclerView rvFastestTicketsFound;
    private ImageButton btnOpenNextActivity;

    private boolean oneWayFlight;
    private AirportModel departureAirport, arrivalAirport;
    private String selectedDepartureDate, selectedDepartureDateReturn, travelClass;
    private int numberPassengersAdults, numberPassengersChildren;
    private int selectedPosition;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fastest_ticket_search_results, container, false);

        final SearchFlightsDataService searchFlightsDataService = new SearchFlightsDataService(getActivity());

        rvFastestTicketsFound = view.findViewById(R.id.foundFastestFlights_RecyclerView);
        btnOpenNextActivity = view.findViewById(R.id.btnFastestOpenNexttActivity_ImageButton);

        getIntentData();
        searchFlightsDataService.getFastestTickets(departureAirport, arrivalAirport, selectedDepartureDate, travelClass, numberPassengersAdults, numberPassengersChildren, oneWayFlight, new SearchFlightsDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), "Nie znaleziono połączeń.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<AirlineTicketModel> ticketsFoundList) {
                ArrayList<AirlineTicketModel> TicketsList = new ArrayList<>();
                for (int i = 0; i < ticketsFoundList.size(); i++) {
                    TicketsList.add(new AirlineTicketModel(
                            ticketsFoundList.get(i).getDepartureAirport(),
                            ticketsFoundList.get(i).getArrivalAirport(),
                            ticketsFoundList.get(i).getFlightDuration(),
                            ticketsFoundList.get(i).getDepartureDate(),
                            ticketsFoundList.get(i).getDepartureTime(),
                            ticketsFoundList.get(i).getFlightNumber(),
                            ticketsFoundList.get(i).getTravelClass(),
                            ticketsFoundList.get(i).getTicketPrice(),
                            ticketsFoundList.get(i).getNumberPassengersAdults(),
                            ticketsFoundList.get(i).getNumberPassengersChildren(),
                            ticketsFoundList.get(i).isOneWayFlight(),
                            ticketsFoundList.get(i).getIsConnecting()));
                }
                rvFastestTicketsFound.setLayoutManager(new LinearLayoutManager(getActivity()));
                FAT_RecyclerViewAdapter FAT_RecyclerViewAdapter = new FAT_RecyclerViewAdapter(getActivity(), TicketsList, FastestTicketSearchResultsFragment.this);
                rvFastestTicketsFound.setAdapter(FAT_RecyclerViewAdapter);

                btnOpenNextActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SeatingChoiceActivity.class);
                        if(!oneWayFlight){
                            TicketsList.get(selectedPosition).setDepartureDateReturn(selectedDepartureDateReturn);
                        }
                        intent.putExtra("SelectedTicket", TicketsList.get(selectedPosition));
                        startActivity(intent);
                    }
                });
            }
        });

        return view;
    }

    private void getIntentData() {
        if (getActivity().getIntent().hasExtra("DepartureAirport") && getActivity().getIntent().hasExtra("ArrivalAirport") && getActivity().getIntent().hasExtra("SelectedDepartureDate") && getActivity().getIntent().hasExtra("TravelClass") && getActivity().getIntent().hasExtra("NumberPassengersAdults") && getActivity().getIntent().hasExtra("NumberPassengersChildren") && getActivity().getIntent().hasExtra("OneWayFlight")) {
            departureAirport = getActivity().getIntent().getParcelableExtra("DepartureAirport");
            arrivalAirport = getActivity().getIntent().getParcelableExtra("ArrivalAirport");
            selectedDepartureDate = getActivity().getIntent().getStringExtra("SelectedDepartureDate");
            travelClass = getActivity().getIntent().getStringExtra("TravelClass");
            numberPassengersAdults = getActivity().getIntent().getIntExtra("NumberPassengersAdults", 0);
            numberPassengersChildren = getActivity().getIntent().getIntExtra("NumberPassengersChildren", 0);
            oneWayFlight = getActivity().getIntent().getExtras().getBoolean("OneWayFlight");
            if (getActivity().getIntent().hasExtra("SelectedDepartureDateReturn")) {
                selectedDepartureDateReturn = getActivity().getIntent().getStringExtra("SelectedDepartureDateReturn");
            }
        } else {
            Toast.makeText(getActivity(), "Brak danych.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(int position) {
        btnOpenNextActivity.setVisibility(View.VISIBLE);
        selectedPosition = position;
    }
}