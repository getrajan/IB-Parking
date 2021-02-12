package net.ideabreed.ibparking.view.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanDevice;
import android.os.Bundle;
import android.posapi.PosApi;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.presenter.helper.PowerUtils;
import net.ideabreed.ibparking.view.checkin.StationCheckinActivity;
import net.ideabreed.ibparking.view.checkout.CheckoutActivity;

public class HomeActivity extends AppCompatActivity {

    private MaterialCardView checkInCardView,checkOutCardView;

    private String barcodeStr = "";
    private final static String SCAN_ACTION = "scan.rcv.message";

    private ScanDevice sm;
    private PosApi posApi;


    private BroadcastReceiver broadcastScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] barcode = intent.getByteArrayExtra("barocode");
            int barcodeLength = intent.getIntExtra("length",0);

            barcodeStr = new String(barcode,0,barcodeLength);

            Intent nextIntent = new Intent(HomeActivity.this, CheckoutActivity.class);
            nextIntent.putExtra("scannedText",barcodeStr);

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

        sm= new ScanDevice();
        sm.setOutScanMode(0);
        sm.openScan();

        initView();
    }

    private void initView() {

        checkInCardView = findViewById(R.id.checkinCV);
        checkOutCardView = findViewById(R.id.checkoutCV);

        checkInCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, StationCheckinActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        PowerUtils.power("0");

        // Enable the two and four wheeler button.
//        setCheckInEnabled(true);

        // Disable last check-in and check-out.
//        setEnabledLastCheckInOut(true);

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
        if(posApi!=null){
            posApi.closeDev();
        }
        PowerUtils.power("0");
    }
}