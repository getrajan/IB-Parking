package net.ideabreed.ibparking.view.checkin; /*
 * Created by Rajan Karki on 2/8/21
 * Copyright @2021
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.model.Station;

import java.util.ArrayList;

public class SelectStationRecyclerViewAdapter extends RecyclerView.Adapter<SelectStationRecyclerViewAdapter.StationViewHolder> {
    ArrayList<Station> stationList = new ArrayList<>();
    Context mContext;
    boolean isSelect = false;
    static private int checkedPosition = -1; //-1: no default selection 0: first item selected

    public SelectStationRecyclerViewAdapter(ArrayList<Station> stationList, Context mContext) {
        this.stationList = stationList;
        this.mContext = mContext;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stationList = new ArrayList<Station>();
        this.stationList = stations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_station_recycler_view_item, parent, false);
        StationViewHolder viewHolder = new StationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        holder.bind(stationList.get(position));
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    public class StationViewHolder extends RecyclerView.ViewHolder {
        TextView stationNameTextView;
        MaterialCardView stationCardView;

        public StationViewHolder(@NonNull View itemView) {
            super(itemView);
            stationNameTextView = itemView.findViewById(R.id.stationItemName);
            stationCardView = itemView.findViewById(R.id.stationNameItemCV);
        }

        @SuppressLint("ResourceAsColor")
        void bind(final Station station) {
            if (checkedPosition == -1) {
                stationCardView.setBackgroundResource(R.drawable.radio_normal);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    stationCardView.setBackgroundResource(R.drawable.radio_selected);
                } else {
                    stationCardView.setBackgroundResource(R.drawable.radio_normal);
                }
            }

            stationNameTextView.setText(station.getStationName());
            stationCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stationCardView.setBackgroundResource(R.drawable.radio_selected);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public Station getSelected() {
        if (checkedPosition != -1) {
            return stationList.get(checkedPosition);
        }
        return null;
    }

}