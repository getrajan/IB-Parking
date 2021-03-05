package net.ideabreed.ibparking.view.log;

import android.os.Bundle;
import android.posapi.PosApi;
import android.posapi.PrintQueue;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.database.DatabaseHelper;
import net.ideabreed.ibparking.model.Ticket;
import net.ideabreed.ibparking.presenter.adapter.TodayLogRecyclerAdapter;

import java.util.ArrayList;

public class TodayLogActivity extends AppCompatActivity {

    private RecyclerView todayLogRecyclerView;
    private TodayLogRecyclerAdapter todayLogAdapter;
    private Button todayLogPrintAllButton;
    ArrayList<Ticket> tickets;
    PosApi posApi;
    private PrintQueue printQueue = null;
    private int concentration = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_log);

        initPos();
        initView();
    }

    private void initPos() {
        posApi = PosApi.getInstance(this);
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

    private void initView() {
        todayLogRecyclerView = findViewById(R.id.todayLogRV);
        todayLogPrintAllButton = findViewById(R.id.todayLogPrintBtn);

//        Get tickets from db
        tickets = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        tickets = dbHelper.getAllTickets();

        todayLogAdapter = new TodayLogRecyclerAdapter(tickets, this);
        todayLogRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        todayLogRecyclerView.setAdapter(todayLogAdapter);

        todayLogPrintAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    StringBuilder sbr = new StringBuilder();
                    sbr.append("Today's Log");
                    sbr.append("\n");
                    sbr.append("....................");
                    sbr.append("\n");
                    printQueue.addText(concentration, sbr.toString().getBytes("GBK"));
                    for (Ticket ticket : tickets) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Start Station: " + ticket.getCheckInStation().getStationName());
                        sb.append("\n");
                        sb.append("End Station: " + ticket.getCheckOutStation().getStationName());
                        sb.append("\n");
                        sb.append("Fare Amont: " + ticket.getFare());
                        sb.append("\n");
                        sb.append("Type: " + ticket.getType().getType());
                        sb.append("\n");
                        sb.append("....................");
                        printQueue.addText(concentration, sb.toString().getBytes("GBK"));
                    }

                    printQueue.addText(concentration, "\n\n\n\n".getBytes("GBK"));
                    printQueue.printStart();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (printQueue != null) {
            printQueue.close();
        }
    }
}