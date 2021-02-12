package net.ideabreed.ibparking.view.checkin; /*
 * Created by Rajan Karki on 2/9/21
 * Copyright @2021
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.model.Passenger;

import java.util.ArrayList;

public class PassengerRecyclerAdapter extends RecyclerView.Adapter<PassengerRecyclerAdapter.PassengerViewHolder> {

    ArrayList<Passenger> passengerTypes = new ArrayList<>();
    Context mContext;
    private int checkedPosition = -1;

    public PassengerRecyclerAdapter(ArrayList<Passenger> passengerTypes, Context mContext) {
        this.passengerTypes = passengerTypes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PassengerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.passanger_type_item,parent,false);
        PassengerViewHolder passengerRecyclerAdapter = new PassengerViewHolder(view);
        return passengerRecyclerAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerViewHolder holder, int position) {
        holder.bind(passengerTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return passengerTypes.size();
    }

    public class PassengerViewHolder extends RecyclerView.ViewHolder{

        ImageView passengerTypeIcon;
        TextView passengerTypeName;
        MaterialCardView passengerTypeCardView;

        public PassengerViewHolder(@NonNull View itemView) {
            super(itemView);

            passengerTypeCardView = itemView.findViewById(R.id.passengerItemTypeCV);
            passengerTypeIcon = itemView.findViewById(R.id.passengerItemIconIV);
            passengerTypeName = itemView.findViewById(R.id.passengerItemNameTV);
        }

        public void bind(final Passenger passengerType){
            if (checkedPosition == -1) {
                passengerTypeCardView.setBackgroundResource(R.drawable.radio_normal);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    passengerTypeCardView.setBackgroundResource(R.drawable.radio_selected);
                } else {
                    passengerTypeCardView.setBackgroundResource(R.drawable.radio_normal);
                }
            }

            passengerTypeName.setText(passengerType.getType());
            passengerTypeIcon.setImageResource(passengerType.getIcon());

            passengerTypeCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passengerTypeCardView.setBackgroundResource(R.drawable.radio_selected);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });


        }

    }
    public Passenger getSelected() {
        if (checkedPosition != -1) {
            return passengerTypes.get(checkedPosition);
        }
        return null;
    }

} 