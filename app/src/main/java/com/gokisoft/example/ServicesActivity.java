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
import android.widget.Toast;

import com.gokisoft.example.utils.DownloadFile;
import com.gokisoft.example.utils.DownloadFileAsync;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener{
    public final static String ACTION_COUNTER = "ACTION_COUNTER";
    public final static String ACTION_DOWNLOAD_FILE = "ACTION_DOWNLOAD_FILE";

    Button startServiceBtn, stopServiceBtn, downloadBtn, testBtn;
    Button stopDownloadBtn, pauseDownloadBtn;
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
                case ACTION_DOWNLOAD_FILE:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int percent = intent.getIntExtra("percent", 0);
                            percentView.setText(percent + "");
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

        stopDownloadBtn = findViewById(R.id.as_download_stop_btn);
        pauseDownloadBtn = findViewById(R.id.as_download_pause_btn);

        stopDownloadBtn.setOnClickListener(this);
        pauseDownloadBtn.setOnClickListener(this);

//        msgView.setText("1...");
//        msgView.setText("2...");

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_COUNTER);
        filter.addAction(ACTION_DOWNLOAD_FILE);
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

            startServiceBtn.setVisibility(View.GONE);
            stopServiceBtn.setVisibility(View.VISIBLE);
        } else if(view.equals(stopServiceBtn)) {
//            msgView.setText("4...");
            if(serviceIntent != null) {
                stopService(serviceIntent);
                serviceIntent = null;
            }

            startServiceBtn.setVisibility(View.VISIBLE);
            stopServiceBtn.setVisibility(View.GONE);
        } else if(view.equals(downloadBtn)) {
//            if(downloadFile == null) {
//                downloadFile = new DownloadFile(this, "https://rr8---sn-quxapm-3c2e.googlevideo.com/videoplayback?expire=1663439242&ei=Kr0lY_mCC5-CvdIPh-6WmAI&ip=2001%3A41d0%3Ad%3A260c%3A%3A&id=o-AAMdvqT48Y2xvfg5f6tM10wKfo5ArTTa8dDJLswL5z6X&itag=22&source=youtube&requiressl=yes&mh=iB&mm=31%2C29&mn=sn-quxapm-3c2e%2Csn-25ge7nse&ms=au%2Crdu&mv=m&mvi=8&pl=48&initcwndbps=341250&spc=yR2vpwcFcUkPvx0zEQECWvzK8e7kn_o&vprv=1&svpuc=1&mime=video%2Fmp4&cnr=14&ratebypass=yes&dur=1105.130&lmt=1663360977663108&mt=1663417265&fvip=5&fexp=24001373%2C24007246&c=ANDROID&rbqsm=fr&txp=3318224&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cspc%2Cvprv%2Csvpuc%2Cmime%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRgIhAKgEMq9jbgRVFglitRBaRyon6kFWa1KJw5JH1PZKSGOWAiEAhf88PbJRRIQ8mjBn7aYuEE9KZ-sk0yDXY5i5VGndIGE%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIhAIZQBazFKofJjiBWO_KMrRDosmM5z2rIDuR6bOSh0L5aAiAsrwJ7_3x6e6lDxGesIy9dKXJjChkfZb_BJhFBDTstlQ%3D%3D&title=Quanglinhvlogs++Thanh+To%C3%A1n+L%C6%B0%C6%A1ng+Cho+%C4%90%E1%BB%99i+C%C3%B4ng+Tr%C3%ACnh+Tr%C6%B0%E1%BB%9Dng+H%E1%BB%8Dc+v%C3%A0+B%E1%BB%95+Sung+Nh%E1%BB%AFng+Ch%C3%BA+D%E1%BB%85+Ch%E1%BA%A5t+L%C6%B0%E1%BB%A3ng");
//                downloadFile.start();
//            } else {
//                Toast.makeText(this, "Dang tai file", Toast.LENGTH_SHORT).show();
//            }
            if(downloadFileAsync == null) {
                downloadFileAsync = new DownloadFileAsync(this);
                downloadFileAsync.execute("https://rr8---sn-quxapm-3c2e.googlevideo.com/videoplayback?expire=1663439242&ei=Kr0lY_mCC5-CvdIPh-6WmAI&ip=2001%3A41d0%3Ad%3A260c%3A%3A&id=o-AAMdvqT48Y2xvfg5f6tM10wKfo5ArTTa8dDJLswL5z6X&itag=22&source=youtube&requiressl=yes&mh=iB&mm=31%2C29&mn=sn-quxapm-3c2e%2Csn-25ge7nse&ms=au%2Crdu&mv=m&mvi=8&pl=48&initcwndbps=341250&spc=yR2vpwcFcUkPvx0zEQECWvzK8e7kn_o&vprv=1&svpuc=1&mime=video%2Fmp4&cnr=14&ratebypass=yes&dur=1105.130&lmt=1663360977663108&mt=1663417265&fvip=5&fexp=24001373%2C24007246&c=ANDROID&rbqsm=fr&txp=3318224&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cspc%2Cvprv%2Csvpuc%2Cmime%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRgIhAKgEMq9jbgRVFglitRBaRyon6kFWa1KJw5JH1PZKSGOWAiEAhf88PbJRRIQ8mjBn7aYuEE9KZ-sk0yDXY5i5VGndIGE%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIhAIZQBazFKofJjiBWO_KMrRDosmM5z2rIDuR6bOSh0L5aAiAsrwJ7_3x6e6lDxGesIy9dKXJjChkfZb_BJhFBDTstlQ%3D%3D&title=Quanglinhvlogs++Thanh+To%C3%A1n+L%C6%B0%C6%A1ng+Cho+%C4%90%E1%BB%99i+C%C3%B4ng+Tr%C3%ACnh+Tr%C6%B0%E1%BB%9Dng+H%E1%BB%8Dc+v%C3%A0+B%E1%BB%95+Sung+Nh%E1%BB%AFng+Ch%C3%BA+D%E1%BB%85+Ch%E1%BA%A5t+L%C6%B0%E1%BB%A3ng");
            }
        } else if(view.equals(testBtn)) {
            Toast.makeText(this, "Show Message", Toast.LENGTH_SHORT).show();
        } else if(view.equals(stopDownloadBtn)) {
//            if(downloadFile != null) {
//                downloadFile.setCancel(true);
//                downloadFile = null;
//            }
            if(downloadFileAsync != null) {
                downloadFileAsync.setCancel(true);
                downloadFileAsync = null;
            }
        } else if(view.equals(pauseDownloadBtn)) {
//            if(downloadFile != null) {
//                downloadFile.switchPause();
//                if(downloadFile.isPause()) {
//                    pauseDownloadBtn.setText("Re-start Download File");
//                } else {
//                    pauseDownloadBtn.setText("Pause Download File");
//                    synchronized (this) {
//                        this.notifyAll();
//                    }
//                }
//            }

            if(downloadFileAsync != null) {
                downloadFileAsync.switchPause();
                if(downloadFileAsync.isPause()) {
                    pauseDownloadBtn.setText("Re-start Download File");
                } else {
                    pauseDownloadBtn.setText("Pause Download File");
                    synchronized (this) {
                        this.notifyAll();
                    }
                }
            }


        }
    }

    DownloadFile downloadFile = null;
    DownloadFileAsync downloadFileAsync = null;
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