package com.zoyberlo.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

@SuppressWarnings("FieldCanBeLocal")
public class Lab2ContentActivity extends AppCompatActivity {

    private Button addButton;
    private TextView titleText;
    private TextView dateText;
    private TextView descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2_content);

        addButton = findViewById(R.id.lab2ContentAdd);
        titleText = findViewById(R.id.lab2ContentTitle);
        dateText = findViewById(R.id.lab2ContentDate);
        descriptionText = findViewById(R.id.lab2ContentDescription);

        final Intent intent = new Intent(this, Lab2Activity.class);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();
                String date = dateText.getText().toString();
                String description = descriptionText.getText().toString();

                Logger.w(date);

                if ((!title.isEmpty()) && (!date.isEmpty()) && (!description.isEmpty())) {
                    intent.putExtra("title", title);
                    intent.putExtra("date", date);
                    intent.putExtra("description", description);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
