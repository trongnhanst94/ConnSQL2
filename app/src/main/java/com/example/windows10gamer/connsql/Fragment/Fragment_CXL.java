package com.example.windows10gamer.connsql.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import com.example.windows10gamer.connsql.Adapter.Adapter_Report_BHCXL;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Info_BHCXL;
import com.example.windows10gamer.connsql.Object.BHCXL;
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
import java.util.Collections;

public class Fragment_CXL extends Fragment {
    ListView lvBHCXL;
    ArrayList<BHCXL> list, list_intent, listLoc;
    Adapter_Report_BHCXL adapter_bhcxl;
    FloatingActionButton fab;
    public String start, end;
    TextView tvNoti;
    int dem = 0;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cxl, container, false);
        lvBHCXL = (ListView) v.findViewById(R.id.lvBHCXL_Report);
        fab = (FloatingActionButton) v.findViewById(R.id.fabBHCXL);
        tvNoti = (TextView) v.findViewById(R.id.tvNoti);
        tvNoti.setText("Nhấn vào biểu tượng \"Kính lúp\" để tìm kiếm!");
        list = new ArrayList<>();
        listLoc = new ArrayList<>();
        adapter_bhcxl = new Adapter_Report_BHCXL(getActivity(), R.layout.adapter_report_bh, listLoc);
        lvBHCXL.setAdapter(adapter_bhcxl);
        lvBHCXL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Connect_Internet.checkConnection(getActivity()))
                    Connect_Internet.buildDialog(getActivity()).show();
                else {
                    list_intent = new ArrayList<>();
                    list_intent.add(listLoc.get(position));
                    Intent intent = new Intent(getActivity(), Main_Info_BHCXL.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("listBHCXL", list_intent);
                    intent.putExtra("InfoBHCXL", bundle);
                    startActivity(intent);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getActivity()))
                    Connect_Internet.buildDialog(getActivity()).show();
                else {
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
                            }, year, month, day);
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
                            }, year, month, day);
                            datePickerDialog.show();
                        }
                    });
                    AlertDialog.Builder builder = null;
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        builder = new AlertDialog.Builder(getActivity());
                    } else {
                        builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setIcon(R.drawable.ic_settings);
                    builder.setView(customView);
                    builder.setTitle("Chọn ngày lọc:");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            start = dpStartDate.getText().toString().trim();
                            end = dpEndDate.getText().toString().trim();
                            new GetData().execute(start, end);
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
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

    class GetData extends AsyncTask<String, Void, Void> {

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
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BHCXL+"?loadBegin="+params[0]+"&loadEnd="+params[1]);
            try {
                list.clear();
                listLoc.clear();
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BHCXL_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    list.add(new BHCXL(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("gio"),
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
                                            object.getString("thoigianhen"),
                                            object.getString("maNVxuly"),
                                            object.getString("tenNVxuly")
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
                for (int i = 0; i < list.size(); i++) {
                    listLoc.add(list.get(i));
                }
                Collections.reverse(list);
                Collections.reverse(listLoc);
                adapter_bhcxl.notifyDataSetChanged();
                if (listLoc.size() == 0){
                    lvBHCXL.setVisibility(View.INVISIBLE);
                    tvNoti.setText("Không tìm thấy");
                }
            } else {
                if (listLoc.size() == 0){
                    tvNoti.setText("Không tìm thấy!");
                    adapter_bhcxl.notifyDataSetChanged();
                }
            }
        }
    }
}