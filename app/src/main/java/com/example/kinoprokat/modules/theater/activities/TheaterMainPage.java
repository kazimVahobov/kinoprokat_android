package com.example.kinoprokat.modules.theater.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kinoprokat.R;
import com.example.kinoprokat.modules.theater.fragments.ThMainPageCommonStatistic;
import com.example.kinoprokat.modules.theater.fragments.ThMainPageTodayReport;

public class TheaterMainPage extends AppCompatActivity implements ThMainPageCommonStatistic.CommonStatisticFragmentListener {

    private ThMainPageTodayReport today_report;
    private ThMainPageCommonStatistic commonStatistic;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.th_activity_main_page);
        initialFragments(0);
        initialFragments(1);
    }

    private void initialFragments(int mode) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (mode) {
            case 0: {
                today_report = new ThMainPageTodayReport();
                ft.add(R.id.today_report_frame, today_report);
                break;
            }
            case 1: {
                commonStatistic = new ThMainPageCommonStatistic();
                ft.add(R.id.common_statistic_frame, commonStatistic);
                break;
            }
        }
        ft.commit();
    }

    @Override
    public void onAllThReportPressed() {
        Intent intent = new Intent(this, TheaterReportList.class);
        startActivity(intent);
    }
}
