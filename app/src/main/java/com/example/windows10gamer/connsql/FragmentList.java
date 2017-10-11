//package com.example.windows10gamer.connsql;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.ListFragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.RetryPolicy;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * Created by EVRESTnhan on 9/24/2017.
// */
//
//public class FragmentList extends ListFragment{
//
//    ListView listView;
//    SanphamAdapter sanphamAdapter;
//    ArrayList<Sanpham> arraySanpham;
//    FloatingActionButton btnAdd;
//    Put_Sanpham putSanpham;
//    String url    = "http://dealtichtac.com/android/json.php";
//    String urlDelete = "http://dealtichtac.com/android/deleteproduct_json.php";
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_sanpham, container, false);
//        putSanpham = (Put_Sanpham) getActivity();
//        btnAdd   = (FloatingActionButton) view.findViewById(R.id.btnAdd);
//        listView = (ListView) view.findViewById(android.R.id.list);
//        arraySanpham = new ArrayList<>();
//        readJson(url);
//        sanphamAdapter = new SanphamAdapter(getActivity(), R.layout.activity_sanpham_list, arraySanpham, FragmentList.this, putSanpham);
//        setListAdapter(sanphamAdapter);
//        btnAdd.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                startActivity(new Intent(getActivity(), Main_AddProduct.class));
//            }
//        });
//        listView.setClickable(false);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), arraySanpham.get(i).getTen(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        return view;
//    }
//
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id)
//    {
//        super.onListItemClick(l, v, position, id);
//        Toast.makeText(getActivity(), arraySanpham.get(position).getTen(), Toast.LENGTH_SHORT).show();
//        putSanpham.DataSanpham(arraySanpham.get(position));
//    }
//
//    private void readJson(String urlJson) {
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlJson, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        arraySanpham.clear();
//                        for (int i = 1; i < response.length(); i++){
//                            try {
//                                JSONObject object = response.getJSONObject(i);
//                                arraySanpham.add(new Sanpham(
//                                        object.getInt("ID"),
//                                        object.getString("Ma"),
//                                        object.getString("Ten"),
//                                        object.getInt("Giaban"),
//                                        object.getString("Des")
//                                ));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        sanphamAdapter.notifyDataSetChanged();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), error+"", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        int socketTimeout = 30000;//30 seconds - change to what you want
//        RetryPolicy policy = new DefaultRetryPolicy(
//                socketTimeout,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        jsonArrayRequest.setRetryPolicy(policy);
//
//        requestQueue.add(jsonArrayRequest);
//    }
//
//    public void DeleteSP(final int idSP){
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response.trim().equals("success")){
//                            Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
//                            readJson(url);
//                        } else {
//                            Toast.makeText(getActivity(), "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> param = new HashMap();
//                param.put("id", String.valueOf(idSP));
//                return param;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
//
//
//}
