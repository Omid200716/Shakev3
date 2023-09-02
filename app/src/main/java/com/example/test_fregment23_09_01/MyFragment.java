package com.example.test_fregment23_09_01;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment implements SensorEventListener {

    private TextView xValue, yValue, zValue;
    private ImageView accelerometerImage;
    private SensorManager sensorManager;
    private float rotationAngle = 0.0f;
    private static final float ROTATION_THRESHOLD = 30.0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accelerometer, container, false);

        xValue = rootView.findViewById(R.id.xValue);
        yValue = rootView.findViewById(R.id.yValue);
        zValue = rootView.findViewById(R.id.zValue);
        accelerometerImage = rootView.findViewById(R.id.image_accelerometer);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            xValue.setText("X-värde: " + x);
            yValue.setText("Y-värde: " + y);
            zValue.setText("Z-värde: " + z);

            rotationAngle = (x + y + z) / 3.0f;

            // Ändra färgen baserat på rotationen längs x-axeln
            if (Math.abs(rotationAngle) > ROTATION_THRESHOLD) {
                // Om rotationen överskrider tröskelvärdet, ändra färgen
                accelerometerImage.setColorFilter(Color.RED); // Ändra till önskad färg
            } else {
                // Återställ färgen om rotationen är inom tröskelvärdet
                accelerometerImage.setColorFilter(Color.GREEN); // Återställ till önskad färg
            }

            // Uppdatera bildens rotation
            accelerometerImage.setRotation(rotationAngle);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
