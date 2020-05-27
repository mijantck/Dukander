package com.mrsoftit.dukander;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfitActivity extends AppCompatActivity {



    Button date;

    String selectetDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);

        date = findViewById(R.id.choose_month);
        final Calendar today = Calendar.getInstance();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date calendar1 = Calendar.getInstance().getTime();
                DateFormat df1 = new SimpleDateFormat("yyyy");
                String todayString = df1.format(calendar1);
                final int presentYear = Integer.parseInt(todayString);

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(ProfitActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {

                       int selectmonth = 1+ selectedMonth;

                       if (selectmonth<10){

                           String selectm = "0"+selectmonth;

                           selectetDate = selectm +""+ selectedYear;


                       }else {

                           selectetDate = selectedMonth +""+ selectedYear;
                       }

                        Toast.makeText(ProfitActivity.this,  selectetDate, Toast.LENGTH_SHORT).show();

                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setActivatedMonth(Calendar.JULY)
                        .setMinYear(1990)
                        .setActivatedYear(presentYear)
                        .setMaxYear(2050)
                        .setMinMonth(Calendar.JANUARY)
                        .setTitle("Select trading month")
                        .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                        // .setMaxMonth(Calendar.OCTOBER)
                        // .setYearRange(1890, 1890)
                        // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                        //.showMonthOnly()
                        // .showYearOnly()
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {

                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {

                                // Toast.makeText(MainActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build()
                        .show();


            }
        });
    }


    private void setNormalPicker() {
        setContentView(R.layout.activity_main);
        final Calendar today = Calendar.getInstance();
        findViewById(R.id.choose_month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
/*
        findViewById(R.id.date_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

}
