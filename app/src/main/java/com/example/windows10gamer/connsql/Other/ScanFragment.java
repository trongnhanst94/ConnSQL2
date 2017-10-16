package com.example.windows10gamer.connsql.Other;

/**
 * Created by EVRESTnhan on 9/30/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.windows10gamer.connsql.R;

import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;


public class ScanFragment extends Fragment {
    private String codeFormat,codeContent;
    ScanFragment activity = this;
    private final String noResultErrorMsg = "Không có dữ liệu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ZxingOrient intentIntegrator = new ZxingOrient(this.getActivity());
        intentIntegrator.setIcon(R.drawable.ic_launcher)   // Sets the custom icon
                .setToolbarColor("#AA3F51B5")       // Sets Tool bar Color
                .setInfoBoxColor("#AA3F51B5")       // Sets Info box color
                .setInfo("Scan a QR code Image.")   // Sets info message in the info box
                .initiateScan(Barcode.QR_CODE);

        new ZxingOrient(this.getActivity())
                .showInfoBox(false) // Doesn't display the info box
                .setBeep(true)  // Doesn't play beep sound
                .setVibration(true)  // Enables the vibration
                .initiateScan();
//        IntentIntegrator integrator = new IntentIntegrator(this.getActivity()).forSupportFragment(this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
//        integrator.setPrompt("Scanning");
//        integrator.setCameraId(0);  // Use Adapter specific camera of the device
//        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        try {
            ZxingOrientResult scanningResult = ZxingOrient.parseActivityResult(requestCode, resultCode, intent);
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

