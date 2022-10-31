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
import com.example.praca_inzynierska.FAT_RecyclerViewAdapter;
import com.example.praca_inzynierska.R;
import com.example.praca_inzynierska.RecyclerViewInterface;
import com.example.praca_inzynierska.SearchFlightsDataService;
import com.example.praca_inzynierska.SeatingChoiceActivity;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BestTicketSearchResultsFragment extends Fragment implements RecyclerViewInterface {
    private RecyclerView rvBestTicketsFound;
    private ImageButton btnOpenNextActivity;
    private int selectedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_best_ticket_search_results, container, false);

        final SearchFlightsDataService searchFlightsDataService = new SearchFlightsDataService(getActivity());
        rvBestTicketsFound = view.findViewById(R.id.foundBestFlights_RecyclerView);
        btnOpenNextActivity = view.findViewById(R.id.btnBestOpenNextActivity_ImageButton);

        Intent intent = getActivity().getIntent();

        boolean oneWayFlight = intent.getExtras().getBoolean("OneWayFlight");

        String departureCode = intent.getStringExtra("DepartureCode");
        String arrivalCode = intent.getStringExtra("ArrivalCode");
        String selectedDepartureDate = intent.getStringExtra("SelectedDepartureDate");
        String travelClass = intent.getStringExtra("TravelClass");
        int numberPassengersAdults = intent.getIntExtra("NumberPassengersAdults", 0);
        int numberPassengersChildren = intent.getIntExtra("NumberPassengersChildren", 0);

        if (!oneWayFlight) {
            String selectedDepartureDateReturn = intent.getStringExtra("SelectedDepartureDateReturn");
            searchFlightsDataService.getBestTickets(departureCode, arrivalCode, selectedDepartureDate, travelClass, numberPassengersAdults, numberPassengersChildren, false, new SearchFlightsDataService.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onResponse(ArrayList<AirlineTicketModel> airlineTicketModel) {
                    ArrayList<AirlineTicketModel> TicketModel = new ArrayList<>();
                    for (int i = 0; i < airlineTicketModel.size(); i++) {
                        TicketModel.add(new AirlineTicketModel(
                                airlineTicketModel.get(i).getDepartureAirportCode(),
                                airlineTicketModel.get(i).getDepartureAirportName(),
                                airlineTicketModel.get(i).getArrivalAirportCode(),
                                airlineTicketModel.get(i).getArrivalAirportName(),
                                airlineTicketModel.get(i).getFlightDuration(),
                                airlineTicketModel.get(i).getDepartureDate(),
                                airlineTicketModel.get(i).getDepartureTime(),
                                airlineTicketModel.get(i).getFlightNumber(),
                                airlineTicketModel.get(i).getTravelClass(),
                                airlineTicketModel.get(i).getTicketPrice(),
                                airlineTicketModel.get(i).getNumberPassengersAdults(),
                                airlineTicketModel.get(i).getNumberPassengersChildren(),
                                airlineTicketModel.get(i).isOneWayFlight(),
                                airlineTicketModel.get(i).isTicketConnecting()));
                    }
                    rvBestTicketsFound.setLayoutManager(new LinearLayoutManager(getActivity()));
                    FAT_RecyclerViewAdapter FAT_RecyclerViewAdapter = new FAT_RecyclerViewAdapter(getActivity(), TicketModel, BestTicketSearchResultsFragment.this);
                    rvBestTicketsFound.setAdapter(FAT_RecyclerViewAdapter);

                    btnOpenNextActivity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), SeatingChoiceActivity.class);
                            TicketModel.get(selectedPosition).setDepartureDateReturn(selectedDepartureDateReturn);
                            intent.putExtra("AirlineTicketsModels", TicketModel.get(selectedPosition));
                            startActivity(intent);
                        }
                    });
                }
            });

        } else {
            searchFlightsDataService.getBestTickets(departureCode, arrivalCode, selectedDepartureDate, travelClass, numberPassengersAdults, numberPassengersChildren, true, new SearchFlightsDataService.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onResponse(ArrayList<AirlineTicketModel> airlineTicketModel) {
                    ArrayList<AirlineTicketModel> TicketModel = new ArrayList<>();
                    for (int i = 0; i < airlineTicketModel.size(); i++) {
                        TicketModel.add(new AirlineTicketModel(
                                airlineTicketModel.get(i).getDepartureAirportCode(),
                                airlineTicketModel.get(i).getDepartureAirportName(),
                                airlineTicketModel.get(i).getArrivalAirportCode(),
                                airlineTicketModel.get(i).getArrivalAirportName(),
                                airlineTicketModel.get(i).getFlightDuration(),
                                airlineTicketModel.get(i).getDepartureDate(),
                                airlineTicketModel.get(i).getDepartureTime(),
                                airlineTicketModel.get(i).getFlightNumber(),
                                airlineTicketModel.get(i).getTravelClass(),
                                airlineTicketModel.get(i).getTicketPrice(),
                                airlineTicketModel.get(i).getNumberPassengersAdults(),
                                airlineTicketModel.get(i).getNumberPassengersChildren(),
                                airlineTicketModel.get(i).isOneWayFlight(),
                                airlineTicketModel.get(i).isTicketConnecting()));
                    }
                    rvBestTicketsFound.setLayoutManager(new LinearLayoutManager(getActivity()));
                    FAT_RecyclerViewAdapter FAT_RecyclerViewAdapter = new FAT_RecyclerViewAdapter(getActivity(), TicketModel, BestTicketSearchResultsFragment.this);
                    rvBestTicketsFound.setAdapter(FAT_RecyclerViewAdapter);

                    btnOpenNextActivity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), SeatingChoiceActivity.class);
                            intent.putExtra("AirlineTicketsModels", TicketModel.get(selectedPosition));
                            startActivity(intent);
                        }
                    });
                }
            });
        }
        return view;
    }

    @Override
    public void onItemClick(int position) {
        btnOpenNextActivity.setVisibility(View.VISIBLE);
        selectedPosition = position;
    }
}