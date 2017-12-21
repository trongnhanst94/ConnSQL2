package com.example.windows10gamer.connsql.Xuat_Nhap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_Duyetnhap;
import com.example.windows10gamer.connsql.Object.XuatNhap;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_Duyetnhap extends AppCompatActivity {
    String maXN, ngay, gio, ca, chinhanhToday, maNVToday, tenNVToday, chinhanhNhan, maNVNhan, tenNVNhan, ghichu;
    TextView tvdanhan, tvchuanhan, tvtatca, tvmaXN, tvngay, tvca, tvchinhanhToday, tvmaNVToday, tvtenNVToday, tvchinhanhNhan, tvmaNVNhan, tvtenNVNhan, tvghichu, tvphantram;
    ListView lv;
    int soluong, phantram;
    ArrayList<XuatNhap> list = new ArrayList<>();
    Adapter_Duyetnhap adapter;
    ProgressDialog dialog;
    FloatingActionButton fabAn, fabHien, fabSubmit;
    LinearLayout lnghichu, lnHiden;
    ArrayList<String> mspList = new ArrayList<>();
    private ListView listview;
    ArrayList<String> items = new ArrayList<String>();
    private int count;
    private boolean[] thumbnailsselection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_duyetnhap);
        tvmaXN = (TextView) findViewById(R.id.tvInfomaXN);
        tvngay = (TextView) findViewById(R.id.tvInfoDate);
        tvca = (TextView) findViewById(R.id.tvInfoTime);
        tvchinhanhToday = (TextView) findViewById(R.id.tvInfochinhanhToday);
        tvmaNVToday = (TextView) findViewById(R.id.tvInfomaNV);
        tvtenNVToday = (TextView) findViewById(R.id.tvInfotenNV);
        tvchinhanhNhan = (TextView) findViewById(R.id.tvInfochinhanhNhan);
        tvmaNVNhan = (TextView) findViewById(R.id.tvInfomaNVNhan);
        tvtenNVNhan = (TextView) findViewById(R.id.tvInfotenNVNhan);
        tvghichu = (TextView) findViewById(R.id.tvInfoghichu);
        tvdanhan = (TextView) findViewById(R.id.tvdanhan);
        tvchuanhan = (TextView) findViewById(R.id.tvchuanhan);
        tvtatca = (TextView) findViewById(R.id.tvtatca);
        lv = (ListView) findViewById(R.id.lvInfoxuathang);
        tvphantram = (TextView) findViewById(R.id.tvphantram);
        lnghichu = (LinearLayout) findViewById(R.id.lnghichu);
        lnHiden = (LinearLayout) findViewById(R.id.lnHiden);
        fabAn = (FloatingActionButton) findViewById(R.id.fabAn);
        fabHien = (FloatingActionButton) findViewById(R.id.fabHien);
        fabSubmit = (FloatingActionButton) findViewById(R.id.fabSubmit);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Main_Duyetnhap");
        maXN =bundle.getString("maXN");
        maNVToday =bundle.getString("maNVToday");
        tenNVToday =bundle.getString("tenNVToday");
        chinhanhToday =bundle.getString("chinhanhToday");
        maNVNhan =bundle.getString("maNVNhan");
        tenNVNhan =bundle.getString("tenNVNhan");
        chinhanhNhan =bundle.getString("chinhanhNhan");
        ngay =bundle.getString("ngay");
        gio =bundle.getString("gio");
        ca =bundle.getString("ca");
        ghichu =bundle.getString("ghichu");
        soluong =bundle.getInt("soluong");
        phantram =bundle.getInt("phantram");
        tvmaXN.setText("Mã đơn xuất: "+maXN);
        tvngay.setText(gio+" "+ngay);
        tvca.setText(ca);
        tvchinhanhToday.setText(chinhanhToday);
        tvmaNVToday.setText(maNVToday);
        tvtenNVToday.setText(tenNVToday);
        tvchinhanhNhan.setText(chinhanhNhan);
        tvmaNVNhan.setText(maNVNhan);
        tvtenNVNhan.setText(tenNVNhan);
        if (ghichu.equals("")){
            lnghichu.setVisibility(View.GONE);
        } else {
            tvghichu.setText(ghichu);
        }
        tvphantram.setText("Tổng: "+phantram+"/"+soluong);
        count = items.size();
        thumbnailsselection = new boolean[count];
        GetDT(new VolleyCallback(){
            @Override
            public void onSuccess(final ArrayList<XuatNhap> result) {
                lv.setAdapter(new ImageAdapter(Main_Duyetnhap.this));
            }
        });
        fabAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnHiden.setVisibility(View.GONE);
                fabAn.setVisibility(View.GONE);
                fabHien.setVisibility(View.VISIBLE);
            }
        });
        fabHien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnHiden.setVisibility(View.VISIBLE);
                fabAn.setVisibility(View.VISIBLE);
                fabHien.setVisibility(View.GONE);
            }
        });
    }

    public ArrayList<XuatNhap> GetDT(final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, Keys.MAIN_XUATNHAP+"?maXN="+maXN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ja = response.getJSONArray(Keys.XUATNHAP);
                            for(int i=0; i < ja.length(); i++){
                                JSONObject object = ja.getJSONObject(i);
                                list.add(new XuatNhap(
                                        object.getString("maXN"),
                                        object.getString("ngay"),
                                        object.getString("ca"),
                                        object.getString("gio"),
                                        object.getString("chinhanhToday"),
                                        object.getString("maNVToday"),
                                        object.getString("tenNVToday"),
                                        object.getString("ma"),
                                        object.getString("ten"),
                                        object.getString("baohanh"),
                                        object.getString("nguon"),
                                        object.getString("ngaynhap"),
                                        object.getString("von"),
                                        object.getString("gia"),
                                        object.getString("ghichu"),
                                        object.getString("chinhanhNhan"),
                                        object.getString("maNVNhan"),
                                        object.getString("tenNVNhan"),
                                        object.getString("trangthai")
                                ));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) { e.printStackTrace();}
                        callback.onSuccess(list);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onSuccess(list);
                    }
                }
        );
        requestQueue.add(jor);
        return list;
    }

