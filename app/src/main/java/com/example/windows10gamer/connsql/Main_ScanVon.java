package com.example.windows10gamer.connsql;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows10gamer.connsql.Adapter.Adapter_ScanVon;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.Keys;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import es.dmoral.toasty.Toasty;

public class Main_ScanVon extends AppCompatActivity {
    Button btnscanvon, btntao;
    private String scannedData;
    ListView lvscanvon;
    ArrayList<Sanpham> arrayList = new ArrayList<>();
    Adapter_ScanVon adapter;
    String ma, ten, baohanh, nguon, ngaynhap, von, gia;
    LinearLayout lntao, lnquet;
    ImageView ivtao;
    String mavach;
    Bitmap bitmap;
    TextView tvtao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifecycle","onCreate 2");
        setContentView(R.layout.activity_main_scan_von);
        btnscanvon = findViewById(R.id.btnscanvon);
        lvscanvon = findViewById(R.id.lvscan);
        btntao = findViewById(R.id.btntao);
        ivtao = findViewById(R.id.ivtao);
        tvtao = findViewById(R.id.tvtao);
        lnquet = findViewById(R.id.lnquet);
        lntao = findViewById(R.id.lntao);
        lntao.setVisibility(View.GONE);
        adapter = new Adapter_ScanVon(Main_ScanVon.this, arrayList);
        lvscanvon.setAdapter(adapter);
        btnscanvon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnquet.setVisibility(View.VISIBLE);
                lntao.setVisibility(View.GONE);
                ScanNow();
            }
        });
        btntao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnquet.setVisibility(View.GONE);
                lntao.setVisibility(View.VISIBLE);
                AlertDialog.Builder dialog = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    dialog = new AlertDialog.Builder(Main_ScanVon.this);
                } else {
                    dialog = new AlertDialog.Builder(Main_ScanVon.this);
                }
                dialog.setIcon(R.drawable.ic_addchi)
                        .setTitle("Tạo mã vạch");
                View mView = getLayoutInflater().inflate(R.layout.dialog_taoma, null);
                dialog.setCancelable(false);
                final EditText ma = mView.findViewById(R.id.ma);
                final EditText ten = mView.findViewById(R.id.ten);
                final EditText baohanh = mView.findViewById(R.id.baohanh);
                final EditText nguon = mView.findViewById(R.id.nguon);
                final EditText ngaynhap = mView.findViewById(R.id.ngaynhap);
                final EditText von = mView.findViewById(R.id.von);
                final EditText gia = mView.findViewById(R.id.gia);
                ngaynhap.setInputType(InputType.TYPE_NULL);
                ngaynhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DATE);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);
                        if (Build.VERSION.SDK_INT == 24) {
                            final Context contextThemeWrapper =
                                    new ContextThemeWrapper(Main_ScanVon.this, android.R.style.Theme_Holo_Light_Dialog);
                            try {
                                DatePickerDialog datePickerDialog = new Keys.FixedHoloDatePickerDialog(contextThemeWrapper, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        calendar.set(year, month, dayOfMonth);
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                        ngaynhap.setText(simpleDateFormat.format(calendar.getTime()));
                                    }
                                }, year, month, day);
                                datePickerDialog.show();
                            } catch ( Fragment.InstantiationException e) {
                                e.printStackTrace();
                            }
                        } else {
                            DatePickerDialog datePickerDialog = new DatePickerDialog(Main_ScanVon.this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    calendar.set(year, month, dayOfMonth);
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    ngaynhap.setText(simpleDateFormat.format(calendar.getTime()));
                                }
                            }, year, month, day);
                            datePickerDialog.show();
                        }
                    }
                });
                dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mavach = "";
                        tvtao.setText("");
                        if (!ma.getText().toString().trim().equals("") && !ten.getText().toString().trim().equals("") && !baohanh.getText().toString().trim().equals("") && !nguon.getText().toString().trim().equals("") && !ngaynhap.getText().toString().trim().equals("") && !Keys.mahoagiavon(von.getText().toString().trim()).equals("") && !gia.getText().toString().trim().equals("")){
                            mavach = Keys.bodautiengviet(ma.getText().toString().trim())
                                    + ";" + Keys.bodautiengviet(ten.getText().toString().trim())
                                    + ";" + Keys.bodautiengviet(baohanh.getText().toString().trim())
                                    + ";" + Keys.bodautiengviet(nguon.getText().toString().trim())
                                    + ";" + ngaynhap.getText().toString().trim()
                                    + ";" + Keys.mahoagiavon(von.getText().toString().trim())
                                    + ";" + gia.getText().toString().trim();
                            tvtao.setText("MÃ CODE: "+mavach);
                            try {
                                bitmap = TextToImageEncode(mavach);
                                ivtao.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toasty.warning(Main_ScanVon.this, "Phải nhập tất cả các trường", Toast.LENGTH_LONG, true).show();
                        }

                    }
                });
                dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setView(mView);
                AlertDialog al = dialog.create();
                al.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle","onStart 2");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume 2");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause 2");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle","onStop 2");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle","onRestart 2");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy 2");
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.QR_CODE,
                    500, 500, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.den):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private void ScanNow() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Quét mã code");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            scannedData = result.getContents();
            if (scannedData != null) {
                try {
                    StringTokenizer st = new StringTokenizer(scannedData, ";");
                    ma = st.nextToken();
                    ten = st.nextToken();
                    baohanh = st.nextToken();
                    nguon = st.nextToken();
                    ngaynhap = st.nextToken();
                    von = st.nextToken();
                    gia = st.nextToken();
                    arrayList.add(new Sanpham(ma, ten, baohanh, nguon, ngaynhap, Keys.setMoney(Integer.valueOf(Keys.giaimagiavon(von))), Keys.setMoney(Integer.parseInt(gia))));
                    adapter.notifyDataSetChanged();
                }   catch (NoSuchElementException nse) {
                    Toasty.success(this, "Lỗi định dạng mã vạch", Toast.LENGTH_LONG, true).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
