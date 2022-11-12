package com.example.praca_inzynierska;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PassengerDetailsListAdapter extends ArrayAdapter<PassengerModel> {
    private final Context context;
    int resource;

    public PassengerDetailsListAdapter(Context context, int resource, ArrayList<PassengerModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String lastName = getItem(position).getLastName();
        int age = getItem(position).getAge();
        String gender = getItem(position).getGender();
        boolean isAdult = getItem(position).isAdult();

        if(convertView == null ){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            TextView tvPassengerName = (TextView) convertView.findViewById(R.id.passengerName_TextView);
            TextView tvPassengerLastName = (TextView) convertView.findViewById(R.id.passengerLastName_TextView);
            TextView tvPassengerAge = (TextView) convertView.findViewById(R.id.passengerAge_TextView);
            TextView tvPassengerGender = (TextView) convertView.findViewById(R.id.passengerGender_TextView);
            ImageView imgPassengerIcon = (ImageView) convertView.findViewById(R.id.passengerIcon_ImageView);

            if(isAdult){
                imgPassengerIcon.setBackgroundResource(R.drawable.ic_adult_passenger);
            }else {
                imgPassengerIcon.setBackgroundResource(R.drawable.ic_child_passenger);
            }
            tvPassengerName.setText(name);
            tvPassengerLastName.setText(lastName);
            tvPassengerAge.setText(age +" l");
            tvPassengerGender.setText(gender+", ");
        }

        return convertView;
    }
}
