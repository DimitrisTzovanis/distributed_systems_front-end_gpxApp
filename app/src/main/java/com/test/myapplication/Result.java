package com.test.myapplication;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;


public class Result extends Activity{


    TextView label1;
    TextView label2;
    TextView label3;
    TextView label4;
    TextView label5;
    TextView label6;
    TextView label7;
    TextView label8;

    TextView label9;

    TextView label10;

    TextView label11;

    TextView labelsegment;

    private ListView listView;



    private static final String CHANNEL_ID = "my_channel_id";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        label1 = (TextView) findViewById(R.id.label1);
        label2= (TextView) findViewById(R.id.label2);
        label3 = (TextView) findViewById(R.id.label3);
        label4 = (TextView) findViewById(R.id.label4);
        label5 = (TextView) findViewById(R.id.label5);

        String result1 = getIntent().getStringExtra("result1");
        String result2 = getIntent().getStringExtra("result2");
        String result3 = getIntent().getStringExtra("result3");
        String result4 = getIntent().getStringExtra("result4");
        String result5 = getIntent().getStringExtra("result5");

        label1.setText(result1);
        label2.setText("Distance: " + result2 + " meters");
        label3.setText("Average Speed: " + result4 + " km/h");
        label4.setText("Elevation: "+ result3 + " meters");
        label5.setText("Exercise Time: " + result5 + " seconds" );

        label6 = (TextView) findViewById(R.id.label6);
        label7 = (TextView) findViewById(R.id.label7);
        label8 = (TextView) findViewById(R.id.label8);

        label9 = (TextView) findViewById(R.id.label9);
        label10 = (TextView) findViewById(R.id.label10);
        label11 = (TextView) findViewById(R.id.label11);


        String result6 = getIntent().getStringExtra("result6");
        String result7 = getIntent().getStringExtra("result8");
        String result8 = getIntent().getStringExtra("result9");


        label6.setText("Total Distance: " + result6 + " meters");
        label7.setText("Total Elevation: "+ result7 + " meters");
        label8.setText("Total Exercise Time: " + result8 + " seconds" );

        String result9 = getIntent().getStringExtra("result10");
        String result10 = getIntent().getStringExtra("result11");
        String result11 = getIntent().getStringExtra("result12");

        ProgressBar progressBar1 = findViewById(R.id.progressBar1);

        ProgressBar progressBar2 = findViewById(R.id.progressBar2);

        ProgressBar progressBar4 = findViewById(R.id.progressBar4);
        progressBar1.setMax(100);
        progressBar2.setMax(100);
        progressBar4.setMax(100);
        int colorRed = getResources().getColor(R.color.red);

        if(result9!=null){
            if(Double.parseDouble( result9)<0.0){
                Double result9int = Double.parseDouble(result9);
                label9.setText("You have walked: " + Math.abs(result9int) + " % less than Average");
                progressBar1.setProgress((int) Math.round(Math.abs(result9int)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ColorStateList colorStateList = ColorStateList.valueOf(colorRed);
                    progressBar1.setProgressTintList(colorStateList);
                } else {
                    PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
                    PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(colorRed, mode);
                    progressBar1.getProgressDrawable().setColorFilter(colorFilter);
                }

            }
            else{
                label9.setText("You have walked: " + result9 + " % more than Average");
                progressBar1.setProgress((int) Math.round(Double.parseDouble(result9)));

            }

            if(Double.parseDouble(result10)<0.0){
                Double result10int = Double.parseDouble(result10);
                label10.setText("Your Time: " + Math.abs(result10int) + " % less than Average");
                progressBar2.setProgress((int) Math.round(Math.abs(result10int)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ColorStateList colorStateList = ColorStateList.valueOf(colorRed);
                    progressBar2.setProgressTintList(colorStateList);
                } else {
                    PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
                    PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(colorRed, mode);
                    progressBar2.getProgressDrawable().setColorFilter(colorFilter);
                }

            }
            else{
                label10.setText("You Time: " + result10 + " % more than Average");
                progressBar2.setProgress((int) Math.round(Double.parseDouble(result10)));

            }

            if(Double.parseDouble(result11)<0.0){
                Double result11int = Double.parseDouble(result11);
                label11.setText("You Elevation: " + Math.abs(result11int) + " % less than Average");
                progressBar4.setProgress((int) Math.round(Math.abs(result11int)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ColorStateList colorStateList = ColorStateList.valueOf(colorRed);
                    progressBar4.setProgressTintList(colorStateList);
                } else {
                    PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
                    PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(colorRed, mode);
                    progressBar4.getProgressDrawable().setColorFilter(colorFilter);
                }

            }
            else{
                label11.setText("You Elevation: " + result11 + " % more than Average");
                progressBar4.setProgress((int) Math.round(Double.parseDouble(result11)));

            }
        }

        showNotification(getApplicationContext(), "GPS APPLICATION", "Your results are back from the server!");

        listView = findViewById(R.id.listview);

        labelsegment = (TextView) findViewById(R.id.labelsegment);

        segmentTimes segmentTimes = com.test.myapplication.segmentTimes.getInstance();

        String[] data = new String[30];
        int i=0;
        if(segmentTimes.times.size()>0){
            labelsegment.setText("Results for segment " +  segmentTimes.name);
            for(String x: segmentTimes.times) {
                data[i] = x;
                i++;
            }
        }


        CustomAdapter adapter = new CustomAdapter(this, data);
        listView.setAdapter(adapter);



    }

    private void showNotification(Context context, String title, String message) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "My Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        Notification.Builder builder = null; // Replace with your notification icon
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.photo3);
        }

        // Display the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationManager.notify(0, builder.build());
        }
    }
}
