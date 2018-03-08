package com.example.windows10gamer.connsql.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Report_BH1D1;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Info_BH1D1;
import com.example.windows10gamer.connsql.Object.BH1D1;
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

/**
 * Created by EVRESTnhan on 11/8/2017.
 */

public class Fragment_1D1 extends android.support.v4.app.Fragment {
    ListView lvBH1D1;
    ArrayList<BH1D1> list, list_intent, listLoc;
    Adapter_Report_BH1D1 adapter_bhdlk;
    FloatingActionButton fab;
    public String start, end;
    int dem = 0;
    TextView tvNoti;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1d1, container, false);
        lvBH1D1 = v.findViewById(R.id.lvBH1D1_Report);
        fab = v.findViewById(R.id.fabBH1D1);
        tvNoti = v.findViewById(R.id.tvNoti);
        tvNoti.setText("Nhấn vào biểu tượng \"Kính lúp\" để tìm kiếm!");
        list = new ArrayList<>();listLoc = new ArrayList<>();
        adapter_bhdlk = new Adapter_Report_BH1D1(getActivity(), R.layout.adapter_report_bh, listLoc);
        lvBH1D1.setAdapter(adapter_bhdlk);
        lvBH1D1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Connect_Internet.checkConnection(getActivity()))
                    Connect_Internet.buildDialog(getActivity()).show();
                else {
                    list_intent = new ArrayList<>();
                    list_intent.add(list.get(position));
                    Intent intent = new Intent(getActivity(), Main_Info_BH1D1.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("listBH1D1", list_intent);
                    intent.putExtra("InfoBH1D1", bundle);
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
                    final EditText dpStartDate = customView.findViewById(R.id.dpStartDate);
                    final EditText dpEndDate = customView.findViewById(R.id.dpEndDate);
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
                            if (Build.VERSION.SDK_INT == 24) {
                                final Context contextThemeWrapper =
                                        new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog);
                                try {
                                    DatePickerDialog datePickerDialog = new Keys.FixedHoloDatePickerDialog(contextThemeWrapper, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            calendar.set(year, month, dayOfMonth);
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                            dpStartDate.setText(simpleDateFormat.format(calendar.getTime()));
                                        }
                                    }, year, month, day);
                                    datePickerDialog.show();
                                } catch ( InstantiationException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        calendar.set(year, month, dayOfMonth);
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                        dpStartDate.setText(simpleDateFormat.format(calendar.getTime()));
                                    }
                                }, year, month, day);
                                datePickerDialog.show();
                            }
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
                            if (Build.VERSION.SDK_INT == 24) {
                                final Context contextThemeWrapper =
                                        new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog);
                                try {
                                    DatePickerDialog datePickerDialog = new Keys.FixedHoloDatePickerDialog(contextThemeWrapper, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            calendar.set(year, month, dayOfMonth);
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                            dpEndDate.setText(simpleDateFormat.format(calendar.getTime()));
                                        }
                                    }, year, month, day);
                                    datePickerDialog.show();
                                } catch ( InstantiationException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        calendar.set(year, month, dayOfMonth);
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                        dpEndDate.setText(simpleDateFormat.format(calendar.getTime()));
                                    }
                                }, year, month, day);
                                datePickerDialog.show();
                            }
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
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BH1DOI1+"?loadBegin="+params[0]+"&loadEnd="+params[1]);
            try {
                list.clear();
                listLoc.clear();
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BH1DOI1_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    list.add(new BH1D1(
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
                                            object.getString("gio_moi"),
                                            object.getString("ma_moi"),
                                            object.getString("ten_moi"),
                                            object.getString("baohanh_moi"),
                                            object.getString("nguon_moi"),
                                            object.getString("ngaynhap_moi"),
                                            object.getString("von_moi"),
                                            object.getString("gia_moi"),
                                            object.getString("phibaohanh"),
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
                for (int i = 0; i < list.size(); i++) {
                    listLoc.add(list.get(i));
                }
                Collections.reverse(list);
                Collections.reverse(listLoc);
                adapter_bhdlk.notifyDataSetChanged();
                if (listLoc.size() == 0){
                    lvBH1D1.setVisibility(View.INVISIBLE);
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

}