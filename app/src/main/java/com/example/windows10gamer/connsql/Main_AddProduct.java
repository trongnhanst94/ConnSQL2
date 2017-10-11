package com.example.windows10gamer.connsql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Other.CustomToast;

import java.util.HashMap;
import java.util.Map;

public class Main_AddProduct extends AppCompatActivity {
    Button btnAddProduct, btnCancelProduct;
    EditText edAddName, edAddMa, edAddGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_product);
        declaration();

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://dealtichtac.com/android/addproduct_json.php";
                String ten = edAddName.getText().toString().trim();
                String ma = edAddMa.getText().toString().trim();
                String gia = edAddGia.getText().toString().trim();
                if (isEmpty(ten) || isEmpty(ma) || isEmpty(gia)){
                    new CustomToast().Show_Toast(Main_AddProduct.this, view, "Không được để trống.");
                } else {
                    addProduct(url, view);
                }
            }
        });

        btnCancelProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void addProduct(String url, final View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            new CustomToast().Show_Toast(Main_AddProduct.this, view, "Thêm thành công.");
                            startActivity(new Intent(Main_AddProduct.this, MainActivity.class));
                        } else {
                            new CustomToast().Show_Toast(Main_AddProduct.this, view, "Bị lỗi.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_AddProduct.this, view, "Bị lỗi "+error);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("ma", edAddMa.getText().toString().trim());
                param.put("ten", edAddName.getText().toString().trim());
                param.put("gia", edAddGia.getText().toString().trim());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void declaration() {
        btnAddProduct    = (Button) findViewById(R.id.btnAddProduct);
        btnCancelProduct = (Button) findViewById(R.id.btnCancelAdd);
        edAddGia         = (EditText) findViewById(R.id.edAddGia);
        edAddMa          = (EditText) findViewById(R.id.edAddMa);
        edAddName        = (EditText) findViewById(R.id.edAddName);
    }

    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
}
