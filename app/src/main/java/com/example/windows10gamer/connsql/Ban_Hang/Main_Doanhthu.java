package com.example.windows10gamer.connsql.Ban_Hang;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Doanhthu;
import com.example.windows10gamer.connsql.Object.Doanhthu;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main_Doanhthu extends AppCompatActivity {

    private ListView listView;
    private View parentView;
    EditText edBengin;
    EditText edEnd;
    Button btnSearch;
    FloatingActionButton fabAdd;
    CheckBox cbCasang, cbCachieu;
    ArrayList<Doanhthu> list = new ArrayList<>();
    private Adapter_Doanhthu adapter;
    String dateBegin, dateEnd, dateCasang = "", dateCachieu = "";
    SharedPreferences shared ;
    String chinhanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doanhthu);
        edBengin  = findViewById(R.id.edBegindt);
        edEnd     = findViewById(R.id.edEnddt);
        btnSearch = findViewById(R.id.btnTimdt);
        fabAdd = findViewById(R.id.fabdt);
        cbCasang       = findViewById(R.id.cbCasangdt);
        cbCachieu      = findViewById(R.id.cbCachieudt);
        edBengin.setText(Keys.getDateNow());
        edEnd.setText(Keys.getDateNow());
        listView = findViewById(R.id.lvdt);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        adapter = new Adapter_Doanhthu(Main_Doanhthu.this, R.layout.adapter_doanhthu, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_Doanhthu.this, Main_Tao_BCDoanhthu.class);
                Bundle bundle = new Bundle();
                bundle.putString("check", "1");
                bundle.putInt("position", position);
                bundle.putParcelableArrayList("list", list);
                intent.putExtra("DataDoanhthu", bundle);
                startActivity(intent);
            }
        });
        dateBegin = Keys.getDateNow();
        dateEnd = Keys.getDateNow();
        cbCasang.setChecked(true);
        dateCasang = Keys.CA_SANG;
        cbCachieu.setChecked(true);
        dateCachieu = Keys.CA_CHIEU;
        new GetDT().execute();
        edBengin.setInputType(InputType.TYPE_NULL);
        edBengin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = java.util.Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                if (Build.VERSION.SDK_INT == 24) {
                    final Context contextThemeWrapper =
                            new ContextThemeWrapper(Main_Doanhthu.this, android.R.style.Theme_Holo_Light_Dialog);
                    try {
                        DatePickerDialog datePickerDialog = new Keys.FixedHoloDatePickerDialog(contextThemeWrapper, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                edBengin.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    } catch ( Fragment.InstantiationException e) {
                        e.printStackTrace();
                    }
                } else {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Doanhthu.this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            edBengin.setText(simpleDateFormat.format(calendar.getTime()));
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });
        edEnd.setInputType(InputType.TYPE_NULL);
        edEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                if (Build.VERSION.SDK_INT == 24) {
                    final Context contextThemeWrapper =
                            new ContextThemeWrapper(Main_Doanhthu.this, android.R.style.Theme_Holo_Light_Dialog);
                    try {
                        DatePickerDialog datePickerDialog = new Keys.FixedHoloDatePickerDialog(contextThemeWrapper, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                edEnd.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    } catch ( Fragment.InstantiationException e) {
                        e.printStackTrace();
                    }
                } else {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Doanhthu.this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            edEnd.setText(simpleDateFormat.format(calendar.getTime()));
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Doanhthu.this).show();
                else {
                    dateBegin = String.valueOf(edBengin.getText());
                    dateEnd = String.valueOf(edEnd.getText());
                    if (cbCasang.isChecked()) dateCasang = Keys.CA_SANG;
                    else dateCasang = "";
                    if (cbCachieu.isChecked()) dateCachieu = Keys.CA_CHIEU;
                    else dateCachieu = "";
                    new GetDT().execute();
                }
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Doanhthu.this).show();
                else {
                    Intent intent = new Intent(Main_Doanhthu.this, Main_Tao_BCDoanhthu.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("check", "0");
                    bundle.putParcelableArrayList("list", list);
                    intent.putExtra("DataDoanhthu", bundle);
                    startActivity(intent);
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

    class GetDT extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_Doanhthu.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_DOANHTHU+"?dateBegin="+dateBegin+"&dateEnd="+dateEnd+"&dateCasang="+dateCasang+"&dateCachieu="+dateCachieu);
            list.clear();
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DOANHTHU);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    list.add(new Doanhthu(
                                            object.getString("maDT"),
                                            object.getString("ngay"),
                                            object.getString("ca"),
                                            object.getString("chinhanh"),
                                            object.getString("maNV"),
                                            object.getString("tenNV"),
                                            object.getString("tiendauca"),
                                            object.getString("tientrave"),
                                            object.getString("doanhthu"),
                                            object.getString("lechcuoica"),
                                            object.getString("tienthucte")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            adapter.notifyDataSetChanged();
        }
    }
}