//package com.example.windows10gamer.connsql;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.windows10gamer.connsql.Object.Sanpham;
//import com.example.windows10gamer.connsql.Other.CustomToast;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//
//public class Main_UpdateProduct extends AppCompatActivity {
//
//    Button btnUpdateProduct, btnCancelUpdate;
//    EditText edUpdateName, edUpdateMa, edUpdateGia;
//    int id;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_update_product);
//        declaration();
//        Intent intent = getIntent();
//        Sanpham sanpham = (Sanpham) intent.getSerializableExtra("dataSanpham");
//        id = sanpham.getId();
//        edUpdateMa.setText(sanpham.getMa());
//        edUpdateName.setText(sanpham.getTen());
//        edUpdateGia.setText(sanpham.getGiaban()+"");
//
//        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = "http://dealtichtac.com/android/updateproduct_json.php";
//                String ten = edUpdateName.getText().toString().trim();
//                String ma = edUpdateMa.getText().toString().trim();
//                String gia = edUpdateGia.getText().toString().trim();
//                if (isEmpty(ten) || isEmpty(ma) || isEmpty(gia)){
//                    new CustomToast().Show_Toast(Main_UpdateProduct.this, view, "Không được để trống.");
//                } else {
//                    updateProduct(url, view);
//                }
//            }
//        });
//
//        btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//
//    private void updateProduct(String url, final View view) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response.trim().equals("success")){
//                            new CustomToast().Show_Toast(Main_UpdateProduct.this, view, "Cập nhật thành công.");
//                            startActivity(new Intent(Main_UpdateProduct.this, MainActivity.class));
//                        } else {
//                            new CustomToast().Show_Toast(Main_UpdateProduct.this, view, "Bị lỗi dữ liệu.");
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        new CustomToast().Show_Toast(Main_UpdateProduct.this, view, "Bị lỗi "+error);
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> param = new HashMap<>();
//                param.put("id", String.valueOf(id));
//                param.put("ma", edUpdateMa.getText().toString().trim());
//                param.put("ten", edUpdateName.getText().toString().trim());
//                param.put("gia", edUpdateGia.getText().toString().trim());
//                return param;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
//
//    private void declaration() {
//        btnUpdateProduct    = (Button) findViewById(R.id.btnUpdateProduct);
//        btnCancelUpdate = (Button) findViewById(R.id.btnCancelUpdate);
//        edUpdateGia         = (EditText) findViewById(R.id.edUpdateGia);
//        edUpdateMa          = (EditText) findViewById(R.id.edUpdateMa);
//        edUpdateName        = (EditText) findViewById(R.id.edUpdateName);
//    }
//
//    public static boolean isEmpty(CharSequence str) {
//        if (str == null || str.length() == 0)
//            return true;
//        else
//            return false;
//    }
//}
