package com.example.windows10gamer.connsql.Other;

/**
 * Created by EVRESTnhan on 9/30/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class ScanFragment extends Fragment {
    private String codeFormat,codeContent;
    ScanFragment activity = this;
    private final String noResultErrorMsg = "Không có dữ liệu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentIntegrator integrator = new IntentIntegrator(this.getActivity()).forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scanning");
        integrator.setCameraId(0);  // Use Adapter specific camera of the device
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        try {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            ScanResultReceiver parentActivity = (ScanResultReceiver) this.getActivity();

            if (scanningResult != null) {
                //we have Adapter result
                codeContent = scanningResult.getContents();
                codeFormat = scanningResult.getFormatName();
                // send received data
                parentActivity.scanResultData(codeFormat, codeContent);
            }else{
                // send exception
                parentActivity.scanResultData(new NoScanResultException(noResultErrorMsg));
            }
        } catch (NumberFormatException e){
            Toast.makeText(getActivity(), "Lỗi định dạng nhãn", Toast.LENGTH_SHORT).show();
        }

    }
}

