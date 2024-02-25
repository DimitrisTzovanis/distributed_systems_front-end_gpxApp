package com.test.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class SendFile extends AppCompatActivity {

    Button btn;

    EditText input;

    TextView label;

    Handler handler;

    Message messagetosend;

    Spinner spinner;

    File file;

    String txt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);

        //set spinner
        spinner = findViewById(R.id.spinner);


        //get every single file from internal storage
        File internalStorageDir = getFilesDir();
        File[] files = internalStorageDir.listFiles();

        //save the files in an arraylist so they can be added to the spinner
        ArrayList<File> fileArrayList = new ArrayList<>();
        if (files != null) {
            for (File file2 : files) {
                fileArrayList.add(file2);
            }
        }

        //create the spinner (requires adapter)
        ArrayAdapter<File> adapter = new ArrayAdapter<File>(this,
                android.R.layout.simple_spinner_item, fileArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //meesage handler
        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {


                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                sendToResult(message);
                return true;
            }
        });

        btn = (Button) findViewById(R.id.button);
        input = (EditText) findViewById(R.id.input);
        label = (TextView) findViewById(R.id.label);

        //button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //diavazei kai elnxei to input file
                if(input.getText().length()>3){
                    txt = input.getText().toString();
                    file = new File(getFilesDir(), txt);
                }

                if (file.exists()) {
                        setContentView(R.layout.loading_screen);
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        Toast.makeText(SendFile.this,"Results acquired!!",Toast.LENGTH_LONG).show();
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    MyThread t1 = new MyThread(txt,handler,file);
                    t1.start();

                    //perimenw na teleiwsei to nima kai paw sto activity result
                    while (t1.isAlive() ) {
                        try {
                            // Sleep for a short duration
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Thread has finished.");
                }
                else{
                    Toast.makeText(SendFile.this,"no results found...",Toast.LENGTH_LONG).show();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                file = (File) parent.getSelectedItem();
                txt = file.getName();
                System.out.println(file.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


    }
    public void sendToResult(Message message){
        Intent myIntent = new Intent(SendFile.this, Result.class);
        //vazw mesa sto intent pou tha xekinisei to result, tis plhrogfories tou file
        String result1 = message.getData().getString("result");
        String result2 = message.getData().getString("string_0");
        String result3 = message.getData().getString("string_1");
        String result4 = message.getData().getString("string_2");
        String result5 = message.getData().getString("string_3");
        myIntent.putExtra("result1", result1);
        myIntent.putExtra("result2", result2);
        myIntent.putExtra("result3", result3);
        myIntent.putExtra("result4", result4);
        myIntent.putExtra("result5", result5);

        String result6 = message.getData().getString("string_4");
        String result8 = message.getData().getString("string_6");
        String result9 = message.getData().getString("string_7");
        myIntent.putExtra("result6", result6);
        myIntent.putExtra("result8", result8);
        myIntent.putExtra("result9", result9);


        String result10 = message.getData().getString("string_8");
        String result11 = message.getData().getString("string_9");
        String result12 = message.getData().getString("string_10");
        myIntent.putExtra("result10", result10);
        myIntent.putExtra("result11", result11);
        myIntent.putExtra("result12", result12);
        startActivityForResult(myIntent, 0);



    }





}
