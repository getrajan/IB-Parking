package net.ideabreed.ibparking.view.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.posapi.PosApi;
import android.posapi.PrintQueue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.model.Station;
import net.ideabreed.ibparking.presenter.CheckoutService;
import net.ideabreed.ibparking.presenter.helper.PowerUtils;
import net.ideabreed.ibparking.view.checkin.SelectStationRecyclerViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, CheckoutService.View {

    private RecyclerView stationRecylerView;
    private LinearLayout checkoutBackOption;
    private MaterialButton checkoutButton;
    private SelectStationRecyclerViewAdapter stationAdapter;

    String receiptId = "";
    String passengerType = "";
    String checkInStation = "";
    String checkOutStation = "";

    String checkInTime = "";
    String checkOutTime  = "";

    private boolean isLost = false;
    String lost = "0";

    private PosApi posApi;
    private PrintQueue printQueue;

    String scannecText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initPos();
        getDateTime();
        initView();
        getScannedText();

    }



    private void initPos() {

        PowerUtils.power("1");
        posApi = PosApi.getInstance(this);
        posApi.initDeviceEx("/dev/ttyMT2");
        printQueue = new PrintQueue(this,posApi);
        printQueue.init();
    }

    private void getScannedText() {

        Intent intent = getIntent();
        scannecText = intent.getStringExtra("scannedText");

        isLost = scannecText.equals("lost");
        if(isLost){
            lost = "1";
        }else {
            lost = "0";
            if(scannecText.contains("#")){
                String[] strArray = String.valueOf(scannecText).split("#");

                if(!checkDates(strArray[3])){
                    Toast.makeText(this, "Ticket has been expired", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                receiptId = strArray[0];
                checkInStation = strArray[2];
                passengerType = strArray[1];
                checkInTime = strArray[3];

            }
        }

    }

    private boolean checkDates(String previousDate) {
        boolean result = false;

        try{

            Date currentDate = new Date();
            DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentFullDate = fullDateFormat.format(currentDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date prev = simpleDateFormat.parse(previousDate);
            Date now = simpleDateFormat.parse(currentFullDate);

            if(prev.equals(now)){
                result = true;
            }else {
                result = false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    private void initView() {

        stationRecylerView = findViewById(R.id.checkOutStationRV);
        checkoutButton = findViewById(R.id.checkoutBtn);
        checkoutBackOption = findViewById(R.id.checkoutStationBackLL);

        setStationAdapter();

        checkoutButton.setOnClickListener(this);
        checkoutBackOption.setOnClickListener(this);
    }

    private void setStationAdapter() {
        ArrayList<Station> stationNameList = new ArrayList<>();
        stationNameList.add(new Station("Koteshwor", "AB",false,1));
        stationNameList.add(new Station("Balkhu", "AB",false,1));
        stationNameList.add(new Station("Koteshwor", "AB",false,1));
        stationNameList.add(new Station("Koteshwor", "AB",false,1));
        stationNameList.add(new Station("Koteshwor", "AB",false,1));



        stationAdapter = new SelectStationRecyclerViewAdapter(stationNameList, this);
        stationRecylerView.setAdapter(stationAdapter);
        stationRecylerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkoutBtn:
                if(stationAdapter.getSelected()!=null){
                    checkOutStation = stationAdapter.getSelected().getStationName();
                    handleCheckout();
                }else{
                    onError("Plase select station");
                }
                break;
            case R.id.checkoutStationBackLL:
                finish();
                break;
        }
    }

    private void handleCheckout() {

        isLost = scannecText.equals("lost");

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Idea Breed Bus Ticketing Sewa");
            sb.append("\n");
            sb.append("Pan No: 609242250");
            sb.append("\n");
            sb.append("Ticket Invoice");
            sb.append("\n");
            sb.append("Type: "+passengerType);
            sb.append("\n");
            sb.append("Check In Station: "+checkInStation);
            sb.append("\n");
            sb.append("Check Out Station: "+checkOutStation);
            sb.append("\n");
            sb.append("Total Price: "+"Rs. 15 /-");
            sb.append("\n");
            sb.append("Check In Time: "+checkInTime);
            sb.append("\n");
            sb.append("Check Out Time: "+checkOutTime);
            sb.append("\n\n\n\n\n");

            printQueue.addText(60,sb.toString().getBytes("GBK"));

            printQueue.printStart();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getDateTime() {

        Date current = new Date();
        DateFormat idFormat = new SimpleDateFormat("yyyyMMdd");
        DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        checkOutTime = fullDateFormat.format(current);

    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onNavigate() {

    }
}