package com.gokisoft.example.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.gokisoft.example.ServicesActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFileAsync extends AsyncTask<String, Integer, String> {
    Activity mActivity;

    String fileUrl = "";
    boolean cancel = false;
    int currentPercent = 0;
    boolean pause = false;

    public DownloadFileAsync(Activity activity) {
        mActivity = activity;
    }

    public boolean isPause() {
        return pause;
    }

    public void switchPause() {
        this.pause = !this.pause;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    protected void onPreExecute() {
        Log.d(DownloadFileAsync.class.getName(), "onPreExecute");
        cancel = false;
        currentPercent = 0;
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(DownloadFileAsync.class.getName(), "doInBackground");
        fileUrl = strings[0];

        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(DownloadFile.class.getName(), "Connection failed");
                return null;
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            output = mActivity.openFileOutput("vidu1.mp4", Activity.MODE_PRIVATE);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if(isCancel()) {
                    break;
                }

                if(isPause()) {
                    synchronized (mActivity) {
                        Log.d(DownloadFile.class.getName(), "Pause");
                        try {
                            mActivity.wait();
                        } catch (Exception e) {}
                        Log.d(DownloadFile.class.getName(), "Re-Start");
                    }
                }

                total += count;
                // publishing the progress....
                if (fileLength > 0) {
                    //publishProgress((int) (total * 100 / fileLength));
                    int percent = (int) (total * 100 / fileLength);
                    publishProgress(percent);
                }
                output.write(data, 0, count);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException ignored) {
            }

            if (connection != null) {
                connection.disconnect();
            }
        }

        Log.d(DownloadFile.class.getName(), "Stop download ...");

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d(DownloadFileAsync.class.getName(), "onProgressUpdate");

        int percent = values[0];
        if(percent != currentPercent) {
            currentPercent = percent;
            Log.d(DownloadFile.class.getName(), "percent : " + percent);
            Intent i = new Intent();
            i.setAction(ServicesActivity.ACTION_DOWNLOAD_FILE);
            i.putExtra("percent", percent);
            mActivity.sendBroadcast(i);
        }

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(DownloadFileAsync.class.getName(), "onPostExecute");
        mActivity = null;
        currentPercent = 0;
        cancel = false;
        fileUrl = null;

        super.onPostExecute(s);
    }
}
