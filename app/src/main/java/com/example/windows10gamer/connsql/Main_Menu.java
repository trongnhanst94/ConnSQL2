package com.example.windows10gamer.connsql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Main_Menu extends AppCompatActivity {
    Button btnDanhsach, btnScan, btnSales, btnDanhsachkiemkho, btnListOrder;
    String session_username, session_ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btnDanhsach        = (Button) findViewById(R.id.btnDanhsachsanpham);
        btnScan            = (Button) findViewById(R.id.btnScanQR);
        btnSales           = (Button) findViewById(R.id.btnSales);
        btnListOrder       = (Button) findViewById(R.id.btnListOrder);
        btnDanhsachkiemkho = (Button) findViewById(R.id.btnDanhsachkiemkho);
        btnDanhsach.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intentget = getIntent();
            session_username = intentget.getStringExtra("session_username");
            Intent intentput = new Intent(Main_Menu.this, Main_List_Excel.class);
            intentput.putExtra("session_username", session_username);
            startActivity(intentput);
            }
        });

        btnListOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentget = getIntent();
                session_username = intentget.getStringExtra("session_username");
                Intent intentput = new Intent(Main_Menu.this, Main_Order.class);
                intentput.putExtra("session_username", session_username);
                startActivity(intentput);
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentget = getIntent();
                session_username = intentget.getStringExtra("session_username");
                session_ma = intentget.getStringExtra("session_ma");
                Intent intentput = new Intent(Main_Menu.this, Main_Kiemkho.class);
                intentput.putExtra("session_username", session_username);
                intentput.putExtra("session_ma", session_ma);
                startActivity(intentput);
            }
        });

        btnDanhsachkiemkho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentget = getIntent();
                session_username = intentget.getStringExtra("session_username");
                session_ma = intentget.getStringExtra("session_ma");
                Intent intentput = new Intent(Main_Menu.this, Main_Ketqua_Kiemkho.class);
                intentput.putExtra("session_username", session_username);
                intentput.putExtra("session_ma", session_ma);

                startActivity(intentput);
            }
        });

        btnSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentget = getIntent();
                session_username = intentget.getStringExtra("session_username");
                session_ma = intentget.getStringExtra("session_ma");
                Intent intentput = new Intent(Main_Menu.this, Main_Sales.class);
                intentput.putExtra("session_username", session_username);
                intentput.putExtra("session_ma", session_ma);
                startActivity(intentput);
            }
        });
    }
}
