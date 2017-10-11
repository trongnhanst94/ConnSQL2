package com.example.windows10gamer.connsql.Other;

/**
 * Created by EVRESTnhan on 9/30/2017.
 */

public interface ScanResultReceiver {
    /**
     * function to receive scanresult
     * @param codeContent data of the barcode scanned
     */
    public void scanResultData(String codeFormat, String codeContent);

    public void scanResultData(NoScanResultException noScanData);
}
