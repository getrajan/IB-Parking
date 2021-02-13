package net.ideabreed.ibparking.view.checkin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.posapi.PosApi;
import android.posapi.PrintQueue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.database.DatabaseHelper;
import net.ideabreed.ibparking.model.Bus;
import net.ideabreed.ibparking.model.Passenger;
import net.ideabreed.ibparking.model.Station;
import net.ideabreed.ibparking.model.Ticket;
import net.ideabreed.ibparking.model.User;
import net.ideabreed.ibparking.presenter.CheckInService;
import net.ideabreed.ibparking.presenter.helper.BitmapTools;
import net.ideabreed.ibparking.utils.UploadData;
import net.ideabreed.ibparking.view.home.HomeActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener, CheckInService.View {
    private TextView studentTypeTextView;
    private ImageView studentTypeImageView;
    private RecyclerView stationNameRecyclerView;
    private LinearLayout backOption;
    private SelectStationRecyclerViewAdapter selectStationGridViewAdapter;
    private PassengerRecyclerAdapter passengerAdapter;
    private Button checkInButton;
    private RecyclerView passengerTyperRecylerView;
    Passenger passenger;

    Intent qrIntent;
    Station stationData;
    Bitmap qrBitmap;

    String checkInTime, receiptId;

    PosApi posApi;
    private PrintQueue printQueue = null;
    private int concentration = 60;

    private Ticket ticket;
    User conductor;
    Ticket checkInData;
    private UploadData uploadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_checkin);
        uploadData = new UploadData();
        initPos();

        getDateTime();
        initView();

        getStationIntent();

    }

    private void initPos() {
        posApi = PosApi.getInstance(this);
//        posApi.initDeviceEx("/dev/ttyMT2");
//        print Ticket instance
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
                saveTicketToDb();
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

    private void saveTicketToDb() {

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        User conductor = new User("test", "test123", false, 1);
        Ticket postTicket = new Ticket(checkInData.getType(), checkInData.getCheckInStation(), checkInTime, conductor, receiptId);
        boolean isInsert = databaseHelper.addTicket(postTicket);
        if (isInsert) {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        }
    }

    private void getReceiptId() {
        Date current = new Date();
        DateFormat idFormat = new SimpleDateFormat("yyyyMMdd");

        conductor = new User("test", "test123", false, 1);
        Bus bus = new Bus("BA 2 KHA 222", "01", 1);


        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = localDateFormat.format(new Date()).replaceAll(":", "");


        receiptId = idFormat.format(current) + "-" + conductor.getUserId() + "-" + bus.getBusCode() + "-" + stationData.getStationCode() + "-" + checkInData.getType().getCode() + "-" + time;

    }

    private void getStationIntent() {

        qrIntent = getIntent();
        Bundle bundle = qrIntent.getExtras();
        stationData = (Station) bundle.getSerializable("stationIntent");
    }

    private void initView() {
        checkInButton = findViewById(R.id.checkInBtn);
        passengerTyperRecylerView = findViewById(R.id.passengerTypeRV);
        backOption = findViewById(R.id.stationCheckInBackLL);

        checkInButton.setOnClickListener(this);
        backOption.setOnClickListener(this);

        setPassengerAdapter();
    }

    public void setPassengerAdapter() {

        passengerAdapter = new PassengerRecyclerAdapter(uploadData.allPassenger(), this);

        passengerTyperRecylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        passengerTyperRecylerView.setAdapter(passengerAdapter);
//        passengerTyperRecylerView.setLayoutFrozen(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.checkInBtn:
                if (passengerAdapter.getSelected() != null) {
                    passenger = new Passenger(passengerAdapter.getSelected().getType(), passengerAdapter.getSelected().getCode(), passengerAdapter.getSelected().getDiscount());
                    Station station = new Station(stationData.getStationName(), stationData.getStationCode());
                    Toast.makeText(this, "checked: " + passengerAdapter.getSelected().getType(), Toast.LENGTH_SHORT).show();
                    checkInData = new Ticket(passenger, station);
                    getReceiptId();
                    handleCheckIn(checkInData);

                } else {
                    onError("Please select passenger type");
                }
                break;
            case R.id.stationCheckInBackLL:
                finish();
                break;
        }

    }

    private void getDateTime() {

        Date current = new Date();
        DateFormat idFormat = new SimpleDateFormat("yyyyMMdd");
        DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        checkInTime = fullDateFormat.format(current);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = localDateFormat.format(new Date()).replaceAll(":", "");


    }

    private void handleCheckIn(Ticket checkInData) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("...................");
            sb.append("\n");
            sb.append("KIPU BUS TICKET");
            sb.append("\n");
            sb.append("...................");
            sb.append("\n");
            sb.append("Starting Station: " + checkInData.getCheckInStation().getStationName());
            sb.append("\n");
            sb.append("Type: " + checkInData.getType().getType());
            sb.append("\n");
            sb.append("Receipt Id: " + receiptId);
            sb.append("\n");
            sb.append("Check In At: " + checkInTime);
            printQueue.addText(concentration, sb.toString().getBytes("GBK"));

//            String builder for QR
            StringBuilder qrSb = new StringBuilder();
            qrSb.append(receiptId);

            Bitmap qrBitmap = textToImage(qrSb.toString(), 200, 200);
            byte[] printData = BitmapTools.bitmap2PrinterBytes(qrBitmap);
            printQueue.addBmp(concentration, 80, 200, 200, printData);
            printQueue.addText(concentration, "For your own convenience, please don't loose this slip.\nIn case of lost, full charges will apply.\n\n\n\n\n".getBytes("GBK"));

            printQueue.printStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Intent intent = new Intent(this, QrGenerateActivity.class);
        intent.putExtra("checkInTicketData", ticket);
        startActivity(intent);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (printQueue != null) {
            printQueue.close();
        }
    }

}

