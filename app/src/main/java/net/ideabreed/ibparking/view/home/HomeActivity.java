package net.ideabreed.ibparking.view.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanDevice;
import android.os.Bundle;
import android.posapi.PosApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.model.Station;
import net.ideabreed.ibparking.presenter.HomeService;
import net.ideabreed.ibparking.presenter.adapter.StationSpinnerAdapter;
import net.ideabreed.ibparking.presenter.helper.PowerUtils;
import net.ideabreed.ibparking.utils.UploadData;
import net.ideabreed.ibparking.view.checkin.CheckInActivity;
import net.ideabreed.ibparking.view.checkout.CheckoutActivity;
import net.ideabreed.ibparking.view.log.TodayLogActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, HomeService.View {

    private Button homeCheckInButton, homeCheckOutButton,homeTodayLogButton;

    private Spinner stationSpinner;
    private StationSpinnerAdapter stationSpinnerAdapter;
    private ArrayList<Station> stations;
    private Station checkedStation;
    private UploadData uploadData;

    private String barcodeStr = "";
    private final static String SCAN_ACTION = "scan.rcv.message";

    private ScanDevice sm;
    private PosApi posApi;
    private Station checkoutStation;


    private BroadcastReceiver broadcastScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] barcode = intent.getByteArrayExtra("barocode");
            int barcodeLength = intent.getIntExtra("length", 0);

            barcodeStr = new String(barcode, 0, barcodeLength);

            Intent nextIntent = new Intent(HomeActivity.this, CheckoutActivity.class);
            nextIntent.putExtra("scannedText", barcodeStr);
            nextIntent.putExtra("checkoutStation", checkoutStation);

            startActivity(nextIntent);

            sm.closeScan();
            sm.openScan();


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PowerUtils.power("1");
        posApi = PosApi.getInstance(this);
        posApi.initDeviceEx("/dev/ttyMT2");

        sm = new ScanDevice();
        sm.setOutScanMode(0);
        sm.openScan();

        initView();
    }

    private void initView() {

        homeCheckInButton = findViewById(R.id.homeCheckInBtn);
        homeCheckOutButton = findViewById(R.id.homeCheckOutBtn);
        stationSpinner = findViewById(R.id.stationSpinner);
        homeTodayLogButton = findViewById(R.id.homeTodayLogBtn);


        homeCheckInButton.setOnClickListener(this);
        homeTodayLogButton.setOnClickListener(this);
        uploadData = new UploadData();

        stationSpinnerAdapter = new StationSpinnerAdapter(this, uploadData.allStations());
        stationSpinner.setAdapter(stationSpinnerAdapter);

        stationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                checkedStation = (Station) parent.getItemAtPosition(position);
                checkoutStation = new Station(checkedStation.getStationName(), checkedStation.getStationCode(), true, checkedStation.getStationId());
                Toast.makeText(HomeActivity.this, "select " + checkedStation.getStationId() + " " + checkedStation.getStationName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SCAN_ACTION);
        registerReceiver(broadcastScanReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sm != null) {
            sm.stopScan();
        }
        unregisterReceiver(broadcastScanReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (posApi != null) {
            posApi.closeDev();
        }

        PowerUtils.power("0");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeCheckInBtn:
                if (checkedStation != null) {
                    onNavigate(checkedStation);
                }
                break;
            case R.id.homeTodayLogBtn:
                Intent intent = new Intent(this, TodayLogActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onNavigate(Station station) {
        Intent intent = new Intent(this, CheckInActivity.class);
        intent.putExtra("stationIntent", station);
        startActivity(intent);
    }


}