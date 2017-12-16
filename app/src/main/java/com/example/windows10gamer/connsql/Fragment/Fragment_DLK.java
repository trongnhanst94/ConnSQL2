package com.example.windows10gamer.connsql.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Report_BHDLK;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Info_BHDLK;
import com.example.windows10gamer.connsql.Object.BHDLK;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by EVRESTnhan on 11/8/2017.
 */

public class Fragment_DLK extends android.support.v4.app.Fragment {
    ListView lvBHDLK;
    ArrayList<BHDLK> list, list_intent, bh, listLoc;
    Adapter_Report_BHDLK adapter_bhdlk;
    FloatingActionButton fab;
    String start, end;
    int dem = 0;
    TextView tvNoti;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dlk, container, false);
        lvBHDLK = (ListView) v.findViewById(R.id.lvBHDLK_Report);
        fab = (FloatingActionButton) v.findViewById(R.id.fabBHDLK);
        tvNoti = (TextView) v.findViewById(R.id.tvNoti);
        tvNoti.setText("Nhấn vào biểu tượng \"Kính lúp\" để tìm kiếm! ");
        listLoc = new ArrayList<>();
        bh = new ArrayList<>();
        list = new ArrayList<>();
        list_intent = new ArrayList<>();
        adapter_bhdlk = new Adapter_Report_BHDLK(getActivity(), R.layout.adapter_report_bh, listLoc);
        lvBHDLK.setAdapter(adapter_bhdlk);
        lvBHDLK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list_intent.clear();
                for (int i = 0; i < list.size(); i++){
                    if (listLoc.get(position).getMaBH().equals(list.get(i).getMaBH())){
                        list_intent.add(list.get(i));
                    }
                }
                Log.d("qqq",list_intent.size()+" size");
                Intent intent = new Intent(getActivity(), Main_Info_BHDLK.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("listBHDLK", list_intent);
                intent.putExtra("InfoBHDLK", bundle);
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvNoti.setText("");
                View customView = getActivity().getLayoutInflater().inflate(R.layout.dialog_bh, null);
                final EditText dpStartDate = (EditText) customView.findViewById(R.id.dpStartDate);
                final EditText dpEndDate = (EditText) customView.findViewById(R.id.dpEndDate);
                dpStartDate.setText(Keys.getDateNow());
                dpEndDate.setText(Keys.getDateNow());
                dpStartDate.setInputType(InputType.TYPE_NULL);
                dpStartDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = java.util.Calendar.getInstance();
                        int day = calendar.get(Calendar.DATE);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                dpStartDate.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        },year, month, day);
                        datePickerDialog.show();
                    }
                });
                dpEndDate.setInputType(InputType.TYPE_NULL);
                dpEndDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = java.util.Calendar.getInstance();
                        int day = calendar.get(Calendar.DATE);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                dpEndDate.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        },year, month, day);
                        datePickerDialog.show();
                    }
                });
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(customView);
                builder.setTitle("Chọn ngày lọc:");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        start = dpStartDate.getText().toString().trim();
                        end = dpEndDate.getText().toString().trim();
                        new GetData().execute();
                    }});
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        return v;
    }

    @Override public void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    class GetData extends AsyncTask<Void, Void, Void> {
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BHDLK);
            try {
                bh.clear();
                listLoc.clear();
                list.clear();
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BHDLK_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    list.add(new BHDLK(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("maOrder"),
                                            object.getString("date"),
                                            object.getString("time"),
                                            object.getString("chinhanh"),
                                            object.getString("tenNV"),
                                            object.getString("maNV"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichuOrder"),
                                            object.getString("tenKH"),
                                            object.getString("sdtKH"),
                                            object.getString("ghichuKH"),
                                            object.getString("ma_moi"),
                                            object.getString("ten_moi"),
                                            object.getString("baohanh_moi"),
                                            object.getString("nguon_moi"),
                                            object.getString("ngaynhap_moi"),
                                            object.getString("von_moi"),
                                            object.getString("gia_moi"),
                                            object.getString("phidoiSP"),
                                            object.getString("chechlech"),
                                            object.getString("lydo")
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
            if(list.size() > 0) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date hom = null; Date loadBegin1 = null; Date loadEnd1 = null;
                try {
                    loadBegin1 = (Date) simpleDateFormat.parse(start);
                    loadEnd1   = (Date) simpleDateFormat.parse(end);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < list.size(); i++) {
                    int resultNV = sosanhBH(listLoc, list.get(i).getMaBH());
                    try {
                        hom = (Date) simpleDateFormat.parse(Keys.setDate(list.get(i).getDateToday()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(hom.compareTo(loadBegin1) >= 0  && hom.compareTo(loadEnd1) <= 0) {
                        if (resultNV == -1){
                            listLoc.add(list.get(i));
                        }
                    }
                }
                Collections.reverse(list);
                Collections.reverse(listLoc);
                adapter_bhdlk.notifyDataSetChanged();
                if (listLoc.size() == 0){
                    lvBHDLK.setVisibility(View.INVISIBLE);
                    tvNoti.setText("Không tìm thấy");
                }
            } else {
                if (listLoc.size() == 0){
                    tvNoti.setText("Không tìm thấy!");
                    adapter_bhdlk.notifyDataSetChanged();
                }
            }
        }
    }

    private int sosanhBH(ArrayList<BHDLK> baohanh, String ma) {
        int result = -1;
        if (baohanh.size() != 0){
            for (int i = 0; i < baohanh.size(); i++){
                if (baohanh.get(i).getMaBH().equals(ma)){
                    result = i;
                }
            }
        }
        return result;
    }
}