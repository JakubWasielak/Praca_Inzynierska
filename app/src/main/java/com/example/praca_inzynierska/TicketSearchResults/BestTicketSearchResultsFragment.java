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

@RequiresApi(api = Build.VERSION_CODES.O)
public class BestTicketSearchResultsFragment extends Fragment implements RecyclerViewInterface {
    private RecyclerView rvBestTicketsFound;
    private ImageButton btnOpenNextActivity;

    private boolean oneWayFlight;
    private AirportModel departureAirport,arrivalAirport;
    private String selectedDepartureDate, selectedDepartureDateReturn, travelClass;
    private int numberPassengersAdults, numberPassengersChildren;
    private int selectedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_best_ticket_search_results, container, false);

        final SearchFlightsDataService searchFlightsDataService = new SearchFlightsDataService(getActivity());
        rvBestTicketsFound = view.findViewById(R.id.foundBestFlights_RecyclerView);
        btnOpenNextActivity = view.findViewById(R.id.btnBestOpenNextActivity_ImageButton);

        getIntentData();

        if (!oneWayFlight) {
            searchFlightsDataService.getBestTickets(departureAirport, arrivalAirport, selectedDepartureDate, travelClass, numberPassengersAdults, numberPassengersChildren, false, new SearchFlightsDataService.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT).show();
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
                    rvBestTicketsFound.setLayoutManager(new LinearLayoutManager(getActivity()));
                    FAT_RecyclerViewAdapter FAT_RecyclerViewAdapter = new FAT_RecyclerViewAdapter(getActivity(), TicketsList, BestTicketSearchResultsFragment.this);
                    rvBestTicketsFound.setAdapter(FAT_RecyclerViewAdapter);

                    btnOpenNextActivity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), SeatingChoiceActivity.class);
                            TicketsList.get(selectedPosition).setDepartureDateReturn(selectedDepartureDateReturn);
                            intent.putExtra("SelectedTicket", TicketsList.get(selectedPosition));
                            startActivity(intent);
                        }
                    });
                }
            });

        } else {
            searchFlightsDataService.getBestTickets(departureAirport, arrivalAirport, selectedDepartureDate, travelClass, numberPassengersAdults, numberPassengersChildren, true, new SearchFlightsDataService.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT).show();
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
                    rvBestTicketsFound.setLayoutManager(new LinearLayoutManager(getActivity()));
                    FAT_RecyclerViewAdapter FAT_RecyclerViewAdapter = new FAT_RecyclerViewAdapter(getActivity(), TicketsList, BestTicketSearchResultsFragment.this);
                    rvBestTicketsFound.setAdapter(FAT_RecyclerViewAdapter);

                    btnOpenNextActivity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), SeatingChoiceActivity.class);
                            intent.putExtra("SelectedTicket", TicketsList.get(selectedPosition));
                            startActivity(intent);
                        }
                    });
                }
            });
        }
        return view;
    }

    private void getIntentData() {
        if (getActivity().getIntent().hasExtra("OneWayFlight")
                && getActivity().getIntent().hasExtra("departureAirport")
                && getActivity().getIntent().hasExtra("arrivalAirport")
                && getActivity().getIntent().hasExtra("SelectedDepartureDate")
                && getActivity().getIntent().hasExtra("TravelClass")
                && getActivity().getIntent().hasExtra("NumberPassengersAdults")
                && getActivity().getIntent().hasExtra("NumberPassengersChildren")) {
            if (getActivity().getIntent().hasExtra("SelectedDepartureDateReturn")) {
                selectedDepartureDateReturn = getActivity().getIntent().getStringExtra("SelectedDepartureDateReturn");
            } else {
                oneWayFlight = getActivity().getIntent().getExtras().getBoolean("OneWayFlight");
                departureAirport = getActivity().getIntent().getParcelableExtra("departureAirport");
                arrivalAirport = getActivity().getIntent().getParcelableExtra("arrivalAirport");
                selectedDepartureDate = getActivity().getIntent().getStringExtra("SelectedDepartureDate");
                travelClass = getActivity().getIntent().getStringExtra("TravelClass");
                numberPassengersAdults = getActivity().getIntent().getIntExtra("NumberPassengersAdults", 0);
                numberPassengersChildren = getActivity().getIntent().getIntExtra("NumberPassengersChildren", 0);
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