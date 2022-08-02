package com.example.praca_inzynierska.typeOfTravelFragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.praca_inzynierska.R;

import java.util.Calendar;

public class OneWayFlightFragment extends Fragment {
    private AutoCompleteTextView mDepartureDateFormat;
    private DatePickerDialog.OnDateSetListener onDepartureDateSetListener;
    private AutoCompleteTextView selectClassOfTravel;
    private final String[] items =  {"Ekonomiczna","Biznesowa","Pierwsza"};
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_way_flight,container,false);
        selectClassOfTravel = view.findViewById(R.id.selectClassOfTravel);
        mDepartureDateFormat = view.findViewById(R.id.departureDate_Format);


        initDepartureDatePicker();
        initClassPicker();
        return view;
    }

    private void initClassPicker() {
        ArrayAdapter<String> adapterItems = new ArrayAdapter<String>(getActivity(), R.layout.drop_down_menu_list_item, items);
        selectClassOfTravel.setAdapter(adapterItems);
    }

    private void initDepartureDatePicker() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

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