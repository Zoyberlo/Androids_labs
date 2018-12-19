package com.zoyberlo.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

public class Lab2Present extends AppCompatActivity {

    private TextView titlePresent;
    private TextView datePresent;
    private TextView descriptionPresent;
    private Button backButton;

    private static CharSequence title;
    private static CharSequence date;
    private static CharSequence description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2_present);

        titlePresent = findViewById(R.id.titlePresent);
        datePresent = findViewById(R.id.datePresent);
        descriptionPresent = findViewById(R.id.descriptionPresent);
        backButton = findViewById(R.id.lab2PresentBack);

        final Intent intent = new Intent(this, Lab2Activity.class);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");

        if ((title != null) && (date != null) && (description != null)) {
            Logger.d(title);
            Logger.d(date);
            Logger.d(description);

            titlePresent.setText(title);
            datePresent.setText(date);
            descriptionPresent.setText(description);

            getIntent().putExtra("title", (String) null);
            getIntent().putExtra("date", (String) null);
            getIntent().putExtra("description", (String) null);
        }
    }
}
