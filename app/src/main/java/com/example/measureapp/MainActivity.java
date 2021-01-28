package com.example.measureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor senseAccel;
    private long lastupdate = 0;
    private  float last_x,last_y,last_z;
    private static final int SHAKE_THRESHOLD = 600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senseAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, senseAccel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, senseAccel, sensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            long curTime = System.currentTimeMillis();
            if ((curTime - lastupdate)>100) {
                long diffTime = (curTime - lastupdate);
                lastupdate = curTime;

                float speed = Math.abs(x+y+z -last_x - last_y - last_z)/diffTime*10000;
                setContentView(R.layout.activity_main);
                last_x = x;
                last_y = y;
                last_z = z;
                TextView speed_text = (TextView)findViewById(R.id.speed);
                TextView loc_xtext = (TextView)findViewById(R.id.loc_x);
                TextView loc_ytext = (TextView)findViewById(R.id.loc_y);
                TextView loc_ztext = (TextView)findViewById(R.id.loc_z);
                loc_xtext.setText("acceleration x = "+last_x+"m/s2");
                loc_ytext.setText("acceleration y = "+last_y+"m/s2");
                loc_ztext.setText("acceleration z x = "+last_z+"m/s2");
                speed_text.setText("Speed = "+speed+"m/s");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}