package com.example.windows10gamer.connsql;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Order;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.OrderList;
import com.example.windows10gamer.connsql.Other.RetrofitClient;
import com.example.windows10gamer.connsql.service.APIService_Sales;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main_Order extends AppCompatActivity {

    private ListView listView;
    private View parentView;
    EditText edBenginOrder;
    EditText edEndOrder;
    Button btnSearchOrder;
    CheckBox cbCasang, cbCachieu;
    private ArrayList<Order> contactList;
    private Adapter_Order adapter;
    ArrayList<Order> temp;
    String dateBegin, dateEnd, dateCasang, dateCachieu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_order);
        edBenginOrder = (EditText) findViewById(R.id.edBeginOrder);
        edEndOrder = (EditText) findViewById(R.id.edEndOrder);
        btnSearchOrder = (Button) findViewById(R.id.btnDateOrder);
        cbCasang = (CheckBox) findViewById(R.id.cbCasangOrder);
        cbCachieu = (CheckBox) findViewById(R.id.cbCachieuOrder);
        cbCasang.setChecked(true);
        cbCachieu.setChecked(true);
        edBenginOrder.setText(Keys.getDateNow());
        edEndOrder.setText(Keys.getDateNow());
        contactList = new ArrayList<>();
        temp = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_Order.this, Main_Information_Order.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("keyMaOrder", temp.get(position).getMaOrder());
                bundle.putParcelableArrayList("arrayOrder", contactList);
                intent.putExtra("DataOrder", bundle);
                startActivity(intent);
            }
        });
        edBenginOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = java.util.Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Order.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edBenginOrder.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        edEndOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Order.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edEndOrder.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });
        btnSearchOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateBegin = String.valueOf(edBenginOrder.getText());
                dateEnd   = String.valueOf(edEndOrder.getText());
//                edBenginOrder.setEnabled(false);
//                edEndOrder.setEnabled(false);
                if (cbCasang.isChecked())  dateCasang  = "Ca sáng"; else dateCasang = "FALSE";
                if (cbCachieu.isChecked()) dateCachieu = "Ca chiều"; else dateCachieu = "FALSE";
                LoadJson(dateBegin, dateEnd, dateCasang, dateCachieu, v);
            }
        });

    }

    public void LoadJson(final String loadBegin, final String loadEnd, final String loadCasang, final String loadCachieu, final View v) {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(Main_Order.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.show();

            APIService_Sales api = RetrofitClient.getApiService();

            Call<OrderList> call = api.getAllProduct();

            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    dialog.dismiss();
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        contactList.clear();
                        temp.clear();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date hom = null; Date loadBegin1 = null; Date loadEnd1 = null;
                        try {
                            loadBegin1 = (Date) simpleDateFormat.parse(loadBegin);
                            loadEnd1 = (Date) simpleDateFormat.parse(loadEnd);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < orignal.size(); i++) {
                            try {
                                hom = (Date) simpleDateFormat.parse(Keys.setDate(orignal.get(i).getDateOrder()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(hom.compareTo(loadBegin1) >= 0  && hom.compareTo(loadEnd1) <= 0) {
                                if (loadCasang.equals(orignal.get(i).getTimeOrder())) {
                                    contactList.add(orignal.get(i));
                                }
                                if (loadCachieu.equals(orignal.get(i).getTimeOrder())) {
                                    contactList.add(orignal.get(i));
                                }
                            }
                        }
                        for (int j = 0; j < contactList.size(); j++) {
                            if (sosanhOrder(temp, contactList.get(j).getMaOrder()) == -1){
                                temp.add(contactList.get(j));
                            }
                        }
                        if (temp.size() == 0){
                            new CustomToast().Show_Toast(Main_Order.this, v, "Không có đơn hàng!");
                        }
                        adapter = new Adapter_Order(Main_Order.this, temp);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } else {
                        new CustomToast().Show_Toast(Main_Order.this, v, "Không có response!");
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            new CustomToast().Show_Toast(Main_Order.this,parentView, "Không có Internet!");
        }
    }

    private int sosanhOrder(ArrayList<Order> order_h, String orderName) {
        int result = -1;
        if (order_h.size() != 0){
            for (int i = 0; i < order_h.size(); i++){
                if (order_h.get(i).getMaOrder().equals(orderName)){
                    result = i;
                }
            }
        }
        return result;
    }
}