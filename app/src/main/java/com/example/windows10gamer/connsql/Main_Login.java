package com.example.windows10gamer.connsql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Main_Login extends AppCompatActivity {
    EditText edUser, edPass;
    Button btnLogin;
    String User, Pass;
    String session_username, session_ma;
    String ma_user, ten, username, password, level, chucdanh, created, shortName;
    CheckBox cbRemember, cbShowpass;
    SharedPreferences shared;
    LinearLayout login_layout;
    Animation animation;
    ArrayList<com.example.windows10gamer.connsql.Object.User> userArrayList;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        shared = getSharedPreferences("login", MODE_PRIVATE);
        if (shared.getBoolean("isLogged", TRUE) == TRUE){
            User = shared.getString("tk", "");
            Pass = shared.getString("mk", "");
            cbRemember.setChecked(shared.getBoolean("checked", FALSE));
            getUserWeb(User, Pass);
        }
        edUser.setText(shared.getString("tk", ""));
        edPass.setText(shared.getString("mk", ""));
        cbRemember.setChecked(shared.getBoolean("checked", FALSE));
        userArrayList = new ArrayList();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Connect_Internet.checkConnection(getApplicationContext())) {
                    User = edUser.getText().toString().trim();
                    Pass = edPass.getText().toString().trim();
                    new SendRequest().execute();
                } else {
                    new CustomToast().Show_Toast(Main_Login.this, v, "Không có Internet");
                    login_layout.startAnimation(animation);
                }
            }
        });

        cbShowpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked) {
                    cbShowpass.setText(R.string.hide_pwd);
                    edPass.setInputType(InputType.TYPE_CLASS_TEXT);
                    edPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    cbShowpass.setText(R.string.showpass);
                    edPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){
            dialog = new ProgressDialog(Main_Login.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Đơn hàng đang được xử lý");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... arg0) {
            getUserWeb(User, Pass);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
        }
    }

    public void getUserWeb(final String user, final String pass){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("error")){
                            String newT = null;
                            try {
                                newT = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            setres(newT, pass);
                        } else {
                            new CustomToast().Show_Toast(Main_Login.this, findViewById(android.R.id.content), "Không đúng tài khoản.");
                            login_layout.startAnimation(animation);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main_Login.this, "Lỗi "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", Keys.md5(pass));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void setres(String response, String pass) {
        StringTokenizer st = new StringTokenizer(response, ";");
        while(st.hasMoreTokens())
        {
            ma_user = st.nextToken();
            ten = st.nextToken();
            shortName = st.nextToken();
            username = st.nextToken();
            password = st.nextToken();
            level = st.nextToken();
            chucdanh = st.nextToken();
            created = st.nextToken();
        }
        if (cbRemember.isChecked()) {
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("tk", username);
            editor.putString("mk", pass);
            editor.putString("ma", ma_user);
            editor.putString("ten", ten);
            editor.putString("shortName", shortName);
            editor.putBoolean("checked", TRUE);
            editor.putBoolean("isLogged", TRUE);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("tk", "");
            editor.putString("mk", "");
            editor.putString("ma", "");
            editor.putString("ten", "");
            editor.putString("shortName", "");
            editor.putBoolean("checked", FALSE);
            editor.putBoolean("isLogged", FALSE);
            editor.commit();
        }
        new CustomToast().Show_Toast(Main_Login.this, findViewById(android.R.id.content), "Xin chào " + shortName);
        Intent intent = new Intent(Main_Login.this, Main.class);
        intent.putExtra("session_username", shortName);
        intent.putExtra("session_ma", ma_user);
        intent.putExtra("shortName", shortName);
        startActivity(intent);
        finish();
    }

    private void anhxa() {
        edUser     = (EditText) findViewById(R.id.edEmail);
        edPass     = (EditText) findViewById(R.id.edPass);
        btnLogin   = (Button) findViewById(R.id.btnLogin);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        cbShowpass = (CheckBox) findViewById(R.id.cbShowpass);
        login_layout = (LinearLayout) findViewById(R.id.login_layout);
        animation = AnimationUtils.loadAnimation(this, R.anim.shake);
    }
}