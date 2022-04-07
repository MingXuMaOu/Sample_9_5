package com.example.sample_9_5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySurfaceView mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);
    }
}