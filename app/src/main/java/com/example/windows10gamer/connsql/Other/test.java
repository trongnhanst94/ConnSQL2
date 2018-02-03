package com.example.windows10gamer.connsql.Other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by EVRESTnhan on 1/26/2018.
 */

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 6; i++){
            list.add(i);
            list.add(i);
        }
        for(int index = list.size() - 1; index >= 0; index--) {
            if(list.get(index) == 5){
                list.remove(index);
            }
        }
        for (int i = 0; i < list.size(); i++){
        }
    }
}
