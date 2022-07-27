package com.example.praca_inzynierska;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowWeeklyCalendarAdapter extends RecyclerView.Adapter<ShowWeeklyCalendarAdapter.ShowWeeklyCalendarAdapterHolder>{
    Context context;
    ArrayList<String> Day;
    ArrayList<String> Number;

    public ShowWeeklyCalendarAdapter(Context ct, ArrayList<String> day, ArrayList<String> number) {
        context = ct;
        Day=day;
        Number = number;
    }

    @NonNull
    @Override
    public ShowWeeklyCalendarAdapter.ShowWeeklyCalendarAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.flip_calendar,parent,false);
        return new ShowWeeklyCalendarAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowWeeklyCalendarAdapterHolder holder, int position) {
        holder.Day_name.setText(Day.get(position));
        holder.Day_number.setText(Number.get(position));
    }

    @Override
    public int getItemCount() {
        return Number.size();
    }

    public static class ShowWeeklyCalendarAdapterHolder extends RecyclerView.ViewHolder {

        TextView Day_name, Day_number;

        public ShowWeeklyCalendarAdapterHolder(@NonNull View itemView) {
            super(itemView);
            Day_name = itemView.findViewById(R.id.Day_of_the_week_name);
            Day_number = itemView.findViewById(R.id.Day_of_the_week_number);
        }
    }
}
