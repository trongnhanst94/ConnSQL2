package com.example.windows10gamer.connsql;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.windows10gamer.connsql.Adapter.Adapter_List_Kho;
import com.example.windows10gamer.connsql.Object.Kiemkho;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Main_List_Kiemkho extends AppCompatActivity {
    ArrayList<Kiemkho> arrayList;
    Adapter_List_Kho adapter;
    ListView listView;
    String url = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.DANHSACHKIEMKHO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_kiemkho);
        listView = (ListView) findViewById(R.id.lvListKiemkho) ;
        arrayList = new ArrayList<>();
        new GetDataKho().execute();
        adapter = new Adapter_List_Kho(Main_List_Kiemkho.this, R.layout.adapter_kiemkho, arrayList);
        listView.setAdapter(adapter);
    }

    class GetDataKho extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */

            x=arrayList.size();

            if(x==0)
                jIndex=0;
            else
                jIndex=x;

            dialog = new ProgressDialog(Main_List_Kiemkho.this);
            dialog.setTitle("Hãy chờ..."+ jIndex);
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getDataFromWeb(url);

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray(Keys.DANHSACHKIEMKHO);

                        /**
                         * Check Length of Array...
                         */


                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    arrayList.add(new Kiemkho(
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
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if(arrayList.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(Main_List_Kiemkho.this, "Không có dữ liệu được tìm thấy", Toast.LENGTH_SHORT).show();
            }
        }
    }


}