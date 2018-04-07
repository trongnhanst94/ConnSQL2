package com.example.windows10gamer.connsql;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.windows10gamer.connsql.Adapter.Adapter_Icon;
import com.example.windows10gamer.connsql.Ban_Hang.Main_Doanhthu;
import com.example.windows10gamer.connsql.Ban_Hang.Main_Order;
import com.example.windows10gamer.connsql.Ban_Hang.Main_Realtime_Order;
import com.example.windows10gamer.connsql.Ban_Hang.Main_Sales;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Baohanh;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Report_BH;
import com.example.windows10gamer.connsql.Khach_Hang.Main_Dat_Coc;
import com.example.windows10gamer.connsql.Kho.Main_KhoOnline;
import com.example.windows10gamer.connsql.Khoan_Chi.Main_Khoan_Chi;
import com.example.windows10gamer.connsql.Khoan_Chi.Main_PhiCOD;
import com.example.windows10gamer.connsql.Kiem_Kho.Main_Ketqua_Kiemkho;
import com.example.windows10gamer.connsql.Kiem_Kho.Main_Kiemkho;
import com.example.windows10gamer.connsql.Object.Chitieu_Nhanvien;
import com.example.windows10gamer.connsql.Object.Icon;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.Version;
import com.example.windows10gamer.connsql.Other.APIService_Sales;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.OrderList;
import com.example.windows10gamer.connsql.Other.RetrofitClient;
import com.example.windows10gamer.connsql.Remove_Data.Main_Remove_Data;
import com.example.windows10gamer.connsql.Settings.Main_Danhmuc;
import com.example.windows10gamer.connsql.Tra_Ve.Main_TienTraVe;
import com.example.windows10gamer.connsql.Xuat_Nhap.Main_XuatNhap;
import com.google.firebase.messaging.FirebaseMessaging;

import net.gotev.uploadservice.MultipartUploadRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

