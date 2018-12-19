package com.zoyberlo.project;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Lab3Activity extends AppCompatActivity {

    private TextView loginText;
    private TextView passwordText;
    private Button authBtn;
    private Button regBtn;

    private static Context appContext;

    private static File file;
    private static String readData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3);

        loginText = findViewById(R.id.lab3Login);
        passwordText = findViewById(R.id.lab3Pass);
        authBtn = findViewById(R.id.lab3Auth);
        regBtn = findViewById(R.id.lab3Reg);

        appContext = getApplicationContext();

        file = new File(getFilesDir(), "LAB3DATA");

        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String login = loginText.getText().toString();
                final String password = passwordText.getText().toString();

                //new FileReadTask().execute();
                if ((readData != null) && (!readData.isEmpty())) {
                    Map<String, Object> data;
                    try {
                        data = JsonUtils.jsonToMap(new JSONObject(readData));
                        if ((data.get(login) != null) && (data.get(login).equals(password))) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(appContext, "USERDATA: login [" + login + "] password [" + password + "]", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        Logger.e(e, "JSON PARSING ERROR!!!!");
                    }
                }
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginText.getText().toString();
                String password = passwordText.getText().toString();

                Map<String, Object> data = new HashMap<>();
                if ((readData == null) || (readData.isEmpty())) {
                    data.put(login, password);
                    readData = JsonUtils.mapToJson(data).toString();
                } else {
                    try {
                        data = JsonUtils.jsonToMap(new JSONObject(readData));
                        data.put(login, password);
                        readData = JsonUtils.mapToJson(data).toString();
                    } catch (JSONException e) {
                        Logger.e(e, "ADD TO JSON");
                    }
                }
            }
        });
    }
}
