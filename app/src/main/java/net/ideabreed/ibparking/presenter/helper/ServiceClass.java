package net.ideabreed.ibparking.presenter.helper; /*
 * Created by Rajan Karki on 2/10/21
 * Copyright @2021
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class ServiceClass extends IntentService {

    public ServiceClass() {
        super("Timer Service");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        int time = intent.getIntExtra("time", 20);
        for (int i = 1; i <= time; i++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("message", "completed");
        receiver.send(1234, bundle);

    }

} 