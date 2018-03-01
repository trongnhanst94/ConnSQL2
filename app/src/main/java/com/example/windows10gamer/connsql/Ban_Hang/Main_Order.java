package com.example.windows10gamer.connsql.Ban_Hang;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Order_Plus;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.Order_Plus;
import com.example.windows10gamer.connsql.Other.APIService_Sales;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.OrderList;
import com.example.windows10gamer.connsql.Other.RetrofitClient;
import com.example.windows10gamer.connsql.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main_Order extends AppCompatActivity {

    private ListView listView;
    private View parentView;
    EditText edBenginOrder;
    EditText edEndOrder;
    Button btnSearchOrder;
    FloatingActionButton fabReportOrder;
    CheckBox cbCasang, cbCachieu;
    private ArrayList<Order> contactList, reportList;
    private Adapter_Order_Plus adapter;
    ArrayList<Order_Plus> temp;
    View view;
    String dateBegin, dateEnd, dateCasang, dateCachieu;
    SharedPreferences shared ;
    String chinhanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_order);
        edBenginOrder  = findViewById(R.id.edBeginOrder);
        edEndOrder     = findViewById(R.id.edEndOrder);
        btnSearchOrder = findViewById(R.id.btnDateOrder);
        fabReportOrder = findViewById(R.id.fabReportOrder);
        cbCasang       = findViewById(R.id.cbCasangOrder);
        cbCachieu      = findViewById(R.id.cbCachieuOrder);
        cbCasang.setChecked(true);
        cbCachieu.setChecked(true);
        edBenginOrder.setText(Keys.getDateNow());
        edEndOrder.setText(Keys.getDateNow());
        contactList = new ArrayList<>();
        temp = new ArrayList<>();
        fabReportOrder.setVisibility(View.INVISIBLE);
        listView = findViewById(R.id.listView);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Order> sendlist = new ArrayList<>();
                for (int i = 0; i < contactList.size(); i++) {
                    if (temp.get(position).getMaDonhang().equals(contactList.get(i).getMaDonhang())){
                        sendlist.add(contactList.get(i));
                    }
                }
                Intent intent = new Intent(Main_Order.this, Main_Information_Order.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("keyMaOrder", sendlist.get(0).getMaDonhang());
                bundle.putParcelableArrayList("arrayOrder", sendlist);
                intent.putExtra("DataOrder", bundle);
                startActivity(intent);
            }
        });
        edBenginOrder.setInputType(InputType.TYPE_NULL);
        edBenginOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = java.util.Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Order.this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        edBenginOrder.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });
        edEndOrder.setInputType(InputType.TYPE_NULL);
        edEndOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Order.this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        edEndOrder.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });
        btnSearchOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Order.this).show();
                else {
                    dateBegin = String.valueOf(edBenginOrder.getText());
                    dateEnd = String.valueOf(edEndOrder.getText());
                    if (cbCasang.isChecked()) dateCasang = "Ca sáng";
                    else dateCasang = "FALSE";
                    if (cbCachieu.isChecked()) dateCachieu = "Ca chiều";
                    else dateCachieu = "FALSE";
                    LoadJson(dateBegin, dateEnd, dateCasang, dateCachieu, v);
                }
            }
        });

        fabReportOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Order.this).show();
                else {
                    if (contactList.size() > 0){
                        dateBegin = String.valueOf(edBenginOrder.getText());
                        dateEnd = String.valueOf(edEndOrder.getText());
                        if (cbCasang.isChecked()) dateCasang = "Ca sáng";
                        else dateCasang = "FALSE";
                        if (cbCachieu.isChecked()) dateCachieu = "Ca chiều";
                        else dateCachieu = "FALSE";
                        Intent intent = new Intent(Main_Order.this, Main_Report_Sales.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("chinhanh", chinhanh);
                        bundle.putString("dateBegin", dateBegin);
                        bundle.putString("dateEnd", dateEnd);
                        bundle.putString("dateCasang", dateCasang);
                        bundle.putString("dateCachieu", dateCachieu);
                        intent.putExtra("ReportBundle", bundle);
                        startActivity(intent);
                    } else {
                        new CustomToast().Show_Toast(Main_Order.this, findViewById(android.R.id.content), "Không có dữ liệu!");
                    }
                }
            }
        });
        cbCasang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!cbCachieu.isChecked() && isChecked == false){
                    cbCachieu.setChecked(true);
                }
            }
        });
        cbCachieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!cbCasang.isChecked() && isChecked == false){
                    cbCasang.setChecked(true);
                }
            }
        });
    }

    private void sortList(ArrayList<Order_Plus> list) {
        Collections.sort(list, new Comparator<Order_Plus>() {
            @Override
            public int compare(Order_Plus lhs, Order_Plus rhs) {
                return rhs.getMaDonhang().compareTo(lhs.getMaDonhang());
            }
        });
    }
    public void getList(ArrayList<Order> List){
        this.reportList = List;
    }

    public void LoadJson(final String loadBegin, final String loadEnd, final String loadCasang, final String loadCachieu, final View v) {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(Main_Order.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();

            APIService_Sales api = RetrofitClient.getApiService();

            Call<OrderList> call = api.getOrder(loadBegin, loadEnd);

            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    dialog.dismiss();
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        contactList.clear();
                        temp.clear();
                        for (int i = 0; i < orignal.size(); i++) {
                            if(orignal.get(i).getChinhanh().equals(chinhanh)) {
                                if (loadCasang.equals(orignal.get(i).getCalam())) {
                                    contactList.add(orignal.get(i));
                                }
                                if (loadCachieu.equals(orignal.get(i).getCalam())) {
                                    contactList.add(orignal.get(i));
                                }
                            }
                        }
                        for (int j = 0; j < contactList.size(); j++) {
                            if (contactList.get(j).getChinhanh().equals(chinhanh)){
                                int result = sosanhOrder(temp, contactList.get(j).getMaDonhang());
                                if ( result == -1){
                                    temp.add( new Order_Plus(
                                            contactList.get(j).getMaDonhang(),
                                            contactList.get(j).getNgay(),
                                            contactList.get(j).getCalam(),
                                            contactList.get(j).getGio(),
                                            contactList.get(j).getChinhanh(),
                                            contactList.get(j).getMaNhanvien(),
                                            contactList.get(j).getTenNhanvien(),
                                            contactList.get(j).getMaSanpham(),
                                            contactList.get(j).getTenSanpham(),
                                            contactList.get(j).getBaohanhSanpham(),
                                            contactList.get(j).getNguonSanpham(),
                                            contactList.get(j).getNgaynhapSanpham(),
                                            contactList.get(j).getVonSanpham(),
                                            contactList.get(j).getGiaSanpham(),
                                            contactList.get(j).getGiamgia(),
                                            contactList.get(j).getGhichuSanpham(),
                                            contactList.get(j).getTenKhachhang(),
                                            contactList.get(j).getSodienthoaiKhachhang(),
                                            contactList.get(j).getGhichuKhachhang(),
                                            contactList.get(j).getMaNhanvienbandum(),
                                            contactList.get(j).getTenNhanvienbandum(),
                                            1
                                    ));
                                } else {
                                    temp.set(result, new Order_Plus(
                                            temp.get(result).getMaDonhang(),
                                            temp.get(result).getNgay(),
                                            temp.get(result).getCalam(),
                                            temp.get(result).getGio(),
                                            temp.get(result).getChinhanh(),
                                            temp.get(result).getMaNhanvien(),
                                            temp.get(result).getTenNhanvien(),
                                            temp.get(result).getMaSanpham(),
                                            temp.get(result).getTenSanpham(),
                                            temp.get(result).getBaohanhSanpham(),
                                            temp.get(result).getNguonSanpham(),
                                            temp.get(result).getNgaynhapSanpham(),
                                            temp.get(result).getVonSanpham(),
                                            temp.get(result).getGiaSanpham(),
                                            temp.get(result).getGiamgia(),
                                            temp.get(result).getGhichuSanpham(),
                                            temp.get(result).getTenKhachhang(),
                                            temp.get(result).getSodienthoaiKhachhang(),
                                            temp.get(result).getGhichuKhachhang(),
                                            temp.get(result).getMaNhanvienbandum(),
                                            temp.get(result).getTenNhanvienbandum(),
                                            temp.get(result).getSoluong()+1
                                    ));
                                }
                            }
                        }

                        getList(contactList);
                        if (temp.size() == 0){
                            new CustomToast().Show_Toast(Main_Order.this, v, "Không có đơn hàng!");
                        }
                        sortList(temp);
                        adapter = new Adapter_Order_Plus(Main_Order.this, temp);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        fabReportOrder.setVisibility(View.VISIBLE);
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

    private int sosanhOrder(ArrayList<Order_Plus> order_h, String orderName) {
        int result = -1;
        if (order_h.size() != 0){
            for (int i = 0; i < order_h.size(); i++){
                if (order_h.get(i).getMaDonhang().equals(orderName)){
                    result = i;
                }
            }
        }
        return result;
    }
}