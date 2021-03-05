package net.ideabreed.ibparking.view.checkout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.posapi.PosApi;
import android.posapi.PrintQueue;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.database.DatabaseHelper;
import net.ideabreed.ibparking.model.Bus;
import net.ideabreed.ibparking.model.Passenger;
import net.ideabreed.ibparking.model.Rates;
import net.ideabreed.ibparking.model.ReceiptItem;
import net.ideabreed.ibparking.model.Station;
import net.ideabreed.ibparking.model.Ticket;
import net.ideabreed.ibparking.presenter.CheckoutService;
import net.ideabreed.ibparking.presenter.adapter.CheckOutReceiptListAdapter;
import net.ideabreed.ibparking.presenter.helper.BitmapTools;
import net.ideabreed.ibparking.utils.UploadData;
import net.ideabreed.ibparking.view.checkin.SelectStationRecyclerViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, CheckoutService.View {

    private LinearLayout checkoutBackOption;
    private Button checkoutButton;
    private ListView checkOutReceiptListView;
    private SelectStationRecyclerViewAdapter stationAdapter;
    private CheckOutReceiptListAdapter receiptListAdapter;
    private Intent checkoutIntent;

    String checkInDate = "";
    String passengerCode = "";
    String checkInStationCode = "";
    String userId = "";
    String busCode = "";
    String checkInTime = "";
    String checkOutTime = "";
    private int concentration = 60;


    private UploadData uploadData;

    private Ticket checkOutTicket;

    private boolean isLost = false;
    String lost = "0";

    private PosApi posApi;
    private PrintQueue printQueue;

    String scannecText = "";

    private Passenger checkoutPassenger;
    private Station endStation;
    private Station startStation;
    private Bus currentBus;
    private int fareAmount;
    private int totalFareAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        uploadData = new UploadData();

        initPos();
        getScannedText();
        getDateTime();
        getStationIntent();
        initView();


    }

    private void getStationIntent() {
        checkoutIntent = getIntent();
        Bundle bundle = checkoutIntent.getExtras();
        endStation = (Station) bundle.getSerializable("checkoutStation");

    }


    private void initPos() {
        posApi = PosApi.getInstance(this);
        printQueue = new PrintQueue(this, posApi);
        printQueue.init();

        printQueue.setOnPrintListener(new PrintQueue.OnPrintListener() {
            @Override
            public void onFailed(int state) {
                switch (state) {
                    case PosApi.ERR_POS_PRINT_NO_PAPER:
                        break;
                    case PosApi.ERR_POS_PRINT_FAILED:
                        break;
                    case PosApi.ERR_POS_PRINT_VOLTAGE_LOW:
                        break;
                    case PosApi.ERR_POS_PRINT_VOLTAGE_HIGH:
                        break;
                }
            }

            @Override
            public void onFinish() {
                //    saveCounter(checkInTime, counter);
                updateToDb();
                finish();
            }

            @Override
            public void onGetState(int state) {

            }

            @Override
            public void onPrinterSetting(int state) {

            }
        });
    }

    private void updateToDb() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Ticket updatedTicket = new Ticket(endStation, checkOutTime, totalFareAmount, scannecText);
        boolean isUpdate = databaseHelper.updateTicket(updatedTicket);

        if (isUpdate) {
//            Intent homeIntent = new Intent(this, HomeActivity.class);
//            startActivity(homeIntent);
            finish();
        }

    }

    private void getScannedText() {

        Intent intent = getIntent();
        scannecText = intent.getStringExtra("scannedText");

        isLost = scannecText.equals("lost");
        if (isLost) {
            lost = "1";
        } else {
            lost = "0";
            if (scannecText.contains("-")) {
                String[] strArray = String.valueOf(scannecText).split("-");
                Log.d("Data", strArray.toString());
                checkInDate = strArray[0];
                userId = strArray[1];
                busCode = strArray[2];
                checkInStationCode = strArray[3];
                passengerCode = strArray[4];
                checkInTime = strArray[5];

//                if (!checkDates(checkInDate)) {
//                    Toast.makeText(this, "Ticket has been expired", Toast.LENGTH_SHORT).show();
//                    finish();
//                    return;
//                }
                Log.d("upload", uploadData.toString());
                for (Passenger passenger : uploadData.allPassenger()) {
                    if (passenger.getCode().equals(passengerCode)) {
                        checkoutPassenger = passenger;
                        break;
                    }
                }

                for (Bus bus : uploadData.allBuses()) {
                    if (bus.getBusCode().equals(busCode)) {
                        currentBus = bus;
                    }
                }

                for (Station station : uploadData.allStations()) {
                    if (station.getStationCode().equals(checkInStationCode)) {
                        startStation = station;
                        break;
                    }
                }

                checkOutTicket = new Ticket(checkoutPassenger, startStation, endStation, checkInTime, checkOutTime, null, fareAmount);
            }
        }

    }

    private boolean checkDates(String previousDate) {
        boolean result = false;

        try {
            Date currentDate = new Date();
            DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentFullDate = fullDateFormat.format(currentDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date prev = simpleDateFormat.parse(previousDate);
            Date now = simpleDateFormat.parse(currentFullDate);

            if (prev.equals(now)) {
                result = true;
            } else {
                result = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private void initView() {


        checkoutBackOption = findViewById(R.id.checkoutStationBackLL);
        checkoutButton = findViewById(R.id.checkOutBtn);
        checkOutReceiptListView = findViewById(R.id.checkOutReceiptLV);


        setReceiptAdapter();

        checkoutButton.setOnClickListener(this);
        checkoutBackOption.setOnClickListener(this);
    }

    private void setReceiptAdapter() {
        for (Rates rate : uploadData.allRates()) {
            if (rate.getStationStart() == startStation.getStationId() && rate.getStationEnd() == endStation.getStationId()) {
                fareAmount = rate.getRate();
                break;
            }
        }
        getTotalFare(checkoutPassenger);
        ArrayList<ReceiptItem> receiptItems = new ArrayList<>();
        receiptItems.add(new ReceiptItem("Bus Number", currentBus.getBusNumber()));
        receiptItems.add(new ReceiptItem("Start Station", startStation.getStationName()));
        receiptItems.add(new ReceiptItem("Check In At", checkInTime));
        receiptItems.add(new ReceiptItem("End Station", endStation.getStationName()));
        receiptItems.add(new ReceiptItem("Check Out At", checkOutTime));
        receiptItems.add(new ReceiptItem("Total Fare", "Rs. " + String.valueOf(totalFareAmount) + "/-"));
        receiptItems.add(new ReceiptItem("Date", checkInDate));
        receiptItems.add(new ReceiptItem("Type", checkoutPassenger.getType()));
        receiptListAdapter = new CheckOutReceiptListAdapter(this, receiptItems);

        checkOutReceiptListView.setAdapter(receiptListAdapter);

    }

    private void getTotalFare(Passenger passenger) {
        switch (passenger.getType()) {
            case "Normal":
                totalFareAmount = fareAmount;
                break;
            case "Elderly":
                double discountAmount = fareAmount * passenger.getDiscount();
                totalFareAmount = fareAmount - (int) discountAmount;
                break;
            case "Student":
                double studentDiscount = passenger.getDiscount() / 100;
                totalFareAmount = fareAmount - (int) Math.ceil(fareAmount * studentDiscount);
                break;
            default:
                totalFareAmount = fareAmount;

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkOutBtn:
                handleCheckout();
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
            sb.append("Kipu Bus Fare");
            sb.append("\n");
            sb.append("Bus Number: " + currentBus.getBusNumber());
            sb.append("\n");
            sb.append("Start Station: " + startStation.getStationName());
            sb.append("\n");
            sb.append("Check In At: " + checkOutTicket.getCheckInTime());
            sb.append("\n");
            sb.append("End Station: " + endStation.getStationName());
            sb.append("\n");
            sb.append("Check Out At: " + checkOutTime);
            sb.append("\n");
            sb.append("Total Fare: " + "Rs. " + totalFareAmount + "/-");
            sb.append("\n");
            sb.append("Date: " + checkInDate);
            sb.append("\n");
            sb.append("Type: " + checkoutPassenger.getType());
            sb.append("\n\n");

            String paymentQrString = "000201010212153137910524005204462103043357:273626500011fonepay.com01102103043357020627369206074457392520412345303524540515.005802NP5916N Cloud Pvt  Ltd6005Kaski622407062736920210985100793763047f0f";

            StringBuilder qrSb = new StringBuilder();
            qrSb.append(paymentQrString);

            Bitmap qrBitmap = textToImage(qrSb.toString(), 200, 200);
            byte[] printData = BitmapTools.bitmap2PrinterBytes(qrBitmap);

            printQueue.addText(60, sb.toString().getBytes("GBK"));
            printQueue.addText(concentration, "\nScan this QR Code For Payment \n".getBytes("GBK"));
            printQueue.addBmp(concentration, 80, 200, 200, printData);
            printQueue.addText(concentration, "Powered by Idea Breed Technology \n\n\n".getBytes("GBK"));

//            sb.append("");
            sb.append("\n\n\n\n\n");


            printQueue.printStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDateTime() {

        Date current = new Date();
        DateFormat idFormat = new SimpleDateFormat("yyyyMMdd");
        DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = localDateFormat.format(new Date()).replaceAll(":", "");
        checkOutTime = time;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (printQueue != null) {
            printQueue.close();
        }
    }
    private Bitmap textToImage(String text, int width, int height) throws WriterException, NullPointerException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.DATA_MATRIX.QR_CODE,
                    width, height, null);
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        int colorWhite = 0xFFFFFFFF;
        int colorBlack = 0xFF000000;

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colorBlack : colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, width, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}