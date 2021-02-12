package net.ideabreed.ibparking.view.checkin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.posapi.PosApi;
import android.posapi.PrintQueue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.model.Ticket;
import net.ideabreed.ibparking.presenter.helper.BitmapTools;
import net.ideabreed.ibparking.presenter.helper.PowerUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrGenerateActivity extends AppCompatActivity implements PrintQueue.OnPrintListener, View.OnClickListener {

    private ImageView qrImage;
    private Button checkInButton;
    private LinearLayout qrGenerateBackOption;
    Intent qrIntent;
    Ticket ticketData;
    Bitmap qrBitmap;

    String checkInTime, receiptId;

    PosApi posApi;
//    private PrintTicket printTicket;
    private PrintQueue printQueue =null;

    private int concentration = 60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generate);

        PowerUtils.power("1");
        posApi = PosApi.getInstance(this);
        posApi.initDeviceEx("/dev/ttyMT2");

//        print Ticket instance
//        posApi = PosApi.getInstance(this);
        printQueue = new PrintQueue(this, posApi);
        printQueue.init();

        getDateTime();

        initView();

        qrIntent = getIntent();
        Bundle bundle = qrIntent.getExtras();
        ticketData = (Ticket) bundle.getSerializable("checkInTicketData");
        String qrInput = "Type: " + ticketData.getType() +
                "\n" + "Check In Location: " + ticketData.getCheckInStation() +
                "\n" + "Date: " + checkInTime +
                "\n" + "Receipt Id: " + receiptId;
        QRGEncoder qrgEncoder = new QRGEncoder(qrInput, null, QRGContents.Type.TEXT, 2000);

        try {
            qrBitmap = qrgEncoder.getBitmap();
            qrImage.setImageBitmap(qrBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getDateTime() {

        Date current = new Date();
        DateFormat idFormat = new SimpleDateFormat("yyyyMMdd");
        DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        checkInTime = fullDateFormat.format(current);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = localDateFormat.format(new Date()).replaceAll(":", "");

        receiptId = idFormat.format(current) + time;

    }

    private void initView() {
        qrImage = findViewById(R.id.qrGenerateQrHolderIV);
        checkInButton = findViewById(R.id.qrGenerateCheckInBtn);
        qrGenerateBackOption = findViewById(R.id.qrGenerateBackLL);

        checkInButton.setOnClickListener(this);
        qrGenerateBackOption.setOnClickListener(this);

    }

    private void printSlip() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Idea Breed Bus Ticketing Sewa");
            sb.append("\n");
            sb.append("Bus Ticket Slip");
            sb.append("\n");
            sb.append("Type: " + ticketData.getType());
            sb.append("\n");
            sb.append("Check In Location : " + ticketData.getCheckInStation());
            sb.append("\n");
            sb.append("Receipt Id: " + receiptId);
            sb.append("\n");
            sb.append("Check In At: " + checkInTime);

            printQueue.addText(concentration, sb.toString().getBytes("GBK"));

//           String builder for QR
            StringBuilder qrSb = new StringBuilder();
            qrSb.append(receiptId);
            qrSb.append("#");
            qrSb.append(ticketData.getType());
            qrSb.append("#");
            qrSb.append(ticketData.getCheckInStation());
            qrSb.append("#");
            qrSb.append(checkInTime);

            Bitmap qrBitmap = textToImage(qrSb.toString(), 200, 200);
            byte[] printData = BitmapTools.bitmap2PrinterBytes(qrBitmap);
            printQueue.addBmp(concentration, 80, 200, 200, printData);

            printQueue.addText(concentration, "For your own convenience, please don't loose this slip.\nIn case of lost, full charges will apply.\n\n\n\n\n".getBytes("GBK"));

            printQueue.printStart();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void showTip(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage(msg)
                .setNegativeButton("CLOSE",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        }).show();
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

    @Override
    public void onFailed(int i) {

    }

    @Override
    public void onFinish() {

        setResult(-1);
        finish();
    }

    @Override
    public void onGetState(int i) {

    }

    @Override
    public void onPrinterSetting(int i) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.qrGenerateCheckInBtn:
                printSlip();
                break;
            case R.id.qrGenerateBackLL:
                finish();
                break;
        }
    }
}