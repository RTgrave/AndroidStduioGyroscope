package com.example.gyroscoppractise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView acceleration;
    Sensor accelerometer;
    SensorManager sensorManager;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acceleration=findViewById(R.id.acceleration);
        img = findViewById(R.id.img);

        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this,accelerometer,sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        acceleration.setText("X: "+(int)event.values[0]+"\nY: "+(int)event.values[1]+"\nZ: "+(int)event.values[2]);
        float[] rotation_matrix =  new float[16];
        SensorManager.getRotationMatrixFromVector(rotation_matrix, event.values);
        float[] remappedRotationMatrix = new float[16];
        SensorManager.remapCoordinateSystem(rotation_matrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, remappedRotationMatrix);
        float[] orientations = new float[3];
        SensorManager.getOrientation(remappedRotationMatrix, orientations);
        for (int i = 0; i < 3; i++)
        {
            orientations[i] = (float) (Math.toDegrees(orientations[i]));
        }
        img.setRotation(-orientations[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}