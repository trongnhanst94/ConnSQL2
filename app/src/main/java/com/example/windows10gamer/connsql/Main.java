package com.example.windows10gamer.connsql;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button btnDanhsach, btnScan, btnSales, btnDanhsachkiemkho, btnListOrder, btnBaocaoDoanhthu,btnBaohanh,
            btnLogout,btnReportBaohanh;
    public static String session_username, shortName, session_ma, chinhanh;
    SharedPreferences shared;
    ArrayList<String> position;
    Button btnchinhanh;
    TextView tvchinhanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vi = inflater.inflate(R.layout.nav_header_main, null); //log.xml is your file.
        TextView tvshortName = (TextView)vi.findViewById(R.id.shortName);


//        LayoutInflater factory = getLayoutInflater();
//        View regisText = factory.inflate(R.layout.nav_header_main, null);
//        TextView tvshortName = (TextView) regisText.findViewById(R.id.shortName);
//        TextView tvsession_ma = (TextView) regisText.findViewById(R.id.session_ma);
        tvshortName.setText(shortName);
//        tvsession_ma.setText(session_ma);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        btnReportBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentget = getIntent();
                session_username = intentget.getStringExtra("session_username");
                session_ma = intentget.getStringExtra("session_ma");
                Intent intentput = new Intent(Main.this, Main_Report_BH.class);
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
                Intent intentput = new Intent(Main.this, Main_List_Excel.class);
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
                Intent intentput = new Intent(Main.this, Main_Baohanh.class);
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
                Intent intentput = new Intent(Main.this, Main_Baocao_Doanhthu.class);
                intentput.putExtra("session_username", session_username);
                startActivity(intentput);
            }
        });

        btnListOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentget = getIntent();
                session_username = intentget.getStringExtra("session_username");
                Intent intentput = new Intent(Main.this, Main_Order.class);
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
                Intent intentput = new Intent(Main.this, Main_Kiemkho.class);
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
                Intent intentput = new Intent(Main.this, Main_Ketqua_Kiemkho.class);
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
                Intent intentput = new Intent(Main.this, Main_Sales.class);
                intentput.putExtra("session_username", session_username);
                intentput.putExtra("session_ma", session_ma);
                startActivity(intentput);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
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
            new CustomToast().Show_Toast(Main.this, findViewById(android.R.id.content), "Đăng xuất thành công!");
            Intent intent = new Intent(Main.this, Main_Login.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Getvitri extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jIndex = 0;
            dialog = new ProgressDialog(Main.this);
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
        AlertDialog.Builder dialog = new AlertDialog.Builder(Main.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        dialog.setTitle("Chọn cửa hàng cần báo cáo");
        dialog.setCancelable(false);
        final Spinner spinner = (Spinner) mView.findViewById(R.id.spinnerKM);
        ArrayAdapter mAdapter = new ArrayAdapter<>(Main.this, android.R.layout.simple_spinner_item, position);
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
