package com.example.test_fregment23_09_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

public class SecondMainactivity extends AppCompatActivity implements SensorEventListener{


    ImageView back;
    private TextView xValue, yValue, zValue;
    private ImageView accelerometerImage;
    private SensorManager sensorManager;
    private float rotationAngle = 0.0f;

    private static final float ROTATION_THRESHOLD = 30.0f;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_mainactivity);


        xValue = findViewById(R.id.xValue);
        yValue = findViewById(R.id.yValue);
        zValue = findViewById(R.id.zValue);

        back = findViewById(R.id.image_icon);
        accelerometerImage = findViewById(R.id.image_accelerometer);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y =event.values[1];
            float z = event.values[2];


            xValue.setText("X-värde: " + x);
            yValue.setText("Y-värde: " + y);
            zValue.setText("Z-värde: " + z);

          


            rotationAngle = (x + y + z) / 3.0f;

            if (Math.abs(rotationAngle) > ROTATION_THRESHOLD) {
                accelerometerImage.setColorFilter(Color.RED);

                // Visa ett Toast-meddelande när skakning upptäcks
                Toast.makeText(this, "Shake detected!", Toast.LENGTH_SHORT).show();
            } else {
                accelerometerImage.setColorFilter(Color.GREEN);
            }





            accelerometerImage.setRotation(rotationAngle);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
