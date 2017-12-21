package com.example.windows10gamer.connsql;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Realtime_Order;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Other.APIService_Sales;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.OrderList;
import com.example.windows10gamer.connsql.Other.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main_Realtime_Order extends AppCompatActivity {

    private ListView listView;
    private View parentView;
    Button btnSearchOrder;
    FloatingActionButton fabReportOrder;
    private ArrayList<Order> contactList, reportList;
    private Adapter_Realtime_Order adapter;
    ArrayList<Order> temp;
    View view;
    String dateBegin, dateEnd;
    SharedPreferences shared ;
    String chinhanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_realtime_order);
        btnSearchOrder = (Button) findViewById(R.id.btnDateOrder);
        fabReportOrder = (FloatingActionButton) findViewById(R.id.fabReportOrder);
        contactList = new ArrayList<>();
        temp = new ArrayList<>();
        fabReportOrder.setVisibility(view.INVISIBLE);
        listView = (ListView) findViewById(R.id.listView);
        dateBegin = Keys.getDateNowPlus(0);
        dateEnd   = Keys.getDateNowPlus(0);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Realtime_Order.this).show();
                else {
                    Intent intent = new Intent(Main_Realtime_Order.this, Main_Information_Order.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    bundle.putString("keyMaOrder", temp.get(position).getMaDonhang());
                    bundle.putParcelableArrayList("arrayOrder", contactList);
                    intent.putExtra("DataOrder", bundle);
                    startActivity(intent);
                }
            }
        });

        if(!Connect_Internet.checkConnection(getApplicationContext()))
            Connect_Internet.buildDialog(Main_Realtime_Order.this).show();
        else {
            LoadJson();
        }

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Realtime_Order.this).show();
                else {LoadJson();}
                handler.postDelayed(this, Keys.LOAD_REALTIME);
            }
        };

        handler.postDelayed(r, 1000);

        fabReportOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Realtime_Order.this).show();
                else {
                    Intent intent = new Intent(Main_Realtime_Order.this, Main_Report_Sales.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("ReportList", contactList);
                    bundle.putString("dateBegin", dateBegin);
                    bundle.putString("dateEnd", dateEnd);
                    bundle.putString("dateCasang", Keys.TENCASANG);
                    bundle.putString("dateCachieu", Keys.TENCACHIEU);
                    intent.putExtra("ReportBundle", bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void sortList(ArrayList<Order> list) {
        Collections.sort(list, new Comparator<Order>() {
            @Override
            public int compare(Order lhs, Order rhs) {
                return rhs.getMaDonhang().compareTo(lhs.getMaDonhang());
            }
        });
    }

    public void LoadJson() {
        if (Connect_Internet.checkConnection(getApplicationContext())) {

            APIService_Sales api = RetrofitClient.getApiService();

            Call<OrderList> call = api.getRealtime_Order(chinhanh, Keys.getDateNow());

            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        contactList.clear();
                        temp.clear();
                        for (int i = 0; i < orignal.size(); i++) {
                           contactList.add(orignal.get(i));
                        }
                        for (int j = 0; j < contactList.size(); j++) {
                            if (contactList.get(j).getChinhanh().equals(chinhanh)){
                                temp.add(contactList.get(j));
                            }
                        }
                        if (temp.size() == 0){
                            new CustomToast().Show_Toast(Main_Realtime_Order.this, findViewById(android.R.id.content), "Không có đơn hàng!");
                        }
                        sortList(temp);
                        adapter = new Adapter_Realtime_Order(Main_Realtime_Order.this, temp);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        fabReportOrder.setVisibility(view.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                }
            });

        } else {
            new CustomToast().Show_Toast(Main_Realtime_Order.this,parentView, "Không có Internet!");
        }
    }
}