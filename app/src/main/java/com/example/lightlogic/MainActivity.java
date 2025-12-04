package com.example.lightlogic;

import static android.hardware.Sensor.TYPE_LIGHT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListner;
    private float maxValue;
//    TextView iLevel = (TextView) findViewById(R.id.iLevel);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("illuminousLevel");

        Button loginBtn = findViewById(R.id.loginbtn);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(TYPE_LIGHT);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemMenu.class);
                startActivity(intent);

            }
        });

//        TextView iLevel = (TextView)findViewById(R.id.iLevel);
//        SeekBar seekBar=(SeekBar)findViewById(R.id.seekBar); // initiate the Seek bar
//
//        if (lightSensor == null){
//            Toast.makeText(this,"does not have light sensor", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//
//        maxValue = lightSensor.getMaximumRange();
//        System.out.println("Max value");
//        System.out.println(maxValue);
//
//        lightEventListner = new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                float value = event.values[0];
//                System.out.println(value);
//                iLevel.setText(String.valueOf(value));
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//            }
//        };
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                System.out.println(progress);
//                myRef.setValue(progress);
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        sensorManager.registerListener(lightEventListner,lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        sensorManager.unregisterListener(lightEventListner);
//    }
}