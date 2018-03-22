package com.example.windows10gamer.connsql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Main_Login extends AppCompatActivity {
    EditText edUser, edPass;
    Button btnLogin;
    String User, Pass;
    String ma_user, ten, username, password, level, chucdanh, created, shortName;
    public String img;
    CheckBox cbRemember, cbShowpass;
    SharedPreferences shared;
    LinearLayout login_layout;
    Animation animation;
    ProgressDialog progress;
    ArrayList<com.example.windows10gamer.connsql.Object.User> userArrayList;
    private String updated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        anhxa();
        progress = new ProgressDialog(Main_Login.this);
        progress.setMessage("Đang đăng nhập.");
        progress.setIndeterminate(false);
        progress.show();
        shared = getSharedPreferences("login", MODE_PRIVATE);
        if (shared.getBoolean("isLogged", TRUE) == TRUE){
            User = shared.getString("tk", "");
            Pass = shared.getString("mk", "");
            cbRemember.setChecked(shared.getBoolean("checked", FALSE));
            edUser.setText(shared.getString("tk", ""));
            edPass.setText(shared.getString("mk", ""));
            if (Connect_Internet.checkConnection(getApplicationContext())) {
                GetUser();
            } else {
                Toasty.error(this, "Không có mạng Internet", Toast.LENGTH_LONG, true).show();
                login_layout.startAnimation(animation);
            }
        } else {
            progress.dismiss();
        }
        userArrayList = new ArrayList();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Connect_Internet.checkConnection(getApplicationContext())) {
                    progress = new ProgressDialog(Main_Login.this);
                    progress.setMessage("Đang đăng nhập.");
                    progress.setIndeterminate(false);
                    progress.show();
                    User = edUser.getText().toString().trim();
                    Pass = edPass.getText().toString().trim();
                    GetUser();
                } else {
                    Toasty.error(Main_Login.this, "Không có mạng Internet", Toast.LENGTH_LONG, true).show();
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

    public ArrayList<User> GetUser() {
        final ArrayList<User> usernames = new ArrayList<User>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Keys.LOGIN2+"?user="+edUser.getText().toString().trim()+"&pass="+Keys.md5(edPass.getText().toString().trim()), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if (object.getInt("level") >= Keys.LEVEL_QL){
                                    usernames.add(new User(
                                            object.getInt("id"),
                                            object.getString("ma_user"),
                                            object.getString("ten"),
                                            object.getString("shortName"),
                                            object.getString("username"),
                                            object.getString("password"),
                                            object.getString("level"),
                                            object.getString("chucdanh"),
                                            object.getString("trangthai"),
                                            object.getString("created"),
                                            object.getString("updated"),
                                            object.getString("img")
                                    ));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (usernames.size() > 0){
                            ma_user = usernames.get(0).getMa();
                            ten = usernames.get(0).getTen();
                            shortName = usernames.get(0).getShortName();
                            username = usernames.get(0).getUsername();
                            password = usernames.get(0).getPassword();
                            level = usernames.get(0).getLevel();
                            chucdanh = usernames.get(0).getChucdanh();
                            created = usernames.get(0).getCreated();
                            updated = usernames.get(0).getUpdated();
                            img = usernames.get(0).getImg();
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("tk", username);
                            editor.putString("mk", edPass.getText().toString().trim());
                            editor.putString("ma", ma_user);
                            editor.putString("level", level);
                            editor.putString("chucdanh", chucdanh);
                            editor.putString("ten", ten);
                            editor.putString("shortName", shortName);
                            editor.putString("img", img);
                            editor.putBoolean("checked", TRUE);
                            if (cbRemember.isChecked()) {
                                editor.putBoolean("isLogged", TRUE);
                                editor.commit();
                            } else {
                                editor.putBoolean("isLogged", FALSE);
                                editor.commit();
                            }
                            progress.dismiss();
                            Toasty.Config.getInstance()
                                    .setErrorColor(Color.parseColor("#d82828"))
                                    .setInfoColor(Color.parseColor("#6699ff"))
                                    .setSuccessColor(Color.parseColor("#6ebf4b"))
                                    .setWarningColor(Color.parseColor("#fdaf17"))
                                    .apply();
                            Toasty.success(Main_Login.this, "Xin chào " + shortName, Toast.LENGTH_LONG, true).show();
                            Intent intent = new Intent(Main_Login.this, Main.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (progress != null){
                                progress.dismiss();
                            }
                            Toasty.warning(Main_Login.this, "Không đúng tài khoản", Toast.LENGTH_LONG, true).show();
                            login_layout.startAnimation(animation);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_Login.this, "Không kết nối được Server", Toast.LENGTH_LONG, true).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return usernames;
    }

    private void anhxa() {
        edUser     = findViewById(R.id.edEmail);
        edPass     = findViewById(R.id.edPass);
        btnLogin   = findViewById(R.id.btnLogin);
        cbRemember = findViewById(R.id.cbRemember);
        cbShowpass = findViewById(R.id.cbShowpass);
        login_layout = findViewById(R.id.login_layout);
        animation = AnimationUtils.loadAnimation(this, R.anim.shake);
    }
}