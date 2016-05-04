package edu.uw.motiondemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MotionActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "Motion";

    private TextView txtX, txtY, txtZ;

    private SensorManager sensorManager;
    private Sensor sensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        //views for easy access
        txtX = (TextView)findViewById(R.id.txt_x);
        txtY = (TextView)findViewById(R.id.txt_y);
        txtZ = (TextView)findViewById(R.id.txt_z);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensors) {
            Log.v(TAG, s.toString());
        }

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        if (sensor == null) {
            Log.v(TAG, "No sensor available!");
            finish();
        }
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, sensor);

        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        txtX.setText("" + Math.toDegrees(values[0]));
        txtY.setText("" + Math.toDegrees(values[1]));
        txtZ.setText("" + Math.toDegrees(values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //leave blank
    }
}
