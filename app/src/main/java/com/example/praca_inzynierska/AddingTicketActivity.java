package com.example.praca_inzynierska;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddingTicketActivity extends AppCompatActivity {
    AutoCompleteTextView mDateFormat;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    TextView Bilet_tab;
    ImageButton goToPreviousActivity;
    ImageButton goToNextActivity;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_adding_ticket);

        initDatePicker();
        initClassPicker();

    }
    private void initDatePicker(){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        mDateFormat = findViewById(R.id.dateFormat);
        mDateFormat.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddingTicketActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener, mYear, mMonth, mDay);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        onDateSetListener = (view, year1, month1, dayOfMonth) -> {
            month1 = month1 +1;
            String date = dayOfMonth+"-"+month1 +"-" + year1;
            String date1 = dayOfMonth+"-"+"0"+month1 +"-" + year1;
            if(mYear<=year1 && mMonth+1 <=month1 && mDay<=dayOfMonth)
            {
                if(month1>9)
                {
                    mDateFormat.setText(date);
                }
                else
                {
                    mDateFormat.setText(date1);
                }
            }
            else{
                mDateFormat.setText("");
            }
        };
    }

    private void initClassPicker() {

        String[] items =  {"Ekonomiczna","Biznesowa","Pierwsza"};
        AutoCompleteTextView autoCompleteTxt;
        ArrayAdapter<String> adapterItems;
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<>(this, R.layout.drop_down_menu_list_item, items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener((parent, view, position, id) -> {
            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
        });
    }

    public void goToPreviousActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void goToNextActivity(View view) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        startActivity(intent);
    }
}