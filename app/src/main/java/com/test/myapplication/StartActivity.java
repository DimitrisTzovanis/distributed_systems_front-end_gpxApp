package com.test.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//import kotlinx.coroutines.channels.Send;


public class StartActivity extends Activity {
    /** Called when the activity is first created. */

    Button next;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        next = (Button) findViewById(R.id.button1);

    }
    @Override
    protected void onStart() {
        super.onStart();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SendFile.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }
}
