package com.example.windows10gamer.connsql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_Ketqua_Kiemkho;
import com.example.windows10gamer.connsql.Adapter.Adapter_kho;
import com.example.windows10gamer.connsql.Object.CountSanpham;
import com.example.windows10gamer.connsql.Object.Kiemkho;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
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
    Adapter_kho adapter_kho;
    ListView lvScan, lvScan2, lvPerson;
    Spinner snA, snB;
    Button btnSubmit;
    String urlUser = Keys.LOGIN;
    ArrayList<String> UserName;
    ArrayAdapter<String> adapterA;
    ArrayAdapter<String> adapterB;
    String snUserA, snUserB;

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
        // anh xa
        lvScan = (ListView) findViewById(R.id.lvKetquakiemkho);
        lvScan2 = (ListView) findViewById(R.id.lvKetquakiemkho2);
        lvPerson = (ListView) findViewById(R.id.lvPerson);
        btnSubmit = (Button) findViewById(R.id.btnSubmitKQKK);
        snA = (Spinner) findViewById(R.id.snKQKKA);
        snB = (Spinner) findViewById(R.id.snKQKKB);

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
                    dem.clear();
                    demA.clear();
                    demB.clear();
                    new GetDataKho().execute();
                } else {
                    Snackbar.make(view, "Không có Internet", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        GetUser(urlUser, new VolleyCallback(){
            @Override
            public void onSuccess(final ArrayList<User> result) {
                final ArrayList<String> resultName, resultMa;
                resultName = new ArrayList<String>();
                resultMa = new ArrayList<String>();
                for (int i = 0; i < result.size(); i++){
                    resultName.add(result.get(i).getTen());
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
                    public void onItemSelected(AdapterView<?> adapter, View v,
                                               int position, long id) {
                        String rs = adapter.getItemAtPosition(position).toString();
                        for (int i = 0; i < result.size(); i++){
                            if (rs.equals(result.get(i).getTen())){
                                snUserA = result.get(i).getMa();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });
                //
                snB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapter, View v,
                                               int position, long id) {
                        String rs = adapter.getItemAtPosition(position).toString();
                        for (int i = 0; i < result.size(); i++){
                            if (rs.equals(result.get(i).getTen())){
                                snUserB = result.get(i).getMa();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
            }
        });


        //select spinner

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
//        for (int i = 0; i < demA.size(); i++) {
//            boolean found = false;
//            for (int j = 0; j < demB.size(); j++) {
//                if (demA.get(i).getMasanpham().equals(demB.get(j).getMasanpham()) && demA.get(i).getSoluong() == demB.get(j).getSoluong()) {
//                    found = true;
//                    j = -1;
//                    break;
//                }
//            }
//            if (!found) {
//                results_Person.add(demA.get(i));
//            }
//        }
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
                                            object.getString("gia")
                                    ));
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
            setList(dem);
        }
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
                                usernames.add(new User(
                                        object.getInt("id"),
                                        object.getString("ma"),
                                        object.getString("ten"),
                                        object.getString("username"),
                                        object.getString("password")
                                ));
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