import static java.lang.Boolean.FALSE;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Adapter_Icon.OnCallBack {
    Button btnWeb, btnScan, btnSales, btnDanhsachkiemkho, btnListOrder, btnXuatnhap,btnBaohanh,
            btnChi,btnReportBaohanh,btnRealtime, btndatcoc, btnnhanvien, btnBcdt, btnremove, btnnhaphang, btnscanvon, btnphicod, btntientrave;
    public static String session_username, shortName, session_ma, chinhanh, level, mkhau;
    SharedPreferences shared;
    ArrayList<String> position;
    TextView tvchinhanh;
    private ProgressDialog progress;
    ImageView ivAvatar = null;
    public static final int RESULT_LOAD_IMAGE = 3;
    private ProgressDialog dialog;
    ProgressDialog dialogbe;
    int totalDoanhthu = 0;
    ArrayList<Order> myListAll =  new ArrayList<>();
    ArrayList<Order> mySing =  new ArrayList<>();
    TextView mdoanhthu, khachhang, sanpham;
    private ArrayList<Chitieu_Nhanvien> listChitieu = new ArrayList<>();
    TextView tvsoca, tvtangca, tvdt, tvdtkh, tvspkh, tvthang, tvluykeluong;
    Integer intsoca = 0;
    Float inttangca;
    Integer intdoanhthutrenkhach;
    Double intsanphamtrenkhach;
    Integer intchitieu = 1;
    Integer intdoanhthu = 1;
    Integer inttotalsoca = 1;
    Integer kqdoanhthu = 1;
    Integer kqsp = 1;
    Integer kqkh = 1;
    Integer pdt = 1;
    Integer pdtkh = 1;
    Integer pspdt;
    TextView ptdt, ptdtkh, ptspkh;
    LinearLayout lnHide, btnHide;
    ImageView ivHide;
    private ArrayList<String> listDica = new ArrayList<>();
    TextView tvdica, ptdica;
    private int pdica = 0;
    private int ptrdica = 0;
    private int PICK_IMAGE_REQUEST = 101;
    private Bitmap bitmap;
    View headerView;
    NavigationView navigationView;
    ImageView test;
    private ProgressDialog progressDialog;
    boolean check = true;
    private Uri filePath;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private String img;
    private String linkAvatar;
    private ArrayList<Version> listVersion = new ArrayList<>();
    private String NOW_VERSION, getVersion = "";
    private ArrayList<String> listsoluong = new ArrayList<>();
    private RecyclerView rvMain;
    ArrayList<Icon> iconArrayAdapter = new ArrayList<Icon>();
    Adapter_Icon adapterIcon;
    private ArrayList<Icon> listMaster = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifecycle","onCreate 1");
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        requestStoragePermission();
        Toolbar toolbar = findViewById(R.id.toolbar);
        anhxa();
        firebase();
        mdoanhthu = findViewById(R.id.mdoanhthu);
        khachhang = findViewById(R.id.khachhang);
        sanpham = findViewById(R.id.sanpham);
        setSupportActionBar(toolbar);
        shared = getSharedPreferences("login", MODE_PRIVATE);
        shortName = shared.getString("ten", "");
        session_ma = shared.getString("ma", "");
        mkhau = shared.getString("mk", "");
        level = shared.getString("level", "");
        img = shared.getString("img", "");
        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        TextView tvshortName = headerView.findViewById(R.id.shortName);
        TextView tvsession_ma = headerView.findViewById(R.id.session_ma);
        TextView tvVersion = headerView.findViewById(R.id.tvVersion);
        ImageView ivAvatar = navigationView.getHeaderView(0).findViewById(R.id.anhdaidien);
        Glide.with(Main.this).load(img).override(300,300).fitCenter().into(ivAvatar);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });
        dialogbe = new ProgressDialog(Main.this);
        dialogbe.setTitle("Hãy chờ...");
        dialogbe.setMessage("Dữ liệu đang được tải xuống");
        dialogbe.setCancelable(false);
        dialogbe.show();
        new GetListSoluong().execute();
        new SendRequest().execute();
        new Getversion().execute();
        tvshortName.setText(shortName);
        tvsession_ma.setText(session_ma);
        tvVersion.setText("Phiên bản hiện tại: "+Keys.NOW_VERSION);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        lnHide.setVisibility(View.GONE);
        btnHide.setVisibility(View.GONE);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        tvchinhanh.setText(chinhanh);
        if (chinhanh.equals("")){
            new Getvitri().execute();
        }
        position = new ArrayList<>();
        Intent intentget = getIntent();
        shortName = intentget.getStringExtra("shortName");
        rvMain.setHasFixedSize(true);
        rvMain.setLayoutManager(new GridLayoutManager(this, 3));
        adapterIcon = new Adapter_Icon(Main.this,this, iconArrayAdapter);
        rvMain.setAdapter(adapterIcon);

        btnReportBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else {
                    startActivity(new Intent(Main.this, Main_Report_BH.class));
                }
            }
        });

        btnphicod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_PhiCOD.class));
            }
        });

        btnnhaphang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_KhoOnline.class));
            }
        });
        final int[] hide = {1};
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hide[0] == 1){
                    lnHide.setVisibility(View.GONE);
                    hide[0] = 0;
                    ivHide.setImageResource(R.drawable.ic_down_arrow);
                } else {
                    lnHide.setVisibility(View.VISIBLE);
                    hide[0] = 1;
                    ivHide.setImageResource(R.drawable.ic_up_arrow);
                }
            }
        });

        btnscanvon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(level) <= Keys.LEVEL_KHO){
                    if(!Connect_Internet.checkConnection(getApplicationContext()))
                        Connect_Internet.buildDialog(Main.this).show();
                    else startActivity(new Intent(Main.this, Main_ScanVon.class));
                } else {
                    AlertDialog.Builder builder = null;
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        builder = new AlertDialog.Builder(Main.this);
                    } else {
                        builder = new AlertDialog.Builder(Main.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Cảnh báo");
                    builder.setIcon(R.drawable.ic_warning);
                    builder.setMessage("Bạn không có quyền truy cập. Nhấn Xác nhận để thoát!");
                    builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });

        btnRealtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_Realtime_Order.class));
            }
        });

        btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(level) <= Keys.LEVEL_QL){
                    if(!Connect_Internet.checkConnection(getApplicationContext()))
                        Connect_Internet.buildDialog(Main.this).show();
                    else startActivity(new Intent(Main.this, Main_Remove_Data.class));
                } else {
                    AlertDialog.Builder builder = null;
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        builder = new AlertDialog.Builder(Main.this);
                    } else {
                        builder = new AlertDialog.Builder(Main.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Cảnh báo");
                    builder.setIcon(R.drawable.ic_warning);
                    builder.setMessage("Bạn không có quyền truy cập. Nhấn Xác nhận để thoát!");
                    builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }

            }
        });

        btnBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                   Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_Baohanh.class));
            }
        });

        btnBcdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_Doanhthu.class));
            }
        });

        btnListOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_Order.class));
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_Kiemkho.class));
            }
        });

        btnDanhsachkiemkho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_Ketqua_Kiemkho.class));
            }
        });

        btnSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_Sales.class));
            }
        });

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_Website.class));
            }
        });

        btnXuatnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_XuatNhap.class));
            }
        });

        btnChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_Khoan_Chi.class));
            }
        });

        btntientrave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else startActivity(new Intent(Main.this, Main_TienTraVe.class));
            }
        });

        btndatcoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else {
                    startActivity(new Intent(Main.this, Main_Dat_Coc.class));
                }
            }
        });

        tvchinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Getvitri().execute();
            }
        });
    }

    @Override
    public void ItemClicked(int positon) {
        if(!Connect_Internet.checkConnection(getApplicationContext()))
            Connect_Internet.buildDialog(Main.this).show();
        else {
            try {
                startActivity(new Intent(Main.this, Class.forName(getPackageName()+iconArrayAdapter.get(positon).getContext())));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    class GetListSoluong extends AsyncTask<Void, Integer, String> {
        int jIndex;

        @Override
        protected void onPreExecute() {

        }

        @Nullable
        @Override
        protected String doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_LISTSOLUONG);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.LISTSOLUONG);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    listsoluong.add(object.getString("masanpham"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                publishProgress(jIndex);
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
        protected void onPostExecute(String s) {
        }
    }

    private void firebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "1";
        String channel2 = "2";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    "Channel 1",NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("This is BNT");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationChannel notificationChannel2 = new NotificationChannel(channel2,
                    "Channel 2",NotificationManager.IMPORTANCE_MIN);

            notificationChannel.setDescription("This is bTV");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel2);

        }
    }

    private void anhxa() {
        rvMain             = findViewById(R.id.rvMain);
        btntientrave       = findViewById(R.id.btntientrave);
        btnphicod          = findViewById(R.id.btnphicod);
        ptdica             = findViewById(R.id.ptdica);
        tvthang            = findViewById(R.id.tvthang);
        tvdica             = findViewById(R.id.tvdica);
        tvluykeluong       = findViewById(R.id.tvluykeluong);
        ivHide             = findViewById(R.id.ivHide);
        lnHide             = findViewById(R.id.lnHide);
        btnHide            = findViewById(R.id.btnHide);
        tvsoca             = findViewById(R.id.tvsoca);
        tvtangca           = findViewById(R.id.tvtangca);
        tvdt               = findViewById(R.id.tvdt);
        tvdtkh             = findViewById(R.id.tvdtkh);
        tvspkh             = findViewById(R.id.tvspkh);
        btnnhaphang        = findViewById(R.id.btnnhaphang);
        btnremove          = findViewById(R.id.btnremove);
        btnscanvon         = findViewById(R.id.btnscanvon);
        btnBcdt            = findViewById(R.id.btnBcdt);
        btnWeb             = findViewById(R.id.btnWeb);
        btnnhanvien        = findViewById(R.id.btnnhanvien);
        btndatcoc          = findViewById(R.id.btndatcoc);
        btnXuatnhap        = findViewById(R.id.btnXuatnhap);
        btnScan            = findViewById(R.id.btnScanQR);
        btnRealtime        = findViewById(R.id.btnRealtime);
        btnSales           = findViewById(R.id.btnSales);
        btnListOrder       = findViewById(R.id.btnListOrder);
        btnDanhsachkiemkho = findViewById(R.id.btnDanhsachkiemkho);
        btnBaohanh         = findViewById(R.id.btnBaohanh);
        btnReportBaohanh   = findViewById(R.id.btnReportBaohanh);
        btnChi             = findViewById(R.id.btnChi);
        tvchinhanh         = findViewById(R.id.tvchinhanh);
        ptdt               = findViewById(R.id.ptdt);
        ptdtkh             = findViewById(R.id.ptdtkh);
        ptspkh             = findViewById(R.id.ptspkh);
    }

    public class SendRequest extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(Void... arg0) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main.this).show();
            else {
                GetDonhang();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    public void GetDonhang() {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            APIService_Sales api = RetrofitClient.getApiService();
            Call<OrderList> call = api.getOrder(Keys.getDauthang(), Keys.getDateNow());
            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, retrofit2.Response<OrderList> response) {
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    if(response.isSuccessful()) {
                        mySing.clear();
                        myListAll.clear();
                        totalDoanhthu = 0;
                        orignal = response.body().getContacts();
                        for (int i = 0; i < orignal.size(); i++) {
                            int ss = checklist(orignal.get(i).getMaSanpham(), listsoluong);
                            if (session_ma.equals(orignal.get(i).getMaNhanvien())){
                                if (ss == -1){
                                    myListAll.add(orignal.get(i));
                                    totalDoanhthu += (Integer.valueOf(orignal.get(i).getGiaSanpham()) - Integer.valueOf(orignal.get(i).getGiamgia()));
                                    int sosanhgiamgia = sosanhgiam(mySing, orignal.get(i).getMaDonhang());
                                    if (sosanhgiamgia == -1) {
                                        mySing.add(orignal.get(i));
                                    }
                                }
                            }
                        }
                        mdoanhthu.setText(Keys.setMoney(totalDoanhthu));
                        sanpham.setText(myListAll.size()+"");
                        khachhang.setText(mySing.size()+"");
                        kqdoanhthu = totalDoanhthu;
                        kqkh = mySing.size();
                        kqsp = myListAll.size();
                        if (kqkh == 0) kqkh = -1;
                        if (kqdoanhthu == 0) kqdoanhthu = -1;
                        if (kqsp == 0) kqsp = -1;
                        new getDica().execute();
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                }
            });
        }
    }

    private int checklist(String maSanpham, ArrayList<String> list) {
        int result = -1;
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                if (maSanpham.equals(list.get(i))){
                    result = i;
                }
            }
        }
        return result;
    }

    private int sosanhgiam(ArrayList<Order> tempgiamgia, String maDonhang) {
        int result = -1;
        if (tempgiamgia.size() != 0){
            for (int i = 0; i < tempgiamgia.size(); i++){
                if (tempgiamgia.get(i).getMaDonhang().equals(maDonhang)){
                    result = i;
                }
            }
        }
        return result;
    }

    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh đại diện"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImageView ivAvatar = navigationView.getHeaderView(0).findViewById(R.id.anhdaidien);
                Glide.with(Main.this).load(filePath).into(ivAvatar);
                uploadMultipart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_giamgia) {
            if (Integer.valueOf(level) <= Keys.LEVEL_QL){
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else {
                    startActivity(new Intent(Main.this, Main_MaGiamGia.class));
                }
            } else {
                AlertDialog.Builder builder = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    builder = new AlertDialog.Builder(Main.this);
                } else {
                    builder = new AlertDialog.Builder(Main.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                }
                builder.setTitle("Cảnh báo");
                builder.setIcon(R.drawable.ic_warning);
                builder.setMessage("Bạn không có quyền truy cập. Nhấn Xác nhận để thoát!");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        } else if (id == R.id.nav_danhmuc) {
            if (Integer.valueOf(level) <= Keys.LEVEL_QL){
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else {
                    startActivity(new Intent(Main.this, Main_Danhmuc.class));
                }
            } else {
                AlertDialog.Builder builder = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    builder = new AlertDialog.Builder(Main.this);
                } else {
                    builder = new AlertDialog.Builder(Main.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                }
                builder.setTitle("Cảnh báo");
                builder.setIcon(R.drawable.ic_warning);
                builder.setMessage("Bạn không có quyền truy cập. Nhấn Xác nhận để thoát!");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        } else if (id == R.id.nav_logout) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main.this).show();
            else {
                logout();
            }
        } else if (id == R.id.nav_doimatkhau) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main.this).show();
            else {
                Alert_Pass();
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        shared = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("tk", "");
        editor.putString("mk", "");
        editor.putString("ma", "");
        editor.putString("shortName", "");
        editor.putString("ten", "");
        editor.putString("level", "");
        editor.putString("chucdanh", "");
        editor.putString("img", "");
        editor.putBoolean("checked", FALSE);
        editor.putBoolean("isLogged", FALSE);
        editor.commit();
        Intent intent = new Intent(Main.this, Main_Login.class);
        startActivity(intent);
        finish();
    }

    private void Alert_Pass() {
        AlertDialog.Builder dialog = null;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            dialog = new AlertDialog.Builder(Main.this);
        } else {
            dialog = new AlertDialog.Builder(Main.this, android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth);
        }
        dialog.setIcon(R.drawable.ic_settings);
        View mView = getLayoutInflater().inflate(R.layout.dialog_changepass, null);
        dialog.setTitle("Đổi mật khẩu");
        dialog.setCancelable(true);
        final EditText ednowpass = mView.findViewById(R.id.ednowpass);
        final EditText edpass1 = mView.findViewById(R.id.edpass1);
        final EditText edpass2 = mView.findViewById(R.id.edpass2);
        final TextView tvpass = mView.findViewById(R.id.tvpass);
        final Button btnpass = mView.findViewById(R.id.btnpass);
        ednowpass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (ednowpass.getText().toString().trim().equals(mkhau)){
                    edpass1.setVisibility(View.VISIBLE);
                    edpass2.setVisibility(View.VISIBLE);
                    ednowpass.setEnabled(false);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        edpass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                btnpass.setVisibility(View.GONE);
                edpass2.setText("");
            }
        });
        edpass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (edpass2.getText().toString().trim().equals(edpass1.getText().toString().trim())){
                    if (edpass2.getText().toString().trim().length() < 5 || edpass2.getText().toString().trim().length() > 20){
                        tvpass.setText("Mật khẩu quá ngắn");
                        btnpass.setVisibility(View.GONE);
                    } else {
                        btnpass.setVisibility(View.VISIBLE);
                        tvpass.setText("");
                    }
                }
            }
        });
        btnpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main.this).show();
                else {
                    progress = ProgressDialog.show(Main.this, "Hãy chờ", "Đang thay đổi mật khẩu...", true);
                    ChangePass(edpass2.getText().toString().trim());
                }
            }
        });
        dialog.setView(mView);
        AlertDialog al = dialog.create();
        al.show();
    }

    public void ChangePass(final String pass){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toasty.success(Main.this, "Đổi mật khẩu thành công", Toast.LENGTH_LONG, true).show();
                            progress.dismiss();
                            logout();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main.this, "Không kết nối được Server!!", Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.CHANGE_PASSWORD);
                params.put("session_ma", session_ma);
                params.put("changepass", Keys.md5(pass));
                Log.e("params",  params.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void uploadMultipart() {
        String path = getPath(filePath);
        try {
            String uploadId = UUID.randomUUID().toString();
            String nn = Keys.TaoMa();
            new MultipartUploadRequest(this, uploadId, Keys.UPLOAD_AVATAR)
                    .addFileToUpload(path, "image")
                    .addParameter("session_ma", session_ma)
                    .addParameter("rand", nn)
                    .setMaxRetries(2)
                    .startUpload();
            linkAvatar = Keys.LINK_AVATAR+session_ma+nn+".jpg";
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("img", linkAvatar);
            editor.commit();

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
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
                            position.clear();
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

    class Getversion extends AsyncTask<Void, Void, Void> {
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
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_VERSION);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.VERSION);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            listVersion.clear();
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    listVersion.add(new Version(
                                            object.getString("id"),
                                            object.getString("ten"),
                                            object.getString("mota"),
                                            object.getString("created")
                                            )
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
            getVersion = listVersion.get(0).getTen();
            setVerrr(getVersion);
        }
    }

    class GetMaster extends AsyncTask<Void, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_MASTER);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.MASTER);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            iconArrayAdapter.clear();
                            for(jIndex = 0 ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (object.getInt("level") >=  Integer.valueOf(level)){
                                        iconArrayAdapter.add(new Icon(
                                                        object.getString("name"),
                                                        Keys.getDrawable(Main.this, object.getString("link")),
                                                        Keys.getDrawable(Main.this, object.getString("color")),
                                                        object.getString("context"),
                                                        object.getInt("level")
                                                )
                                        );
                                    }

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
            adapterIcon.notifyDataSetChanged();
        }
    }

    private void setVerrr(String getVersion) {
        if (!getVersion.equals(Keys.NOW_VERSION)){
            AlertDialog.Builder builder = null;
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                builder = new AlertDialog.Builder(Main.this);
            } else {
                builder = new AlertDialog.Builder(Main.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
            }
            builder.setIcon(R.drawable.ic_warning);
            builder.setTitle("Cảnh báo");
            builder.setCancelable(false);
            builder.setMessage("Yêu cầu người dùng cập nhật phiên bản mới nhất trước khi sử dụng!");
            builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    System.exit(0);
                }
            });
            builder.show();
        }
    }

    class getDica extends AsyncTask<String, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.DANHSACHDICA+"?batdau="+Keys.getDauthang()+"&ketthuc="+Keys.getDateNow()+"&nhanvien="+session_ma);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DI_CA);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            listDica.clear();
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                        listDica.add(object.getString("tennhanvien"));
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
            pdica = listDica.size();
            tvdica.setText(pdica+"");
            new GetChitieu().execute();
        }
    }

    class GetChitieu extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jIndex = 0;
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_CHITIEU_NV);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.CHITIEU_NHANVIEN);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            listChitieu.clear();
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (object.getString("manhanvien").equals(session_ma)){
                                        listChitieu.add(new Chitieu_Nhanvien(
                                                        object.getString("id"),
                                                        object.getString("manhanvien"),
                                                        object.getString("tennhanvien"),
                                                        object.getString("soca"),
                                                        object.getString("tangca"),
                                                        object.getString("chitieu"),
                                                        object.getString("doanhthutrenkhach"),
                                                        object.getString("sanphamtrenkhach"),
                                                        object.getString("thang"),
                                                        object.getString("luykeluong")
                                                )

                                        );
                                    }
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
            if (listChitieu.size() > 0){
                lnHide.setVisibility(View.VISIBLE);
                btnHide.setVisibility(View.VISIBLE);
                tvthang.setText(listChitieu.get(0).getThang()+"");
                tvluykeluong.setText(Keys.setMoney(Integer.valueOf(listChitieu.get(0).getLuykeluong())));
                if (kqdoanhthu == -1 || kqsp == -1 || kqkh == -1){
                } else {
                    DecimalFormat df = new DecimalFormat("0.0");
                    DecimalFormat pf = new DecimalFormat("0");
                    intsoca = Integer.valueOf(listChitieu.get(0).getSoca());
                    inttangca = Float.valueOf(listChitieu.get(0).getTangca());
                    intchitieu = Integer.valueOf(listChitieu.get(0).getChitieu());
                    intdoanhthutrenkhach = Integer.valueOf(listChitieu.get(0).getDoanhthutrenkhach());
                    intsanphamtrenkhach = Double.valueOf(listChitieu.get(0).getSanphamtrenkhach());
                    intdoanhthu = Keys.getTotalSoca(intsoca, inttangca, intchitieu);
                    tvdt.setText(Keys.setMoney(kqdoanhthu) + " | " + Keys.setMoney(intdoanhthu));
                    tvsoca.setText(pdica + " | " +String.valueOf(intsoca));
                    tvtangca.setText(String.valueOf(inttangca));
                    tvdtkh.setText(Keys.setMoney(kqdoanhthu / kqkh) + " | " + Keys.setMoneyFloat(intdoanhthutrenkhach));
                    tvspkh.setText(df.format(Double.valueOf(kqsp) / Double.valueOf(kqkh)) + " | " + df.format(intsanphamtrenkhach));
                    ptrdica = (pdica) * 100 / intsoca;
                    pdt = ((kqdoanhthu / 1000) / ((intdoanhthu / 100) / 1000));
                    pdtkh = (kqdoanhthu / kqkh) * 100 / intdoanhthutrenkhach;
                    pspdt = Integer.valueOf(pf.format(Double.valueOf(kqsp) / Double.valueOf(kqkh) * 100 / intsanphamtrenkhach));
                    if (ptrdica < 50){
                        ptdica.setTextColor(getResources().getColor(R.color.RED));
                    } else if (ptrdica > 50 && ptrdica < 90){
                        ptdica.setTextColor(getResources().getColor(R.color.holo_cam));
                    } else ptdica.setTextColor(getResources().getColor(R.color.holo_xanh));
                    if (pdt < 50){
                        ptdt.setTextColor(getResources().getColor(R.color.RED));
                    } else if (pdt > 50 && pdt < 90){
                        ptdt.setTextColor(getResources().getColor(R.color.holo_cam));
                    } else ptdt.setTextColor(getResources().getColor(R.color.holo_xanh));
                    if (pdtkh < 50){
                        ptdtkh.setTextColor(getResources().getColor(R.color.RED));
                    } else if (pdtkh > 50 && pdtkh < 90){
                        ptdtkh.setTextColor(getResources().getColor(R.color.holo_cam));
                    } else ptdtkh.setTextColor(getResources().getColor(R.color.holo_xanh));
                    if (pspdt < 50){
                        ptspkh.setTextColor(getResources().getColor(R.color.RED));
                    } else if (pspdt > 50 && pspdt < 90){
                        ptspkh.setTextColor(getResources().getColor(R.color.holo_cam));
                    } else ptspkh.setTextColor(getResources().getColor(R.color.holo_xanh));
                    ptdica.setText(ptrdica + "%");
                    ptdt.setText(pdt + "%");
                    ptdtkh.setText(pdtkh + "%");
                    ptspkh.setText(pspdt + "%");
                }
            }
            dialogbe.dismiss();
            new GetMaster().execute();
        }
    }

    private void setLisst(ArrayList<String> position) {
        if(!Connect_Internet.checkConnection(getApplicationContext()))
            Connect_Internet.buildDialog(Main.this).show();
        else {
            this.position = position;
            AlertDialog.Builder dialog = null;
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                dialog = new AlertDialog.Builder(Main.this);
            } else {
                dialog = new AlertDialog.Builder(Main.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
            }
            dialog.setIcon(R.drawable.ic_settings)
                    .setTitle("Chọn cửa hàng?");
            View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
            dialog.setCancelable(false);
            final Spinner spinner = mView.findViewById(R.id.spinnerKM);
            ArrayAdapter mAdapter = new ArrayAdapter<>(Main.this, android.R.layout.simple_spinner_item, position);
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(null);
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




    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle","onStart 1");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume 1");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause 1");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle","onStop 1");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle","onRestart 1");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy 1");
    }
}
