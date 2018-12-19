package com.zoyberlo.project;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Lab6Activity extends AppCompatActivity {
    private ActivesAdapter mAdapter;
    private ArrayList<Active> activesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noActivesView;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 100;
    private DatabaseHelper db;


    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab6);
        time = findViewById(R.id.time);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        recyclerView = findViewById(R.id.recycler_view);
        noActivesView = findViewById(R.id.empty_notes_view);

        db = new DatabaseHelper(this);

        activesList.addAll(db.getAllActives());

        mAdapter = new ActivesAdapter(this, activesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
        toggleEmptyActives();
    }


    /**
     * Вставка новой активности в базу данных
     */
    private void createActive(String active) {
        // Вставляем в базу данных активность
        long id = db.insertActive(active);

        // Получаем активность из базы данных
        Active act = db.getActive(id);

        if (act != null) {
            activesList.add(0, act);
            mAdapter.notifyDataSetChanged();
            toggleEmptyActives();
        }
    }

    /**
     * Переключение списка и просмотр пустой заметки
     */
    private void toggleEmptyActives() {
        if (db.getActivesCount() > 0) {
            noActivesView.setVisibility(View.GONE);
        } else {
            noActivesView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(listener, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(listener);
    }


    SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Sensor mySensor = sensorEvent.sensor;

            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                long curTime = System.currentTimeMillis();

                if ((curTime - lastUpdate) > 100) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;

                    final float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                    if (speed > SHAKE_THRESHOLD) {
                        createActive("Активен");
                    } else if (x + y + z - last_x - last_y - last_z == 0) {
                        createActive("Не активен");
                    }
                    last_x = x;
                    last_y = y;
                    last_z = z;
                }
            }
        }


    };
}
