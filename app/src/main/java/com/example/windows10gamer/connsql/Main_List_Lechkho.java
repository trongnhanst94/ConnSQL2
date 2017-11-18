package com.example.windows10gamer.connsql;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;

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
    ArrayList<CountSanpham> giong, khac, ga, gb, fullga, fullgb, showa, showb;
    ArrayList<CountSanpham2> fullga2, fullgb2, fullgiong;
    String nameUserA, nameUserB, chinhanh, kho, tentrang = "trangmoi";
    TableLayout stk;
    CheckBox cbGiong, cbKhac;
    Button btnLoc;
    ArrayList<Kiemkho> donhang;
    ArrayList<Lechkho> lechkhoArrayList = new ArrayList<>();
    ProgressDialog dialog;
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
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("LechkhoBundle");
        donhang = bundle.getParcelableArrayList("donhang");
        giong = bundle.getParcelableArrayList("giong");
        khac = bundle.getParcelableArrayList("khac");
        ga = bundle.getParcelableArrayList("ga");
        gb = bundle.getParcelableArrayList("gb");
        nameUserA = bundle.getString("nameUserA");
        nameUserB = bundle.getString("nameUserB");
        chinhanh = bundle.getString("chinhanh");
        kho = bundle.getString("kho");
        fullga.addAll(FullList(khac, ga));
        fullgb.addAll(FullList(khac, gb));
        fullga2.addAll(iniTen(donhang, fullga));
        fullgb2.addAll(iniTen(donhang, fullgb));
        fullgiong.addAll(iniTen(donhang, giong));
        sortList(fullga2);
        sortList(fullgb2);
        init(fullga2, fullgb2);
        fabBCKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Main_List_Lechkho.this);
                dialog.setMessage("Bạn có chắc muốn báo cáo?");
                View mView = getLayoutInflater().inflate(R.layout.spinner_lk, null);
                final EditText input = (EditText) mView.findViewById(R.id.input);
                dialog.setView(input);
                dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().trim().equals("")){
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
                stk.removeAllViews();
                if (cbGiong.isChecked()){
                    if (cbKhac.isChecked()){
                        sortList(fullga2);
                        sortList(fullgb2);
                        init(fullga2, fullgb2);
                    } else {
                        sortList(fullgiong);
                        init(fullgiong, fullgiong );
                    }
                } else {
                    if (cbKhac.isChecked()){
                        sortList(fullga2);
                        sortList(fullgb2);
                        ArrayList<CountSanpham2> tamA, tamB;
                        tamA = new ArrayList<>(); tamB = new ArrayList<>();
                        tamA.addAll(fullga2); tamB.addAll(fullgb2);
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
                        new CustomToast().Show_Toast(Main_List_Lechkho.this, v, "Không có dữ liệu!");
                    }
                }
            }
        });
    }

    private void addList(ArrayList<CountSanpham2> fullga2, ArrayList<CountSanpham2> fullgb2) {
        lechkhoArrayList.clear();
        lechkhoArrayList.add(new Lechkho("STT", "Mã SP", "Tên sản phẩm", nameUserA, nameUserB));
        int dem = 1;
        for (int i = 0; i < fullga.size(); i++) {
            if (fullga.get(i).getSoluong() == fullgb.get(i).getSoluong()){
            lechkhoArrayList.add(new Lechkho(1+"", fullga2.get(i).getMa(), fullga2.get(i).getTenSanpham(), fullga2.get(i).getSoluong()+"", fullgb2.get(i).getSoluong()+""));
            dem++;
            Log.d("qqq", fullga2.get(i).getMa()+ fullga2.get(i).getTenSanpham()+"; "+ fullga2.get(i).getSoluong()+"; "+ fullgb2.get(i).getSoluong()+" giong");
            }
        }
        for (int i = 0; i < fullgb.size(); i++) {
            if (fullga.get(i).getSoluong() != fullgb.get(i).getSoluong()){
                lechkhoArrayList.add(new Lechkho(1+"", fullgb2.get(i).getMa(), fullgb2.get(i).getTenSanpham(), fullga2.get(i).getSoluong()+"", fullgb2.get(i).getSoluong()+""));
                dem++;
                Log.d("qqq", fullga2.get(i).getMa()+ fullga2.get(i).getTenSanpham()+"; "+ fullga2.get(i).getSoluong()+"; "+ fullgb2.get(i).getSoluong()+" khac");
            }
        }
        new SendRequest().execute();
    }

    private ArrayList<CountSanpham2> iniTen(ArrayList<Kiemkho> donhang, ArrayList<CountSanpham> list) {
        ArrayList<CountSanpham2> full = new ArrayList<>();
        for (int i = 0; i<list.size(); i++){
            int result = sosanh2(donhang, list.get(i).getMasanpham());
            if (result != -1){
                full.add(new CountSanpham2(list.get(i).getMasanpham(), donhang.get(result).getTen(), list.get(i).getNhanvien(), list.get(i).getSoluong()));
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

    public void init(ArrayList<CountSanpham2> fullga, ArrayList<CountSanpham2> fullgb) {
        //TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(500, 500);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        stk = (TableLayout) findViewById(R.id.tbLechkho);
  //      stk.setLayoutParams(tableParams);
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
            dialog.setMessage("Đơn hàng đang được xử lý");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... arg0) {
            int j = 0;
            CreatedTable();
            while (j < lechkhoArrayList.size()){
                putData(j);
                //addOrderWeb(j);
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
                postDataParams.put("kkNhanvienA", lechkhoArrayList.get(j).getNvA());
                postDataParams.put("kkNhanvienB", lechkhoArrayList.get(j).getNvB());
            } else {
                postDataParams.put("nameSheet", tentrang);
                postDataParams.put("kkChinhanh", chinhanh);
                postDataParams.put("kkKho", kho);
                postDataParams.put("kkStt", j);
                postDataParams.put("kkMaSP", lechkhoArrayList.get(j).getMasp());
                postDataParams.put("kkTenSP", lechkhoArrayList.get(j).getTensp());
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
