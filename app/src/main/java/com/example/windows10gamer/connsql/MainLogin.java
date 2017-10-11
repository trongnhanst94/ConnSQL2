package com.example.windows10gamer.connsql;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainLogin extends AppCompatActivity {
    EditText edUser, edPass;
    Button btnLogin;
    String User, Pass;
    CheckBox cbRemember, cbShowpass;
    SharedPreferences shared;
    LinearLayout login_layout;
    Animation animation;
    ArrayList<com.example.windows10gamer.connsql.Object.User> userArrayList;
    boolean checkValues;
    String url = Keys.LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        shared = getSharedPreferences("login", MODE_PRIVATE);
        edUser.setText(shared.getString("tk",""));
        edPass.setText(shared.getString("mk",""));
        cbRemember.setChecked(shared.getBoolean("checked", FALSE));
        userArrayList = new ArrayList();
        CheckValues(url);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connect_Internet.checkConnection(getApplicationContext())) {
                    User = edUser.getText().toString().trim();
                    Pass = edPass.getText().toString().trim();
                    if (isEmpty(User) && isEmpty(Pass)){
                        login_layout.startAnimation(animation);
                        new CustomToast().Show_Toast(MainLogin.this, view, "Không được để trống.");
                    } else {
                        if (checkLogin(userArrayList, User, Pass) != null){
                            int pos = Integer.parseInt(checkLogin(userArrayList, User, Pass));
                            new CustomToast().Show_Toast(MainLogin.this, view, "Xin chào " + userArrayList.get(pos).getTen());
                            Intent intent = new Intent(MainLogin.this, Main_Menu.class);
                            intent.putExtra("session_username", userArrayList.get(pos).getTen());
                            intent.putExtra("session_ma", userArrayList.get(pos).getMa());
                            startActivity(intent);
                            checkValues = TRUE;
                            if (cbRemember.isChecked()) {
                                SharedPreferences.Editor editor = shared.edit();
                                editor.putString("tk", User);
                                editor.putString("mk", Pass);
                                editor.putBoolean("checked", TRUE);
                                editor.putBoolean("isLogged", TRUE);
                                editor.commit();
                            } else {
                                SharedPreferences.Editor editor = shared.edit();
                                editor.putString("tk", "");
                                editor.putString("mk", "");
                                editor.putBoolean("checked", FALSE);
                                editor.putBoolean("isLogged", FALSE);
                                editor.commit();
                            }
                        } else {
                            checkValues = FALSE;
                            new CustomToast().Show_Toast(MainLogin.this, view, "Không đúng tài khoản.");
                            login_layout.startAnimation(animation);
                        }
                    }
                } else {
                    Snackbar.make(view, "Không có Internet", Snackbar.LENGTH_LONG).show();
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

    public static String checkLogin(ArrayList<User> userArrayList, String User, String Pass) {
        String pos = null;
        for (int i = 0; i < userArrayList.size(); i++){
            if ((userArrayList.get(i).getUsername().equals(User)) && (userArrayList.get(i).getPassword().equals(md5(Pass)))){
                pos = i+"";
                break;
            }
        }
        return pos;
    }

    private void CheckValues(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                userArrayList.add(new User(
                                        object.getInt("id"),
                                        object.getString("ma"),
                                        object.getString("ten"),
                                        object.getString("username"),
                                        object.getString("password")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MainLogin.this, error+"", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
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

    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }


    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}