package net.ideabreed.ibparking.presenter.adapter; /*
 * Created by Rajan Karki on 2/13/21
 * Copyright @2021
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.model.Station;

import java.util.ArrayList;

public class StationSpinnerAdapter extends ArrayAdapter<Station> {

    public StationSpinnerAdapter(@NonNull Context context, ArrayList<Station> stations) {
        super(context, 0, stations);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_station_item, parent, false);
        }

        TextView spinnerStationName, spinnerStationCode;
        spinnerStationCode = convertView.findViewById(R.id.spinnerStationCode);
        spinnerStationName = convertView.findViewById(R.id.spinerStationName);

        Station station = getItem(position);

        if (station != null) {
            spinnerStationName.setText(station.getStationName());
            spinnerStationCode.setText(station.getStationCode());
        }
        return convertView;
    }
}