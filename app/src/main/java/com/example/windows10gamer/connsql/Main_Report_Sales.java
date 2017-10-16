package com.example.windows10gamer.connsql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.windows10gamer.connsql.Object.Order;

import java.util.ArrayList;
import java.util.Collections;

public class Main_Report_Sales extends AppCompatActivity {
    static ArrayList<Order> reportList;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_report_sales);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("ReportBundle");
        reportList = bundle.getParcelableArrayList("ReportList");

        tableLayout = (TableLayout) findViewById(R.id.tbReport);

        int soDong = 16;
        for (int i = 0; i<reportList.size(); i++){

        }
        int soCot = 3;

        Collections.shuffle(Main_Report_Sales.reportList);

        for (int i = 1; i <= soDong; i++){
            TableRow tableRow = new TableRow(this);

            for (int j = 1; j <= soCot; j++) {
                final int pos = soCot*(i-1)+j-1;

            }
            tableLayout.addView(tableRow);
        }
    }
}