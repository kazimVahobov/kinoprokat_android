package com.example.kinoprokat.common_activities;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.kinoprokat.R;
import com.example.kinoprokat.adapters.ViewPagerAdapter;
import com.example.kinoprokat.common_fragments.ThReportDetailCommonInfo;
import com.example.kinoprokat.common_fragments.ThReportDetailInfoBySessions;
import com.example.kinoprokat.common_fragments.ThReportDetailWithoutContInfo;
import com.example.kinoprokat.models.Theater;
import com.example.kinoprokat.models.TheaterReport;
import com.example.kinoprokat.services.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThReportDetail extends AppCompatActivity {

    private NetworkService networkService;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ProgressDialog progressDialog;
    private String report_id;
    private TheaterReport report;
    private Theater theater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.th_activity_report_detail);

        findViews();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        if (bundle.getString("id") != null) {
            report_id = bundle.getString("id");
            getReport();
        } else {
            // TODO view error
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getReport() {
        networkService.getApies().getThReportsById(report_id).enqueue(new Callback<TheaterReport>() {
            @Override
            public void onResponse(Call<TheaterReport> call, Response<TheaterReport> response) {
                if (response.isSuccessful()) {
                    report = response.body();
                } else {
                    // TODO view error
                }
            }

            @Override
            public void onFailure(Call<TheaterReport> call, Throwable t) {
                // TODO view error
            }
        });
    }

    private void getTheaterById(String theaterId) {
        networkService.getApies().getTheaterById(theaterId).enqueue(new Callback<Theater>() {
            @Override
            public void onResponse(Call<Theater> call, Response<Theater> response) {
                if (response.isSuccessful()) {
                    theater = response.body();
                } else {
                    // TODO view error
                }
            }

            @Override
            public void onFailure(Call<Theater> call, Throwable t) {
                // TODO view error
            }
        });
    }

    private void findViews() {
        networkService = NetworkService.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.detail);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ThReportDetailCommonInfo.newInstance(report), getString(R.string.common));
        adapter.addFragment(ThReportDetailInfoBySessions.newInstance(report), getString(R.string.sessions));
        adapter.addFragment(ThReportDetailWithoutContInfo.newInstance(report), getString(R.string.without_cont));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