//    public void Check(String msp) {
//        mspList.add(msp);
//        Log.d("qqq", msp+" add");
//    }
//
//    public void Uncheck(String msp) {
//        mspList.remove(msp);
//        Log.d("qqq", msp+" remove");
//    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<XuatNhap> result);
    }

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context mContext;
        public ImageAdapter(Context context) {
            mContext = context;
        }
        public int getCount() {
            return count;
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_duyetnhap, null);
                holder.tvAdapterSalesMa = (TextView) convertView.findViewById(R.id.tvAdapterSalesMa);
                holder.tvAdapterSalesNguon = (TextView) convertView.findViewById(R.id.tvAdapterSalesNguon);
                holder.tvAdapterSalesBaohanh = (TextView) convertView.findViewById(R.id.tvAdapterSalesBaohanh);
                holder.tvAdapterSalesNgaynhap = (TextView) convertView.findViewById(R.id.tvAdapterSalesNgaynhap);
                holder.tvAdapterSalesTen = (TextView) convertView.findViewById(R.id.tvAdapterSalesTen);
                holder.tvAdapterSalesGia = (TextView) convertView.findViewById(R.id.tvAdapterSalesGia);
                holder.cbduyetnhap = (CheckBox) convertView.findViewById(R.id.cbduyetnhap);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvAdapterSalesMa.setId(position);
            holder.tvAdapterSalesNguon.setId(position);
            holder.tvAdapterSalesBaohanh.setId(position);
            holder.tvAdapterSalesNgaynhap.setId(position);
            holder.tvAdapterSalesTen.setId(position);
            holder.tvAdapterSalesGia.setId(position);
            holder.cbduyetnhap.setId(position);
//            holder.checkbox.setOnClickListener(new OnClickListener() {
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    CheckBox cb = (CheckBox) v;
//                    int id = cb.getId();
//                    if (thumbnailsselection[id]) {
//                        cb.setChecked(false);
//                        thumbnailsselection[id] = false;
//                    } else {
//                        cb.setChecked(true);
//                        thumbnailsselection[id] = true;
//                    }
//                }
//            });
//            holder.textview.setOnClickListener(new OnClickListener() {
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    int id = v.getId();
//                }
//            });
//            holder.textview.setText(items.get(position));
//            holder.checkbox.setChecked(thumbnailsselection[position]);
//            holder.id = position;
            return convertView;
        }
    }
    private class ViewHolder{
        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia, tvAdapterSalesNguon, tvAdapterSalesBaohanh, tvAdapterSalesNgaynhap;
        CheckBox cbduyetnhap;
    }
//    public void click(View v) {
//        if (v.getId() == R.id.button1) {
//            final ArrayList<Integer> posSel = new ArrayList<Integer>();
//            posSel.clear();
//            boolean noSelect = false;
//            for (int i = 0; i < thumbnailsselection.length; i++) {
//                if (thumbnailsselection[i] == true) {
//                    noSelect = true;
//                    Log.e("sel pos thu-->", "" + i);
//                    posSel.add(i);
//                    // break;
//                }
//            }
//            if (!noSelect) {
//                Toast.makeText(MainActivity.this, "Please Select Item!",
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(MainActivity.this,
//                        "Selected Items:" + posSel.toString(),
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
