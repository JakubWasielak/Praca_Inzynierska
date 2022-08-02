package com.example.praca_inzynierska.typeOfTravelFragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.praca_inzynierska.R;

import java.util.Calendar;

public class RoundTripFlightFragment extends Fragment {
    private AutoCompleteTextView mDepartureDateFormat, mReturnDateFormat;
    private DatePickerDialog.OnDateSetListener onDepartureDateSetListener, onReturnDateSetListener;
    private AutoCompleteTextView selectClassOfTravel;
    private final String[] items =  {"Ekonomiczna","Biznesowa","Pierwsza"};
    private final Calendar c = Calendar.getInstance();
    private final int mYear = c.get(Calendar.YEAR);
    private final int mMonth = c.get(Calendar.MONTH);
    private final int mDay = c.get(Calendar.DAY_OF_MONTH);

    private int dYear;
    private int dMonth;
    private int dDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_round_trip_flight,container,false);
        selectClassOfTravel = view.findViewById(R.id.selectClassOfTravel);
        mDepartureDateFormat = view.findViewById(R.id.departureDate_Format);
        mReturnDateFormat = view.findViewById(R.id.returnDate_Format);

        initDepartureDatePicker();
        initBackDatePicker();
        initClassPicker();

        return view;
    }

    private void initBackDatePicker() {

        mReturnDateFormat.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onReturnDateSetListener, dYear, dMonth, dDay+1);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        onReturnDateSetListener = (view, year1, month1, dayOfMonth) -> {
            month1 = month1 +1;
            String DepartureDate = String.valueOf(mReturnDateFormat.getText());
            String date = dayOfMonth+"-"+month1 +"-" + year1;
            String date1 = dayOfMonth+"-"+"0"+month1 +"-" + year1;
            System.out.println(dDay);
            System.out.println(dMonth);
            System.out.println(dYear);
            if(dYear<=year1 && dMonth+1 <=month1 && dDay<=dayOfMonth)
            {
                if(month1>9)
                {
                    mReturnDateFormat.setText(date);
                }
                else
                {
                    mReturnDateFormat.setText(date1);
                }
            }
            else{
                mReturnDateFormat.setText("");
            }
        };
    }

    private void initClassPicker() {
        ArrayAdapter<String> adapterItems = new ArrayAdapter<String>(getActivity(), R.layout.drop_down_menu_list_item, items);
        selectClassOfTravel.setAdapter(adapterItems);
    }

    private void initDepartureDatePicker() {

        mDepartureDateFormat.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDepartureDateSetListener, mYear, mMonth, mDay);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        onDepartureDateSetListener = (view, year1, month1, dayOfMonth) -> {
            month1 = month1 +1;
            String date = dayOfMonth+"-"+month1 +"-" + year1;
            String date1 = dayOfMonth+"-"+"0"+month1 +"-" + year1;
            if(mYear<=year1 && mMonth+1 <=month1 && mDay<=dayOfMonth)
            {
                dDay=dayOfMonth;
                dMonth= month1-1;
                dYear=year1;

                if(month1>9)
                {
                    mDepartureDateFormat.setText(date);
                }
                else
                {
                    mDepartureDateFormat.setText(date1);
                }
            }
            else{
                mDepartureDateFormat.setText("");

            }
        };
    }
}