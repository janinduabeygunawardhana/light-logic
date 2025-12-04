package com.example.lightlogic;

import static android.hardware.Sensor.TYPE_LIGHT;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Element extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListner;
    private float maxValue;
    private String Id;

    TextView title,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);

        FirebaseDatabase database = FirebaseDatabase.getInstance();



        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(TYPE_LIGHT);

        title = findViewById(R.id.elementTitle);
        id = findViewById(R.id.elementId);

        SeekBar seekBar=(SeekBar)findViewById(R.id.seekBar);
        TextView iLevel = (TextView)findViewById(R.id.iLevel);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            title.setText(bundle.getString("Title"));
            id.setText(bundle.getString("Id"));
            this.Id = bundle.getString("Id");
        }
        DatabaseReference myRef = database.getReference("LightsLevels").child(this.Id);
//        if(this.Id == "123"){
//            myRef = database.getReference("illuminousLevel");
//        }
//        else{
//            myRef = database.getReference("LightsLevels").child(this.Id);
//        }



        if (lightSensor == null){
            Toast.makeText(this,"does not have light sensor", Toast.LENGTH_SHORT).show();
            finish();
        }

        maxValue = lightSensor.getMaximumRange();
        System.out.println("Max value");
        System.out.println(maxValue);

        lightEventListner = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float value = event.values[0];
                System.out.println(value);
                iLevel.setText(String.valueOf(value));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println(progress);
                System.out.println(Id);
                if(Id == "123"){
                    DatabaseReference myRef2 = database.getReference("illuminousLevel");
                    myRef2.setValue(progress);
                }
                else{
                    myRef.setValue(progress);
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListner,lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListner);
    }
}