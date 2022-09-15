package com.gokisoft.example;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class CounterService extends Service {
    Handler handler;
    int counter = 0;

    public CounterService() {
    }

    @Override
    public void onCreate() {
        Log.d(CounterService.class.getName(), "onCreate");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(CounterService.class.getName(), "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(CounterService.class.getName(), "onStartCommand");
        handler = new Handler();
        startCounter();

        return super.onStartCommand(intent, flags, startId);
    }

    private void startCounter() {
        if(handler == null) return;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent();
                i.setAction(ServicesActivity.ACTION_COUNTER);
                i.putExtra("counter", ++counter + "");

                Log.d(CounterService.class.getName(), "Counter: " + counter);

                sendBroadcast(i);

                startCounter();
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        Log.d(CounterService.class.getName(), "onDestroy");
        handler = null;

        super.onDestroy();
    }
}