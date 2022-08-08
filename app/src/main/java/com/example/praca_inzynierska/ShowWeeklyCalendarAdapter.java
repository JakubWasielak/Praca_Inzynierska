package com.example.praca_inzynierska;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowWeeklyCalendarAdapter extends RecyclerView.Adapter<ShowWeeklyCalendarAdapter.ShowWeeklyCalendarAdapterHolder>{
    Context context;
    ArrayList<String> Day;
    ArrayList<String> Number;
    int singleItem_selection_position = -1;

    public ShowWeeklyCalendarAdapter(Context ct, ArrayList<String> day, ArrayList<String> number) {
        context = ct;
        Day=day;
        Number = number;
    }

    @NonNull
    @Override
    public ShowWeeklyCalendarAdapter.ShowWeeklyCalendarAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dayofweek_item,parent,false);
        return new ShowWeeklyCalendarAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowWeeklyCalendarAdapterHolder holder, int position) {
        holder.Day_name.setText(Day.get(position));
        holder.Day_number.setText(Number.get(position));
        if(singleItem_selection_position == position)
        {
            holder.ivBackground.setBackgroundResource(R.drawable.bg_background_found_ticket_selected);
        }else {
            holder.ivBackground.setBackgroundResource(R.drawable.bg_background_found_ticket);
        }
    }

    @Override
    public int getItemCount() {
        return Number.size();
    }

    public class ShowWeeklyCalendarAdapterHolder extends RecyclerView.ViewHolder {
        ImageView ivBackground;
        TextView Day_name;
        TextView Day_number;

        public ShowWeeklyCalendarAdapterHolder(@NonNull View itemView) {
            super(itemView);
            Day_name = itemView.findViewById(R.id.dayOfWeekName_TextView);
            Day_number = itemView.findViewById(R.id.dayOfWeekNumber_TextView);
            ivBackground = itemView.findViewById(R.id.background_ImageView);

            ivBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSingleSelection(getAdapterPosition());
                }
            });
        }
        private void setSingleSelection(int adapterPosition) {
            if(adapterPosition == RecyclerView.NO_POSITION) return;
            notifyItemChanged(singleItem_selection_position);
            singleItem_selection_position = adapterPosition;
            notifyItemChanged(singleItem_selection_position);
        }
    }
}
