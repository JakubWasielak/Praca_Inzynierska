package com.example.praca_inzynierska;

import static java.time.temporal.ChronoUnit.DAYS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SearchResultsActivity extends AppCompatActivity implements RecyclerViewInterface{
    ArrayList<FoundAirlineTicketsModel> foundAirlineTicketsModels = new ArrayList<>();
    RecyclerView Weekly_calendar;
    LocalDate selectedDate = LocalDate.of(2022,8,03);
    ImageButton goToNextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_search_results);

        RecyclerView foundFlightsRecyclerView = findViewById(R.id.foundFlights_RecyclerView);
        setUpFoundAirlineTicketsModels();

        FAT_RecyclerViewAdapter adapter = new FAT_RecyclerViewAdapter(this,foundAirlineTicketsModels, this);
        foundFlightsRecyclerView.setAdapter(adapter);
        foundFlightsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Weekly_calendar = findViewById(R.id.weeklyCalendar_RecyclerView);
        ArrayList<String> dayNumberView = numberDayToView(selectedDate);
        ArrayList<String> dayNameView = nameDayToView(selectedDate);

        ShowWeeklyCalendarAdapter search1 = new ShowWeeklyCalendarAdapter(this,dayNameView,dayNumberView);
        Weekly_calendar.setAdapter(search1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchResultsActivity.this,LinearLayoutManager.HORIZONTAL,false);
        Weekly_calendar.setLayoutManager(layoutManager);

        goToNextActivity = findViewById(R.id.goToNextActivity_ImageButton);

    }

    private void setUpFoundAirlineTicketsModels(){
        String[] foundAirlineTicketsDepartureAirportCodes = getResources().getStringArray(R.array.Departure_Code);
        String[] foundAirlineTicketsDepartureAirportCityNames = getResources().getStringArray(R.array.Departure_City);
        String[] foundAirlineTicketsArrivalAirportCodes = getResources().getStringArray(R.array.Arrival_Code);
        String[] foundAirlineTicketsArrivalAirportCityNames = getResources().getStringArray(R.array.Arrival_City);
        String[] foundAirlineTicketsFlightsDuration = getResources().getStringArray(R.array.Duration_Flight);
        String[] foundAirlineTicketsDeparturesDatesAndTimes = getResources().getStringArray(R.array.Flight_Date);
        String[] foundAirlineTicketsFlightNumbers = getResources().getStringArray(R.array.Flight_Number);
        String[] foundAirlineTicketsTicketPrices = getResources().getStringArray(R.array.Ticket_Price);

        for(int i =0; i < foundAirlineTicketsDepartureAirportCodes.length; i++){
            foundAirlineTicketsModels.add(new FoundAirlineTicketsModel(foundAirlineTicketsDepartureAirportCodes[i],
                    foundAirlineTicketsDepartureAirportCityNames[i],
                    foundAirlineTicketsArrivalAirportCodes[i],
                    foundAirlineTicketsArrivalAirportCityNames[i],
                    foundAirlineTicketsFlightsDuration[i],
                    foundAirlineTicketsDeparturesDatesAndTimes[i],
                    foundAirlineTicketsFlightNumbers[i],
                    foundAirlineTicketsTicketPrices[i]));
        }
    }


    private static ArrayList<String> numberDayToView(LocalDate selecteddate)
    {
        ArrayList<String> NumberOfdays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int daysBetween = (int) DAYS.between(today, selecteddate);

        if(!selecteddate.equals(today))
        {
            if(daysBetween<7)
            {
                int j =daysBetween-1;
                for(int i=0;i<daysBetween;i++)
                {

                    NumberOfdays.add(String.valueOf(selecteddate.minusDays(j).getDayOfMonth()));
                    j--;
                }
                for(int i=1;i<=7;i++)
                {
                    NumberOfdays.add(String.valueOf(selecteddate.plusDays(i).getDayOfMonth()));
                }
            }
            else {
                int j =6;
                for(int i=0;i<7;i++)
                {
                    NumberOfdays.add(String.valueOf(selecteddate.minusDays(j).getDayOfMonth()));
                    j--;
                }
                for(int i=1;i<=7;i++)
                {
                    NumberOfdays.add(String.valueOf(selecteddate.plusDays(i).getDayOfMonth()));
                }
            }
        }
        else
        {
            for(int i=0;i<=14;i++)
            {
                NumberOfdays.add(String.valueOf(selecteddate.plusDays(i).getDayOfMonth()));
            }
        }

        return NumberOfdays;
    }
    private static ArrayList<String> nameDayToView(LocalDate selecteddate)
    {
        ArrayList<String> NameOfdays = new ArrayList<>();

        LocalDate today = LocalDate.now();
        int daysBetween = (int) DAYS.between(today, selecteddate);

        if(!selecteddate.equals(today))
        {
            if(daysBetween<7)
            {
                int j =daysBetween-1;
                for(int i=0;i<daysBetween;i++)
                {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(j)));
                    j--;
                }
                for(int i=1;i<=7;i++)
                {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(i)));
                }
            }
            else {
                int j =6;
                for(int i=0;i<7;i++)
                {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(j)));
                    j--;
                }
                for(int i=1;i<=7;i++)
                {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(i)));
                }
            }
        }
        else
        {
            for(int i=0;i<=14;i++)
            {
                NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(i)));
            }
        }

        return NameOfdays;
    }
    private static String nameDayOfWeek(LocalDate selecteddate)
    {
        switch (String.valueOf(selecteddate.getDayOfWeek())) {
            case "MONDAY":
                return "Pon";
            case "TUESDAY":
                return "Wto";
            case "WEDNESDAY":
                return "Śro";
            case "THURSDAY":
                return "Czw";
            case "FRIDAY":
                return "Pią";
            case "SATURDAY":
                return "Sob";
            case "SUNDAY":
                return "Nie";
            default:
                System.out.println("Błąd!!");
        }
        return null;
    }

    public void backToSearchingForTicketAcitivity(View view) {
        Intent intent = new Intent(this, SearchingForTicketActivity.class);
        startActivity(intent);
    }

    public void goToSeatingChoiceAcitivity(View view) {
        Intent intent = new Intent(this, SeatingChoiceActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        goToNextActivity.setVisibility(View.VISIBLE);
        Intent intent = new Intent(SearchResultsActivity.this,SeatingChoiceActivity.class);
        intent.putExtra("departureAirportCode",foundAirlineTicketsModels.get(position).getDepartureAirportCode());
        intent.putExtra("departureAirportCityName",foundAirlineTicketsModels.get(position).getDepartureAirportCityName());
        intent.putExtra("arrivalAirportCode",foundAirlineTicketsModels.get(position).getArrivalAirportCode());
        intent.putExtra("arrivalAirportCityName",foundAirlineTicketsModels.get(position).getArrivalAirportCityName());
        intent.putExtra("flightDuration",foundAirlineTicketsModels.get(position).getFlightDuration());
        intent.putExtra("departureDateAndTime",foundAirlineTicketsModels.get(position).getDepartureDateAndTime());
        intent.putExtra("flightNumber",foundAirlineTicketsModels.get(position).getFlightNumber());
        goToNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}
