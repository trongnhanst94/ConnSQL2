package com.example.windows10gamer.connsql.Kho;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows10gamer.connsql.Adapter.Adapter_Kho;
import com.example.windows10gamer.connsql.Object.Kho;
import com.example.windows10gamer.connsql.Other.APIService_Kho;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.KhoList;
import com.example.windows10gamer.connsql.Other.RetrofitClient;
import com.example.windows10gamer.connsql.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Nhaphang extends AppCompatActivity {
    TextView tvtenNV, tvmaNV, tvchinhanh;
    ListView listView;
    String tenNV, maNV, chinhanh;
    private SharedPreferences shared;
    FloatingActionButton fab;
    ArrayList<Kho> temp = new ArrayList<>();
    Adapter_Kho adapter;
    ArrayList<Kho> orignal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nhaphang);
        listView = findViewById(R.id.listView);
        tvtenNV = findViewById(R.id.tvtenNV);
        tvmaNV = findViewById(R.id.tvmaNV);
        tvchinhanh = findViewById(R.id.tvchinhanh);
        fab = findViewById(R.id.fab);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        shared = getSharedPreferences("login", MODE_PRIVATE);
        tenNV = shared.getString("ma", "");
        maNV = shared.getString("shortName", "");
        tvchinhanh.setText(chinhanh);
        tvmaNV.setText("Mã số: "+tenNV);
        tvtenNV.setText("Tên nhân viên: "+maNV);
        adapter = new Adapter_Kho(Main_Nhaphang.this, orignal);
        //listView.setAdapter(adapter);
        LoadJson();
    }

    public void LoadJson() {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(Main_Nhaphang.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();

            APIService_Kho api = RetrofitClient.getApiService_kho();

            Call<KhoList> call = api.getAllProduct_kho();

            call.enqueue(new Callback<KhoList>() {
                @Override
                public void onResponse(Call<KhoList> call, Response<KhoList> response) {
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts_kho();
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<KhoList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            Toasty.error(this, "Không có mạng Internet", Toast.LENGTH_LONG, true).show();
        }
    }
}
