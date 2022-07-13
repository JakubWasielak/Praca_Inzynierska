package com.example.praca_inzynierska;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowWeeklyCalendarAdapter extends RecyclerView.Adapter<ShowWeeklyCalendarAdapter.ShowWeeklyCalendarAdapterHolder>{
    Context context;
    String[] Day;
    String[] Number;

    public ShowWeeklyCalendarAdapter(Context ct, String[] Day_of_week, String[] Number_of_day) {
        context = ct;
        Day = Day_of_week;
        Number = Number_of_day;
    }

    @NonNull
    @Override
    public ShowWeeklyCalendarAdapter.ShowWeeklyCalendarAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.flip_calendar,parent,false);
        return new ShowWeeklyCalendarAdapter.ShowWeeklyCalendarAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowWeeklyCalendarAdapterHolder holder, int position) {
        holder.Day_name.setText(Day[position]);
        holder.Day_number.setText(Number[position]);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ShowWeeklyCalendarAdapterHolder extends RecyclerView.ViewHolder {

        TextView Day_name, Day_number;

        public ShowWeeklyCalendarAdapterHolder(@NonNull View itemView) {
            super(itemView);
            Day_name = itemView.findViewById(R.id.Day_of_the_week_name);
            Day_number = itemView.findViewById(R.id.Day_of_the_week_number);
        }
    }
}
