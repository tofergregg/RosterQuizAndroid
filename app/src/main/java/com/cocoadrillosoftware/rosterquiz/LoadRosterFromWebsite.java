package com.cocoadrillosoftware.rosterquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadRosterFromWebsite extends AppCompatActivity {
    public class RosterInfo {
        String name;
        String filename;

        RosterInfo(String n, String fn) {
            name = n;
            filename = fn;
        }

        @Override public String toString() {
            return name + "(" + filename + ")";
        }
    }

    ArrayAdapter adapter;
    final String sentinel = "_S__S_";

    ArrayList<RosterInfo> rosterNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_roster_from_website);
        final ListView rosterTable = (ListView) findViewById(R.id.rosterListView);


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, rosterNames);
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
                EditText nameEditText = (EditText)(findViewById(R.id.nameText));
                EditText pwEditText = (EditText)(findViewById(R.id.pwText));
                String nameText = nameEditText.getText().toString();
                String pwText = pwEditText.getText().toString();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nameEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(pwEditText.getWindowToken(), 0);

                CallAPI sendPost = new CallAPI();
                String bodyData = "https://www.eecs.tufts.edu/~cgregg/rosters/cgi-bin/list_rosters.cgi";


                bodyData += "?name="+nameText+"&pw="+pwText;
                sendPost.execute(bodyData);
                //rosterNames.add("Next");
                //adapter.notifyDataSetChanged();
            }
        });
    }

    public class CallAPI extends AsyncTask<String, String, String> {

        public CallAPI() {
            //set context variables if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {

            String urlString = params[0]; // URL to call

            String resultToDisplay = "";

            InputStream in = null;
            try {

                URL url = new URL(urlString);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //urlConnection.setRequestMethod("POST");

                in = new BufferedInputStream(urlConnection.getInputStream());


            } catch (Exception e) {
                String s = e.getMessage();
                System.out.println(e.getMessage());

                return e.getMessage();

            }

            resultToDisplay = new Scanner(in,"UTF-8").useDelimiter("\\A").next();

            return resultToDisplay;
        }


        @Override
        protected void onPostExecute(String result) {
            //Update the UI
            System.out.println(result);
            if (result.contains("User name and password do not match!")) {
                new AlertDialog.Builder(LoadRosterFromWebsite.this)
                        .setTitle("Error!")
                        .setMessage(result)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // nothing to do
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else {
                // split by newline
                String[] lines = result.split("\n");
                for (String line : lines) {
                    String[] parts = line.split(sentinel);
                    RosterInfo rosterInfo = new RosterInfo(parts[0],parts[1]);
                    rosterNames.add(rosterInfo);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}