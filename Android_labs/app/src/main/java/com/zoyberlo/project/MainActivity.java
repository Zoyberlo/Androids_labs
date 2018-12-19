package com.zoyberlo.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private Button lab2;
    private Button lab3;
    private Button lab4;
    private Button lab5;
    private Button lab6;

    public MainActivity() {
        Logger.addLogAdapter(
                new AndroidLogAdapter(
                        PrettyFormatStrategy.newBuilder()
                                .showThreadInfo(false)
                                .methodCount(2)
                                .methodOffset(7)
                                .tag("LAB3_LOG")
                                .build()) {
                    /*
                     * Устанавливаем уровень вывода логов на дебуг
                     */

                    @Override
                    public boolean isLoggable(int priority, String tag) {
                        return BuildConfig.DEBUG;
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lab2 = findViewById(R.id.LAB2);
        lab3 = findViewById(R.id.LAB3);
        lab4 = findViewById(R.id.LAB4);
        lab5 = findViewById(R.id.LAB5);
        lab6 = findViewById(R.id.LAB6);

        final Intent intentLab2 = new Intent(this, Lab2Activity.class);
        final Intent intentLab3 = new Intent(this, Lab3Activity.class);
        final Intent intentLab4 = new Intent(this, Lab4Activity.class);
        final Intent intentLab5 = new Intent(this, Lab5Activity.class);
        final Intent intentLab6 = new Intent(this, Lab6Activity.class);

        lab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentLab2);
            }
        });

        lab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentLab3);
            }
        });

        lab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentLab4);
            }
        });

        lab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentLab5);
            }
        });

        lab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentLab6);
            }
        });
    }
}
