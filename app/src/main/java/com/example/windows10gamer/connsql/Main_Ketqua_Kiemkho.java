package com.example.windows10gamer.connsql;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_Ketqua_Kiemkho;
import com.example.windows10gamer.connsql.Object.CountSanpham;
import com.example.windows10gamer.connsql.Object.Kiemkho;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Main_Ketqua_Kiemkho extends AppCompatActivity {
    ArrayList<Kiemkho> arrayList;
    ArrayList<CountSanpham> dem, demA, demB, results_Person;
    String session_username, session_ma;
    String url = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.DANHSACHKIEMKHO;
    Adapter_Ketqua_Kiemkho adapter, adapter2, adapterPerson;
    ListView lvScan, lvScan2, lvPerson;
    Spinner snA, snB;
    Button btnSubmit, btnReport, btnDsLechKho;
    ArrayList<String> UserName;
    ArrayAdapter<String> adapterA;
    ArrayAdapter<String> adapterB;
    String snUserA, snUserB, nameUserA, nameUserB;
    LinearLayout linearLayout;
    TextView tvga, tvgb, tvgiong, tvkhac, tvslga, tvslgb;
    ArrayList<String> position;
    String url2 = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.DANHSACHCUAHANG;
    String vitriKK, kho = "Kho mới";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ketqua_kiemkho);
        results_Person = new ArrayList<>();
        arrayList = new ArrayList<>();
        UserName = new ArrayList<>();
        dem = new ArrayList<>();
        demA = new ArrayList<>();
        demB = new ArrayList<>();
        linearLayout = (LinearLayout) findViewById(R.id.li);
        lvScan = (ListView) findViewById(R.id.lvKetquakiemkho);
        lvScan2 = (ListView) findViewById(R.id.lvKetquakiemkho2);
        lvPerson = (ListView) findViewById(R.id.lvPerson);
        btnSubmit = (Button) findViewById(R.id.btnSubmitKQKK);
        btnReport = (Button) findViewById(R.id.btnReportKQKK);
        snA = (Spinner) findViewById(R.id.snKQKKA);
        snB = (Spinner) findViewById(R.id.snKQKKB);
        position = new ArrayList<>();
        new Getvitri().execute();
        // Intent
        Intent intent = getIntent();
        session_username = intent.getStringExtra("session_username");
        session_ma = intent.getStringExtra("session_ma");


        //list A & B
        adapter = new Adapter_Ketqua_Kiemkho(Main_Ketqua_Kiemkho.this, R.layout.adapter_ketqua_kiemkho, demA){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                view.setBackgroundColor(getResources().getColor(R.color.âs));
                return view;
            }
        };
        lvScan.setAdapter(adapter);
        adapter2 = new Adapter_Ketqua_Kiemkho(Main_Ketqua_Kiemkho.this, R.layout.adapter_ketqua_kiemkho, demB){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                view.setBackgroundColor(getResources().getColor(R.color.âs));
                return view;
            }
        };
        lvScan2.setAdapter(adapter2);

        adapterPerson = new Adapter_Ketqua_Kiemkho(Main_Ketqua_Kiemkho.this, R.layout.adapter_ketqua_kiemkho, results_Person);
        lvPerson.setAdapter(adapterPerson);


        // btn Cập nhật
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabKQKK);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (Connect_Internet.checkConnection(getApplicationContext())) {
                    if (Build.VERSION.SDK_INT >= 11) {
                        recreate();
                    } else {
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                } else {
                    Snackbar.make(view, "Không có Internet", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        GetUser(Keys.DANHSACHLOGIN, new VolleyCallback(){
            @Override
            public void onSuccess(final ArrayList<User> result) {
                final ArrayList<String> resultName, resultMa;
                resultName = new ArrayList<String>();
                resultMa = new ArrayList<String>();
                for (int i = 0; i < result.size(); i++){
                    resultName.add(result.get(i).getShortName());
                    resultMa.add(result.get(i).getMa());
                }

                //spinner A
                adapterA = new ArrayAdapter<String>(Main_Ketqua_Kiemkho.this, android.R.layout.simple_spinner_item, resultName);
                adapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                snA.setAdapter(adapterA);

                //spinner B
                adapterB = new ArrayAdapter<String>(Main_Ketqua_Kiemkho.this, android.R.layout.simple_spinner_item, resultName);
                adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                snB.setAdapter(adapterB);

                snA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                        String rs = adapter.getItemAtPosition(position).toString();
                        for (int i = 0; i < result.size(); i++){
                            if (rs.equals(result.get(i).getShortName())){
                                snUserA = result.get(i).getMa();
                                nameUserA = result.get(i).getShortName();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {}
                });

                snB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                        String rs = adapter.getItemAtPosition(position).toString();
                        for (int i = 0; i < result.size(); i++){
                            if (rs.equals(result.get(i).getShortName())){
                                snUserB = result.get(i).getMa();
                                nameUserB = result.get(i).getShortName();
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {}
                });
            }
        });

        //btn submit
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dem.clear();
                demA.clear();
                demB.clear();
                new GetDataKho().execute();
                btnSubmit.setEnabled(false);
                btnSubmit.setBackgroundColor(getResources().getColor(R.color.aaaaa));
            }
        });
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
        dialog(giong, khac, ga, gb);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(giong, khac, ga, gb);
            }
        });
    }


    public void dialog(final ArrayList<CountSanpham> giong, final ArrayList<CountSanpham> khac, final ArrayList<CountSanpham> ga, final ArrayList<CountSanpham> gb){
        Dialog dialog = new Dialog(Main_Ketqua_Kiemkho.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_report);
        dialog.show();
        btnDsLechKho = (Button) dialog.findViewById(R.id.btnDsLechkho);
        tvga = (TextView) dialog.findViewById(R.id.tvga);
        tvgb = (TextView) dialog.findViewById(R.id.tvgb);
        tvgiong = (TextView) dialog.findViewById(R.id.tvgiong);
        tvkhac = (TextView) dialog.findViewById(R.id.tvkhac);
        tvslga = (TextView) dialog.findViewById(R.id.tvslga);
        tvslgb = (TextView) dialog.findViewById(R.id.tvslgb);
        tvga.setText(nameUserA);tvgb.setText(nameUserB);
        tvslga.setText((ga.size())+"");
        tvslgb.setText((gb.size())+"");
        tvgiong.setText((giong.size())+"");
        tvkhac.setText((khac.size())+"");
        btnDsLechKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ga.size() == 0){
                    new CustomToast().Show_Toast(Main_Ketqua_Kiemkho.this, findViewById(android.R.id.content), "Danh sách của "+nameUserA+" rỗng");
                } else {
                    if (gb.size() == 0){
                        new CustomToast().Show_Toast(Main_Ketqua_Kiemkho.this, findViewById(android.R.id.content), "Danh sách của "+nameUserB+" rỗng");
                    } else {
                        Intent intent = new Intent(Main_Ketqua_Kiemkho.this, Main_List_Lechkho.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("giong", giong);
                        bundle.putParcelableArrayList("khac", khac);
                        bundle.putParcelableArrayList("ga", ga);
                        bundle.putParcelableArrayList("gb", gb);
                        bundle.putParcelableArrayList("donhang", arrayList);
                        bundle.putString("nameUserA", nameUserA);
                        bundle.putString("nameUserB", nameUserB);
                        bundle.putString("chinhanh", vitriKK);
                        bundle.putString("kho", kho);
                        intent.putExtra("LechkhoBundle", bundle);
                        startActivity(intent);
                    }
                }
            }
        });
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    class Getvitri extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            x = arrayList.size();
            if(x == 0)
                jIndex = 0;
            else
                jIndex = x;
            dialog = new ProgressDialog(Main_Ketqua_Kiemkho.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(url2);
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
//        AlertDialog.Builder dialog = new AlertDialog.Builder(Main_Ketqua_Kiemkho.this);
//        View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
//        dialog.setTitle("Chọn cửa hàng cần báo cáo");
//        dialog.setCancelable(false);
//        final Spinner spinner = (Spinner) mView.findViewById(R.id.spinnerKM);
//        ArrayAdapter mAdapter = new ArrayAdapter<>(Main_Ketqua_Kiemkho.this, android.R.layout.simple_spinner_item, position);
//        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(mAdapter);
//        dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                vitriKK = String.valueOf(spinner.getSelectedItem());
//            }
//        });

        AlertDialog.Builder dialog = new AlertDialog.Builder(Main_Ketqua_Kiemkho.this);
        View mView = getLayoutInflater().inflate(R.layout.spinner_kk, null);
        dialog.setTitle("Chọn chi nhánh và kho");
        dialog.setCancelable(false);
        final Spinner spinner = (Spinner) mView.findViewById(R.id.spinnerKM);
        final RadioButton rbmoi = (RadioButton) mView.findViewById(R.id.srbmoi);
        final RadioButton rbloi = (RadioButton) mView.findViewById(R.id.srbloi);
        rbmoi.setChecked(true);
        ArrayAdapter mAdapter = new ArrayAdapter<>(Main_Ketqua_Kiemkho.this, android.R.layout.simple_spinner_item, position);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        rbmoi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rbloi.isChecked() && isChecked == true){
                    rbloi.setChecked(false);
                    kho = "Kho mới";
                } else {
                    rbloi.setChecked(true);
                    kho = "Kho lỗi";
                }
            }
        });
        rbloi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rbmoi.isChecked() && isChecked == true){
                    rbmoi.setChecked(false);
                    kho = "Kho lỗi";
                } else {
                    rbmoi.setChecked(true);
                    kho = "Kho mới";
                }
            }
        });
        dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vitriKK = String.valueOf(spinner.getSelectedItem());
            }
        });

        dialog.setView(mView);
        AlertDialog al = dialog.create();
        al.show();
    }

    class GetDataKho extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            x = arrayList.size();
            if(x == 0)
                jIndex = 0;
            else
                jIndex = x;
            dialog = new ProgressDialog(Main_Ketqua_Kiemkho.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... strings) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(url);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DANHSACHKIEMKHO);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (vitriKK.equals(object.getString("vitri")) && (object.getString("kho").equals(kho))){
                                        arrayList.add(0, new Kiemkho(
                                                object.getString("ngay"),
                                                object.getString("gio"),
                                                object.getString("manhanvien"),
                                                object.getString("tennhanvien"),
                                                object.getString("ma"),
                                                object.getString("ten"),
                                                object.getString("baohanh"),
                                                object.getString("nguon"),
                                                object.getString("ngaynhap"),
                                                object.getString("von"),
                                                object.getString("gia")
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
            dialog.dismiss();
            if(arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++){
                    int resultNV = sosanhNV(dem, arrayList.get(i).getMaNhanvien());
                    int resultMA = sosanhMA(dem, arrayList.get(i).getMa(), arrayList.get(i).getMaNhanvien());
                    if (resultNV == -1){
                        if (resultMA == -1){
                            dem.add(new CountSanpham(arrayList.get(i).getMaNhanvien(), arrayList.get(i).getMa(), 1));
                            adapter.notifyDataSetChanged();
                        } else {
                        }
                    } else {
                        if (resultMA == -1){
                            dem.add(new CountSanpham(arrayList.get(i).getMaNhanvien(), arrayList.get(i).getMa(), 1));
                            adapter.notifyDataSetChanged();
                        } else {
                            dem.set( resultMA, new CountSanpham(dem.get(resultMA).getNhanvien(), dem.get(resultMA).getMasanpham(),dem.get(resultMA).getSoluong()+1));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            } else {
                Toast.makeText(Main_Ketqua_Kiemkho.this, "Không có dữ liệu được tìm thấy", Toast.LENGTH_SHORT).show();
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);
            setList(dem);
        }
    }

    private int sosanh(ArrayList<CountSanpham> dem_h, String masanpham, int soluong) {
        int result = -1;
        if (dem_h.size() != 0){
            for (int i = 0; i < dem_h.size(); i++){
                if (dem_h.get(i).getMasanpham().equals(masanpham) && (soluong == dem_h.get(i).getSoluong())){
                    result = i;
                }
            }
        }
        return result;
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

    public ArrayList<User> GetUser(String urlUser, final VolleyCallback callback) {
        final ArrayList<User> usernames = new ArrayList<User>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlUser, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if (object.getInt("level") == Keys.LEVEL_BH){
                                    usernames.add(new User(
                                            object.getInt("id"),
                                            object.getString("ma_user"),
                                            object.getString("ten"),
                                            object.getString("shortName"),
                                            object.getString("username"),
                                            object.getString("password")
                                    ));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onSuccess(usernames);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onSuccess(usernames);
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return usernames;
    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<User> result);
    }
}
