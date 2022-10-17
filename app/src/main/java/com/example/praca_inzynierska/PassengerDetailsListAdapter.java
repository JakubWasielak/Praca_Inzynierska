package com.example.praca_inzynierska;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PassengerDetailsListAdapter extends ArrayAdapter<PassengerModel> {
    private Context context;
    int resource;

    public PassengerDetailsListAdapter(Context context, int resource, ArrayList<PassengerModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String lastName = getItem(position).getLastName();
        int age = getItem(position).getAge();
        String gender = getItem(position).getGender();


        if(convertView == null ){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            TextView tvPassengerName = (TextView) convertView.findViewById(R.id.passengerName_TextView);
            TextView tvPassengerLastName = (TextView) convertView.findViewById(R.id.passengerLastName_TextView);
            TextView tvPassengerAge = (TextView) convertView.findViewById(R.id.passengerAge_TextView);
            TextView tvPassengerGender = (TextView) convertView.findViewById(R.id.passengerGender_TextView);

            tvPassengerName.setText(name);
            tvPassengerLastName.setText(lastName);
            tvPassengerAge.setText(String.valueOf(age)+" l");
            tvPassengerGender.setText(gender);
        }

        return convertView;
    }
}
