package com.gokisoft.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener{
    public final static String ACTION_COUNTER = "ACTION_COUNTER";

    Button startServiceBtn, stopServiceBtn, downloadBtn, testBtn;
    TextView msgView, percentView;
    Intent serviceIntent;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_COUNTER:
                    //Du lieu nhan dc o day -> Ko trung voi Main UI Thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String msg = intent.getStringExtra("counter");
                            Log.d(CounterService.class.getName(), "msg > " + msg);
                            msgView.setText(msg);
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        startServiceBtn = findViewById(R.id.as_start_btn);
        stopServiceBtn = findViewById(R.id.as_stop_btn);
        downloadBtn = findViewById(R.id.as_download_start_btn);
        testBtn = findViewById(R.id.as_test_btn);

        msgView = findViewById(R.id.as_msg);
        percentView = findViewById(R.id.as_percent);

        startServiceBtn.setOnClickListener(this);
        stopServiceBtn.setOnClickListener(this);
        downloadBtn.setOnClickListener(this);
        testBtn.setOnClickListener(this);

//        msgView.setText("1...");
//        msgView.setText("2...");

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_COUNTER);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(startServiceBtn)) {
            if(serviceIntent == null) {
                serviceIntent = new Intent(this, CounterService.class);
                startService(serviceIntent);
            }
//            msgView.setText("3...");

//            startServiceBtn.setVisibility(View.GONE);
//            stopServiceBtn.setVisibility(View.VISIBLE);
        } else if(view.equals(stopServiceBtn)) {
//            msgView.setText("4...");
            if(serviceIntent != null) {
                stopService(serviceIntent);
                serviceIntent = null;
            }

//            startServiceBtn.setVisibility(View.VISIBLE);
//            stopServiceBtn.setVisibility(View.GONE);
        } else if(view.equals(downloadBtn)) {

        } else if(view.equals(testBtn)) {

        }
    }

    @Override
    protected void onDestroy() {
        try {
            if(mReceiver != null) {
                unregisterReceiver(mReceiver);
                mReceiver = null;
            }
        } catch(Exception e) {}

        super.onDestroy();
    }
}