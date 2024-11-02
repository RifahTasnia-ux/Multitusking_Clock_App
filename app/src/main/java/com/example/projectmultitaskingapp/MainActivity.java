package com.example.projectmultitaskingapp;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.DigitalClock;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.projectmultitaskingapp.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ListView listView ;

    String[] ListElements = new String[] {  };

    List<String> ListElementsArrayList ;

    ArrayAdapter<String> adapter ;
    private Chronometer stopwatch;
    private long pauseOffset;
    private boolean is_running;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date currentdate = Calendar.getInstance().getTime();
        String datenow = DateFormat.getDateInstance(DateFormat.FULL).format(currentdate);
        DigitalClock digital_clock = (DigitalClock) findViewById(R.id.digital_clock);
        stopwatch = findViewById(R.id.stopwatch);
        Button button= (Button) findViewById(R.id.button);
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(datenow);
        Button lap = (Button)findViewById(R.id.button1) ;
        listView = (ListView)findViewById(R.id.list_view1);
        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );
        listView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.example.projectmultitaskingapp.DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date_picker");
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Selected Calendar Date or Event will be shown here", Toast.LENGTH_SHORT).show();
            }
        });
        digital_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Digital Clock", Toast.LENGTH_SHORT).show();
            }
        });
        stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Stopwatch", Toast.LENGTH_SHORT).show();
            }
        });
        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(stopwatch.getText().toString());

                adapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView date = (TextView) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Selected Date or Event from Calendar View", Toast.LENGTH_SHORT).show();
            }
        });
        date.setText(currentDateString);
    }

    public void startStopwatch(View v){
        if(!is_running){
            stopwatch.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            stopwatch.start();
            is_running = true;
        }

    }
    public void pauseStopwatch(View v){
        if(is_running){
            stopwatch.stop();
            pauseOffset = SystemClock.elapsedRealtime() - stopwatch.getBase();
            is_running = false;
        }
    }
    public void resetStopwatch(View v){
        stopwatch.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;


    }
    public void delete_lap_times(View v){
        ListElementsArrayList.clear();

        adapter.notifyDataSetChanged();

    }

}