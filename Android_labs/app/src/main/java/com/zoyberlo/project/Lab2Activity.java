package com.zoyberlo.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "FieldCanBeLocal"})
public class Lab2Activity extends AppCompatActivity {

    private ListView list;

    private static List<String> namesList = new ArrayList<>();
    private static Map<String, ListItem> items = new HashMap<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = findViewById(R.id.lab2List);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namesList);
        list.setAdapter(adapter);

        final Intent presentIntent = new Intent(this, Lab2Present.class);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = namesList.get((int) id);
                String date = String.valueOf(items.get(title).getDate());
                String description = String.valueOf(items.get(title).getDescription());

                presentIntent.putExtra("title", title);
                presentIntent.putExtra("date", date);
                presentIntent.putExtra("description", description);
                presentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(presentIntent);
            }
        });

        final Intent lab2ContentIntent = new Intent(this, Lab2ContentActivity.class);
        FloatingActionButton fab = findViewById(R.id.lab2Add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(lab2ContentIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        CharSequence title = getIntent().getStringExtra("title");
        CharSequence date = getIntent().getStringExtra("date");
        CharSequence description = getIntent().getStringExtra("description");

        if ((title != null) && (date != null) && (description != null)) {
            namesList.add(String.valueOf(title));
            items.put(String.valueOf(title), new ListItem(title, date, description));
            adapter.notifyDataSetChanged();

            getIntent().putExtra("title", (String) null);
            getIntent().putExtra("date", (String) null);
            getIntent().putExtra("description", (String) null);
        }
    }
}
