package net.ideabreed.ibparking.presenter.adapter; /*
 * Created by Rajan Karki on 2/13/21
 * Copyright @2021
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.model.Ticket;

import java.util.ArrayList;

public class TodayLogRecyclerAdapter extends RecyclerView.Adapter<TodayLogRecyclerAdapter.TodayLogViewHolder> {

    ArrayList<Ticket> tickets = new ArrayList<>();
    Context mContext;

    public TodayLogRecyclerAdapter(ArrayList<Ticket> tickets,Context context) {
        this.tickets = tickets;
        this.mContext = context;
    }

    @NonNull
    @Override
    public TodayLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.today_log_item,parent,false);
        TodayLogViewHolder logViewHolder = new TodayLogViewHolder(view);
        return logViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayLogViewHolder holder, int position) {

        holder.todayLogStartStation.setText(tickets.get(position).getCheckInStation().getStationName());
        holder.todayLogEndStation.setText(tickets.get(position).getCheckOutStation().getStationName());
        holder.todayLogType.setText(tickets.get(position).getType().getType());
        holder.todayLogFare.setText("Rs. "+String.valueOf(tickets.get(position).getFare()+"/-"));

    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class TodayLogViewHolder extends RecyclerView.ViewHolder{

        CardView todayLogCardView;
        TextView todayLogStartStation,todayLogEndStation,todayLogFare,todayLogType,todayLogDate;

        public TodayLogViewHolder(@NonNull View itemView) {
            super(itemView);
            todayLogCardView = itemView.findViewById(R.id.todayLogItemCV);
            todayLogStartStation = itemView.findViewById(R.id.todayLogStartStationTV);
            todayLogEndStation = itemView.findViewById(R.id.todayLogEndStationTV);
            todayLogFare = itemView.findViewById(R.id.todayLogFareTV);
            todayLogType = itemView.findViewById(R.id.todayLogTypeTV);

        }
    }
} 