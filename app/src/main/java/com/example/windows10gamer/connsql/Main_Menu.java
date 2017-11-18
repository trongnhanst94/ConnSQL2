package com.example.windows10gamer.connsql;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Bao_Hanh.Main_Baohanh;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Report_BH;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;

public class Main_Menu extends AppCompatActivity {
    Button btnDanhsach, btnScan, btnSales, btnDanhsachkiemkho, btnListOrder, btnBaocaoDoanhthu,btnBaohanh,btnLogout,btnReportBaohanh;
    public static String session_username, shortName, session_ma, chinhanh;
    SharedPreferences shared;
    ArrayList<String> position;
    Button btnchinhanh;
    TextView tvchinhanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btnDanhsach        = (Button) findViewById(R.id.btnDanhsachsanpham);
        btnScan            = (Button) findViewById(R.id.btnScanQR);
        btnSales           = (Button) findViewById(R.id.btnSales);
        btnListOrder       = (Button) findViewById(R.id.btnListOrder);
        btnDanhsachkiemkho = (Button) findViewById(R.id.btnDanhsachkiemkho);
        btnBaocaoDoanhthu  = (Button) findViewById(R.id.btnBaocaoDoanhthu);
        btnBaohanh         = (Button) findViewById(R.id.btnBaohanh);
        btnReportBaohanh   = (Button) findViewById(R.id.btnReportBaohanh);
        btnLogout          = (Button) findViewById(R.id.btnLogout);
        btnchinhanh        = (Button) findViewById(R.id.btn_chinhanh);
        tvchinhanh         = (TextView) findViewById(R.id.tvchinhanh);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        tvchinhanh.setText(chinhanh);
        if (tvchinhanh.getText().equals("")){
            new Getvitri().execute();
        }
        position = new ArrayList<>();
        Intent intentget = getIntent();
        shortName = intentget.getStringExtra("shortName");
        btnchinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Getvitri().execute();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("tk", "");
                editor.putString("mk", "");
                editor.putString("ma", "");
                editor.putString("shortName", "");
                editor.putString("ten", "");
                editor.putBoolean("checked", FALSE);
                editor.putBoolean("isLogged", FALSE);
                editor.commit();
                new CustomToast().Show_Toast(Main_Menu.this, findViewById(android.R.id.content), "Đăng xuất thành công!");
                Intent intent = new Intent(Main_Menu.this, Main_Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnReportBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentget = getIntent();
                session_username = intentget.getStringExtra("session_username");
                session_ma = intentget.getStringExtra("session_ma");
                Intent intentput = new Intent(Main_Menu.this, Main_Report_BH.class);
                intentput.putExtra("session_username", session_username);
                startActivity(intentput);
            }
        });

        btnDanhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentget = getIntent();
                session_username = intentget.getStringExtra("session_username");
                session_ma = intentget.getStringExtra("session_ma");
                Intent intentput = new Intent(Main_Menu.this, Main_List_Excel.class);
                intentput.putExtra("session_username", session_username);
                startActivity(intentput);
            }
        });

        btnBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentget = getIntent();
                session_username = intentget.getStringExtra("session_username");
                session_ma = intentget.getStringExtra("session_ma");
                Intent intentput = new Intent(Main_Menu.this, Main_Baohanh.class);
                intentput.putExtra("session_username", session_username);
                startActivity(intentput);
            }
        });

        btnBaocaoDoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentget = getIntent();
                session_username = intentget.getStringExtra("session_username");
                session_ma = intentget.getStringExtra("session_ma");
                Intent intentput = new Intent(Main_Menu.this, Main_Baocao_Doanhthu.class);
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

    class Getvitri extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jIndex = 0;
            dialog = new ProgressDialog(Main_Menu.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_MENU_DSCH);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DANHSACHCUAHANG);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    position.add(
                                            object.getString("cuahang")
                                    );
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } else {

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
            setLisst(position);
        }
    }

    private void setLisst(ArrayList<String> position) {
        this.position = position;
        AlertDialog.Builder dialog = new AlertDialog.Builder(Main_Menu.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        dialog.setTitle("Chọn cửa hàng cần báo cáo");
        dialog.setCancelable(false);
        final Spinner spinner = (Spinner) mView.findViewById(R.id.spinnerKM);
        ArrayAdapter mAdapter = new ArrayAdapter<>(Main_Menu.this, android.R.layout.simple_spinner_item, position);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chinhanh = String.valueOf(spinner.getSelectedItem());
                tvchinhanh.setText(chinhanh);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("chinhanh", chinhanh);
                editor.commit();
            }
        });
        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(mView);
        AlertDialog al = dialog.create();
        al.show();
    }
}
