package com.cocoadrillosoftware.rosterquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class LoadRosterFromWebsite extends AppCompatActivity {

    ArrayList<String> rosterNames = new ArrayList<String>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_roster_from_website);
        final ListView rosterTable = (ListView) findViewById(R.id.rosterListView);


        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,rosterNames);
        rosterTable.setAdapter(adapter);
        registerForContextMenu(rosterTable);

        rosterTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

            }
        });

        final Button button = (Button) findViewById(R.id.showRostersButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rosterNames.add("Next");
                adapter.notifyDataSetChanged();
            }
        });
    }
}
