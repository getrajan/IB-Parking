package net.ideabreed.ibparking.view.checkin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.model.Passenger;
import net.ideabreed.ibparking.model.Station;
import net.ideabreed.ibparking.model.Ticket;
import net.ideabreed.ibparking.presenter.StationCheckInService;

import java.util.ArrayList;

public class StationCheckinActivity extends AppCompatActivity implements View.OnClickListener, StationCheckInService.View {
    private TextView studentTypeTextView;
    private ImageView studentTypeImageView;
    private RecyclerView stationNameRecyclerView;
    private LinearLayout backOption;
    private SelectStationRecyclerViewAdapter selectStationGridViewAdapter;
    private PassengerRecyclerAdapter passengerAdapter;
    private Button nextButton;
    private RecyclerView passengerTyperRecylerView;


    private Ticket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_checkin);

        initView();
    }

    private void initView() {
        stationNameRecyclerView = findViewById(R.id.checkInStationSelectStationRV);
        nextButton = findViewById(R.id.checkInStationNextBtn);
        passengerTyperRecylerView = findViewById(R.id.passengerTypeRV);
        backOption = findViewById(R.id.stationCheckInBackLL);

        nextButton.setOnClickListener(this);
        backOption.setOnClickListener(this);

        setPassengerAdapter();

        setStationAdapter();
    }

    public void setPassengerAdapter() {

        ArrayList<Passenger> passengerTypes = new ArrayList<>();
        passengerTypes.add(new Passenger("Normal", R.drawable.ic_normal_person_34,1,"AB",15));
        passengerTypes.add(new Passenger("Elderly", R.drawable.ic_normal_person_34,1,"AB",15));
        passengerTypes.add(new Passenger("Student", R.drawable.ic_normal_person_34,1,"AB",15));

        passengerAdapter = new PassengerRecyclerAdapter(passengerTypes, this);

        passengerTyperRecylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        passengerTyperRecylerView.setAdapter(passengerAdapter);
//        passengerTyperRecylerView.setLayoutFrozen(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.checkInStationNextBtn:
                if (selectStationGridViewAdapter.getSelected() != null && passengerAdapter.getSelected() != null) {
                    Ticket checkInData = new Ticket(passengerAdapter.getSelected().getType(), selectStationGridViewAdapter.getSelected().getStationName());
                    onNavigate(checkInData);
                } else {
                    onError("Please select both type and station");
                }
                break;
            case R.id.stationCheckInBackLL:
                finish();
                break;
        }

    }

    private void setStationAdapter() {
        ArrayList<Station> stationNameList = new ArrayList<>();
        stationNameList.add(new Station("Koteshwor", "AB",false,1));
        stationNameList.add(new Station("Balkhu", "AB",false,1));
        stationNameList.add(new Station("Koteshwor", "AB",false,1));
        stationNameList.add(new Station("Koteshwor", "AB",false,1));
        stationNameList.add(new Station("Koteshwor", "AB",false,1));


        selectStationGridViewAdapter = new SelectStationRecyclerViewAdapter(stationNameList, this);
        stationNameRecyclerView.setAdapter(selectStationGridViewAdapter);
        stationNameRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    public void onError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNavigate(final Ticket ticket) {
        Log.d("onNav", ticket.getType());
        Intent intent = new Intent(this, QrGenerateActivity.class);
        intent.putExtra("checkInTicketData", ticket);
        startActivity(intent);

    }

}

