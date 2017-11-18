package com.example.windows10gamer.connsql.Bao_Hanh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.windows10gamer.connsql.Fragment.Fragment_1D1;
import com.example.windows10gamer.connsql.Fragment.Fragment_DLK;
import com.example.windows10gamer.connsql.Fragment.Fragment_HT;
import com.example.windows10gamer.connsql.Other.SectionsPageAdapter;
import com.example.windows10gamer.connsql.R;

public class Main_Report_BH extends AppCompatActivity {

    private SectionsPageAdapter sectionsPage;
    private ViewPager viewPager;
    FloatingActionButton fabReportBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_report_bh);
        fabReportBH = (FloatingActionButton) findViewById(R.id.fabReportBH);
        sectionsPage = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_BH);
        tabLayout.setupWithViewPager(viewPager);
        fabReportBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Report_BH.this, Main_ScanBH.class));
            }
        });
    }



    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_HT(), "Hoàn tiền");
        adapter.addFragment(new Fragment_DLK(), "Đổi lấy khác");
        adapter.addFragment(new Fragment_1D1(), "1 đổi 1");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }
}
