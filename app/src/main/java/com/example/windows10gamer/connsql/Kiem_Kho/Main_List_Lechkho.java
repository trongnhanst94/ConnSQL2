package com.example.windows10gamer.connsql.Kiem_Kho;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.CountSanpham;
import com.example.windows10gamer.connsql.Object.CountSanpham2;
import com.example.windows10gamer.connsql.Object.Kiemkho;
import com.example.windows10gamer.connsql.Object.Lechkho;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class Main_List_Lechkho extends AppCompatActivity {
    ArrayList<CountSanpham> giong, khac, ga, gb, fullga, fullgb, showa, showb, all;
    ArrayList<CountSanpham2> fullga2, fullgb2, fullgiong;
    ArrayList<CountSanpham> dem, demA, demB;
    String nameUserA, nameUserB, chinhanh, kho, tentrang = "", snUserA, snUserB;
    TableLayout stk;
    CheckBox cbGiong, cbKhac;
    Button btnLoc;
    ArrayList<Kiemkho> donhang;
    ArrayList<Lechkho> lechkhoArrayList = new ArrayList<>();
    ProgressDialog dialog, dialog2;
    FloatingActionButton fabBCKK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_lechkho);
        cbGiong = (CheckBox) findViewById(R.id.cbGiongLechkho);
        cbKhac  = (CheckBox) findViewById(R.id.cbKhacLechkho);
        btnLoc  = (Button)   findViewById(R.id.btnLocLechkho);
        fabBCKK = (FloatingActionButton) findViewById(R.id.fabBCKK);
        donhang = new ArrayList<>();
        giong = new ArrayList<>();
        khac = new ArrayList<>();
        ga = new ArrayList<>();
        gb = new ArrayList<>();
        fullga = new ArrayList<>();
        fullgb = new ArrayList<>();
        fullga2 = new ArrayList<>();
        fullgb2 = new ArrayList<>();
        fullgiong = new ArrayList<>();
        showa = new ArrayList<>();
        showb = new ArrayList<>();
        dem = new ArrayList<>();
        demA = new ArrayList<>();
        demB = new ArrayList<>();
        all = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("LechkhoBundle");
        nameUserA = bundle.getString("nameUserA");
        nameUserB = bundle.getString("nameUserB");
        snUserA = bundle.getString("snUserA");
        snUserB = bundle.getString("snUserB");
        chinhanh = bundle.getString("chinhanh");
        kho = bundle.getString("kho");
        new GetDataKho().execute();
        fabBCKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_List_Lechkho.this).show();
                else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Main_List_Lechkho.this);
                    dialog.setMessage("Bạn có chắc muốn báo cáo?");
                    View mView = getLayoutInflater().inflate(R.layout.spinner_lk, null);
                    final EditText input = (EditText) mView.findViewById(R.id.input);
                    dialog.setView(input);
                    input.setText(Keys.getDateNow() + "_" + kho + "_" + chinhanh);
                    dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (input.getText().toString().trim().equals("")) {
                                new CustomToast().Show_Toast(Main_List_Lechkho.this, findViewById(android.R.id.content), "Không được để trống tên trang!");
                            } else {
                                tentrang = input.getText().toString().trim();
                                addList(fullga2, fullgb2);
                            }
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
        });

        cbKhac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    cbGiong.setChecked(true);
                }
            }
        });
        cbGiong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    cbKhac.setChecked(true);
                }
            }
        });
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_List_Lechkho.this).show();
                else {
                    stk.removeAllViews();
                    if (cbGiong.isChecked()) {
                        if (cbKhac.isChecked()) {
                            sortList(fullga2);
                            sortList(fullgb2);
                            init(fullga2, fullgb2);
                        } else {
                            sortList(fullgiong);
                            init(fullgiong, fullgiong);
                        }
                    } else {
                        if (cbKhac.isChecked()) {
                            sortList(fullga2);
                            sortList(fullgb2);
                            ArrayList<CountSanpham2> tamA, tamB;
                            tamA = new ArrayList<>();
                            tamB = new ArrayList<>();
                            tamA.addAll(fullga2);
                            tamB.addAll(fullgb2);
                            for (int i = 0; i < fullgiong.size(); i++) {
                                for (int j = 0; j < tamA.size(); j++) {
                                    if ((fullgiong.get(i).getMa()).equals(tamB.get(j).getMa()) && (fullgiong.get(i).getSoluong() == tamB.get(j).getSoluong())) {
                                        tamA.remove(j);
                                        tamB.remove(j);
                                    }
                                }
                            }
                            init(tamA, tamB);
                        } else {
                            new CustomToast().Show_Toast(Main_List_Lechkho.this, findViewById(android.R.id.content), "Không có dữ liệu!");
                        }
                    }
                }
            }
        });
    }


    private void addList(ArrayList<CountSanpham2> fullga2, ArrayList<CountSanpham2> fullgb2) {
        lechkhoArrayList.clear();
        lechkhoArrayList.add(new Lechkho("STT", "Mã SP", "Tên sản phẩm", "Bảo hành", "Nguồn", "Ngày nhập", "Vốn", "Giá", nameUserA, nameUserB));
        int dem = 1;
        for (int i = 0; i < fullga.size(); i++) {
            if (fullga.get(i).getSoluong() == fullgb.get(i).getSoluong()){
            lechkhoArrayList.add(new Lechkho(1+"", fullga2.get(i).getMa(), fullga2.get(i).getTenSanpham(), fullga2.get(i).getBaohanh(), fullga2.get(i).getNguon(), fullga2.get(i).getNgaynhap(), fullga2.get(i).getVon(), fullga2.get(i).getGia(),fullga2.get(i).getSoluong()+"", fullgb2.get(i).getSoluong()+""));
            dem++;
            }
        }
        for (int i = 0; i < fullgb.size(); i++) {
            if (fullga.get(i).getSoluong() != fullgb.get(i).getSoluong()){
                lechkhoArrayList.add(new Lechkho(1+"", fullgb2.get(i).getMa(), fullgb2.get(i).getTenSanpham(), fullgb2.get(i).getBaohanh(), fullgb2.get(i).getNguon(), fullgb2.get(i).getNgaynhap(), fullgb2.get(i).getVon(), fullgb2.get(i).getGia(),fullga2.get(i).getSoluong()+"", fullgb2.get(i).getSoluong()+""));
                dem++;
            }
        }
        new SendRequest().execute();
    }

    private ArrayList<CountSanpham2> iniTen(ArrayList<Kiemkho> donhang, ArrayList<CountSanpham> list) {
        ArrayList<CountSanpham2> full = new ArrayList<>();
        for (int i = 0; i<list.size(); i++){
            int result = sosanh2(donhang, list.get(i).getMasanpham());
            if (result != -1){
                full.add(new CountSanpham2(list.get(i).getMasanpham(), donhang.get(result).getTen(), donhang.get(result).getBaohanh(), donhang.get(result).getNguon(), donhang.get(result).getNgaynhap(), donhang.get(result).getVon(), donhang.get(result).getGia(), list.get(i).getNhanvien(), list.get(i).getSoluong()));
            }
        }
        return full;
    }

    private void sortList(ArrayList<CountSanpham2> list) {
        Collections.sort(list, new Comparator<CountSanpham2>() {
            @Override
            public int compare(CountSanpham2 lhs, CountSanpham2 rhs) {
                return lhs.getMa().compareTo(rhs.getMa());
            }
        });
    }

    private ArrayList<CountSanpham> FullList(ArrayList<CountSanpham> khac, ArrayList<CountSanpham> ga) {
        ArrayList<CountSanpham> full = new ArrayList<>();
        full.addAll(ga);
        for (int i = 0; i<khac.size(); i++){
            int result = sosanh(ga, khac.get(i).getMasanpham(), khac.get(i).getSoluong());
            if (result == -1){
                full.add(new CountSanpham(ga.get(0).getNhanvien(), khac.get(i).getMasanpham(), 0));
            }
        }
        return full;
    }

    private int sosanh2(ArrayList<Kiemkho> dem_h, String masanpham) {
        int result = -1;
        if (dem_h.size() != 0){
            for (int i = 0; i < dem_h.size(); i++){
                if (dem_h.get(i).getMa().equals(masanpham)){
                    result = i;
                }
            }
        }
        return result;
    }

    private int sosanh(ArrayList<CountSanpham> dem_h, String masanpham, int soluong) {
        int result = -1;
        if (dem_h.size() != 0){
            for (int i = 0; i < dem_h.size(); i++){
                if (dem_h.get(i).getMasanpham().equals(masanpham)){
                    result = i;
                }
            }
        }
        return result;
    }

    class GetDataKho extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            x = donhang.size();
            if(x == 0)
                jIndex = 0;
            else
                jIndex = x;
            dialog = new ProgressDialog(Main_List_Lechkho.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... strings) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_KIEMKHO_URL);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DANHSACHKIEMKHO2);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (chinhanh.equals(object.getString("vitri")) && (object.getString("kho").equals(kho))){
                                        donhang.add(0, new Kiemkho(
                                                object.getString("ngay"),
                                                object.getString("gio"),
                                                object.getString("maNhanvien"),
                                                object.getString("tenNhanvien"),
                                                object.getString("maSanpham"),
                                                object.getString("tenSanpham"),
                                                object.getString("baohanhSanpham"),
                                                object.getString("nguonSanpham"),
                                                object.getString("ngaynhapSanpham"),
                                                object.getString("vonSanpham"),
                                                object.getString("giaSanpham")
                                        ));
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
        protected void onPostExecute(Void string) {
            super.onPostExecute(string);
            if(donhang.size() > 0) {
                for (int i = 0; i < donhang.size(); i++){
                    int resultNV = sosanhNV(dem, donhang.get(i).getMaNhanvien());
                    int resultMA = sosanhMA(dem, donhang.get(i).getMa(), donhang.get(i).getMaNhanvien());
                    if (resultNV == -1){
                        if (resultMA == -1){
                            dem.add(new CountSanpham(donhang.get(i).getMaNhanvien(), donhang.get(i).getMa(), 1));
                        } else {
                        }
                    } else {
                        if (resultMA == -1){
                            dem.add(new CountSanpham(donhang.get(i).getMaNhanvien(), donhang.get(i).getMa(), 1));
                        } else {
                            dem.set( resultMA, new CountSanpham(dem.get(resultMA).getNhanvien(), dem.get(resultMA).getMasanpham(),dem.get(resultMA).getSoluong()+1));
                        }
                    }
                }
            } else {
                new CustomToast().Show_Toast(Main_List_Lechkho.this, findViewById(android.R.id.content), "Không có dữ liệu được tìm thấy");
            }
            setList(dem);
            dialog.dismiss();
        }
    }

    public void setList(ArrayList<CountSanpham> list) {
        this.dem = list;
        for (int i = 0; i<dem.size(); i++){
            if (dem.get(i).getNhanvien().equals(snUserA)){
                demA.add(dem.get(i));
            }
            if (dem.get(i).getNhanvien().equals(snUserB)){
                demB.add(dem.get(i));
            }
        }
        final ArrayList<CountSanpham> giong = new ArrayList<>(), khac = new ArrayList<>(),
                all = new ArrayList<>(), ga = new ArrayList<>(), gb = new ArrayList<>();
        ga.addAll(demA); gb.addAll(demB);
        for (int i = 0; i < ga.size(); i++){
            for (int j = 0; j < gb.size(); j++){
                if ((ga.get(i).getMasanpham().equals(gb.get(j).getMasanpham())) && (ga.get(i).getSoluong() == (gb.get(j).getSoluong()))){
                    giong.add(new CountSanpham("giong",ga.get(i).getMasanpham(), ga.get(i).getSoluong()));
                }
            }
        }

        all.addAll(ga);all.addAll(gb);
        for (int i = 0; i < all.size(); i++){
            int result = sosanh(giong, all.get(i).getMasanpham(), all.get(i).getSoluong());
            if (result == -1){
                khac.add(all.get(i));
            }
        }
        fullga.addAll(FullList(khac, ga));
        fullgb.addAll(FullList(khac, gb));
        fullga2.addAll(iniTen(donhang, fullga));
        fullgb2.addAll(iniTen(donhang, fullgb));
        fullgiong.addAll(iniTen(donhang, giong));
        sortList(fullga2);
        sortList(fullgb2);
        init(fullga2, fullgb2);
    }

    private int sosanhMA(ArrayList<CountSanpham> dem_h, String masanpham, String nhanvien) {
        int result = -1;
        if (dem_h.size() != 0){
            for (int i = 0; i < dem_h.size(); i++){
                if (dem_h.get(i).getMasanpham().equals(masanpham) && dem_h.get(i).getNhanvien().equals(nhanvien)){
                    result = i;
                }
            }
        }
        return result;
    }

    private int sosanhNV(ArrayList<CountSanpham> dem_h, String nhanvien) {
        int result = -1;
        if (dem_h.size() != 0){
            for (int i = 0; i < dem_h.size(); i++){
                if (dem_h.get(i).getNhanvien().equals(nhanvien)){
                    result = i;
                }
            }
        }
        return result;
    }

    public void init(ArrayList<CountSanpham2> fullga, ArrayList<CountSanpham2> fullgb) {
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        stk = (TableLayout) findViewById(R.id.tbLechkho);
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setLayoutParams(rowParams);
        TextView tv0 = new TextView(this);
        tv0.setText(" STT ");
        tv0.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv0.setBackgroundResource(R.drawable.cell_shape);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" MSP ");
        tv1.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv1.setBackgroundResource(R.drawable.cell_shape);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Tên sản phẩm ");
        tv2.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv2.setBackgroundResource(R.drawable.cell_shape);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(nameUserA);
        tv3.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv3.setBackgroundResource(R.drawable.cell_shape);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(nameUserB);
        tv4.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv4.setBackgroundResource(R.drawable.cell_shape);
        tbrow0.addView(tv4);
        stk.addView(tbrow0);
        int stt = 1;
        for (int i = 0; i < fullga.size(); i++) {
            if (fullga.get(i).getSoluong() == fullgb.get(i).getSoluong()){
                TableRow tbrow = new TableRow(this);
                tbrow.setLayoutParams(rowParams);
                TextView t1v = new TextView(this);
                t1v.setText(stt+"");
                t1v.setGravity(Gravity.CENTER);
                t1v.setBackgroundResource(R.drawable.cell_shape);
                t1v.setTextColor(Color.BLUE);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(fullga.get(i).getMa());
                t2v.setGravity(Gravity.CENTER);
                t2v.setBackgroundResource(R.drawable.cell_shape);
                t2v.setTextColor(Color.BLUE);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText(fullga.get(i).getTenSanpham());
                t3v.setBackgroundResource(R.drawable.cell_shape);
                t3v.setTextColor(Color.BLUE);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText(fullga.get(i).getSoluong()+"");
                t4v.setGravity(Gravity.CENTER);
                t4v.setBackgroundResource(R.drawable.cell_shape);
                t4v.setTextColor(Color.BLUE);
                tbrow.addView(t4v);
                TextView t5v = new TextView(this);
                t5v.setText(fullgb.get(i).getSoluong()+"");
                t5v.setGravity(Gravity.CENTER);
                t5v.setBackgroundResource(R.drawable.cell_shape);
                t5v.setTextColor(Color.BLUE);
                tbrow.addView(t5v);
                stk.addView(tbrow);
                stt++;
            } else {
                TableRow tbrow = new TableRow(this);
                tbrow.setLayoutParams(rowParams);
                tbrow.setWeightSum(10);
                TextView t1v = new TextView(this);
                t1v.setText(stt+"");
                t1v.setGravity(Gravity.CENTER);
                t1v.setBackgroundResource(R.drawable.cell_shape);
                t1v.setTextColor(Color.RED);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(fullga.get(i).getMa());
                t2v.setGravity(Gravity.CENTER);
                t2v.setBackgroundResource(R.drawable.cell_shape);
                t2v.setTextColor(Color.RED);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText(fullga.get(i).getTenSanpham());
                t3v.setBackgroundResource(R.drawable.cell_shape);
                t3v.setTextColor(Color.RED);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText(fullga.get(i).getSoluong()+"");
                t4v.setGravity(Gravity.CENTER);
                t4v.setBackgroundResource(R.drawable.cell_shape);
                t4v.setTextColor(Color.RED);
                tbrow.addView(t4v);
                TextView t5v = new TextView(this);
                t5v.setText(fullgb.get(i).getSoluong()+"");
                t5v.setGravity(Gravity.CENTER);
                t5v.setBackgroundResource(R.drawable.cell_shape);
                t5v.setTextColor(Color.RED);
                tbrow.addView(t5v);
                stk.addView(tbrow);
                stt++;
            }
        }
    }

    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){
            dialog = new ProgressDialog(Main_List_Lechkho.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Đang tạo báo cáo");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... arg0) {
            int j = 0;
            CreatedTable();
            while (j < lechkhoArrayList.size()){
                putData(j);
                j++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            new CustomToast().Show_Toast(Main_List_Lechkho.this, findViewById(android.R.id.content), "Thành công!");
        }
    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    public String CreatedTable(){
        try {
            // Link Script
            URL url = new URL(Keys.SCRIPT_CREATEDTABLE);

            // Load Json object
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("nameSheet", tentrang);
            Log.e("params", postDataParams.toString());

            // Kết nối HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            } else {
                return new String("false : " + responseCode);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    public String putData(int j){
        try {
            // Link Script
            URL url = new URL(Keys.SCRIPT_BC_KK);

            // Load Json object
            JSONObject postDataParams = new JSONObject();
            if (j == 0) {
                postDataParams.put("nameSheet", tentrang);
                postDataParams.put("kkChinhanh", "Chi Nhánh");
                postDataParams.put("kkKho", "Kho kiểm");
                postDataParams.put("kkStt", lechkhoArrayList.get(0).getSTT());
                postDataParams.put("kkMaSP", lechkhoArrayList.get(j).getMasp());
                postDataParams.put("kkTenSP", lechkhoArrayList.get(j).getTensp());
                postDataParams.put("kkBaohanhSP", lechkhoArrayList.get(j).getBaohanhsp());
                postDataParams.put("kkNguonSP", lechkhoArrayList.get(j).getNguonsp());
                postDataParams.put("kkNgaynhapSP", lechkhoArrayList.get(j).getNgaynhapsp());
                postDataParams.put("kkVonSP", lechkhoArrayList.get(j).getVonsp());
                postDataParams.put("kkGiaSP", lechkhoArrayList.get(j).getGiasp());
                postDataParams.put("kkNhanvienA", lechkhoArrayList.get(j).getNvA());
                postDataParams.put("kkNhanvienB", lechkhoArrayList.get(j).getNvB());
            } else {
                postDataParams.put("nameSheet", tentrang);
                postDataParams.put("kkChinhanh", chinhanh);
                postDataParams.put("kkKho", kho);
                postDataParams.put("kkStt", j);
                postDataParams.put("kkMaSP", lechkhoArrayList.get(j).getMasp());
                postDataParams.put("kkTenSP", lechkhoArrayList.get(j).getTensp());
                postDataParams.put("kkBaohanhSP", lechkhoArrayList.get(j).getBaohanhsp());
                postDataParams.put("kkNguonSP", lechkhoArrayList.get(j).getNguonsp());
                postDataParams.put("kkNgaynhapSP", lechkhoArrayList.get(j).getNgaynhapsp());
                postDataParams.put("kkVonSP", lechkhoArrayList.get(j).getVonsp());
                postDataParams.put("kkGiaSP", lechkhoArrayList.get(j).getGiasp());
                postDataParams.put("kkNhanvienA", lechkhoArrayList.get(j).getNvA());
                postDataParams.put("kkNhanvienB", lechkhoArrayList.get(j).getNvB());
            }
            Log.e("params", postDataParams.toString());

            // Kết nối HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            } else {
                return new String("false : " + responseCode);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

}
