package com.example.kinoprokat.modules.theater.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kinoprokat.R;
import com.example.kinoprokat.common_activities.ThReportDetail;
import com.example.kinoprokat.models.TheaterReport;
import com.example.kinoprokat.modules.theater.adapters.ThReportListAdapter;
import com.example.kinoprokat.services.NetworkService;
import com.example.kinoprokat.services.RoleService;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheaterReportList extends AppCompatActivity implements ThReportListAdapter.ThReportListListener {

    private ThReportListAdapter adapter;
    private NetworkService networkService;
    private RoleService roleService;
    private String permissionKey;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressDialog dialog;
    private TextView error;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.th_activity_report_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog = ProgressDialog.show(this, null, getString(R.string.loading));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        findViews();
    }

    private void getReports() {
        networkService.getApies().getThReportsByThId(networkService.getJobId()).enqueue(new Callback<List<TheaterReport>>() {
            @Override
            public void onResponse(Call<List<TheaterReport>> call, Response<List<TheaterReport>> response) {
                if (response.isSuccessful()) {
                    error.setVisibility(View.GONE);
                    setAdapter(response.body());
                } else {
                    error.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<TheaterReport>> call, Throwable t) {
                error.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
    }

    private void setAdapter(List<TheaterReport> reports) {
        Collections.sort(reports, TheaterReport.compareByDate);
        recyclerView.setVisibility(View.VISIBLE);
        adapter = new ThReportListAdapter(reports, this);
        recyclerView.setAdapter(adapter);
    }

    private void findViews() {
        networkService = NetworkService.getInstance();
        roleService = RoleService.getInstance();
        permissionKey = roleService.th_report_key;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.all_reports);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        error = (TextView) findViewById(R.id.error);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab);
        if (roleService.checkPermission(0, permissionKey)) {
            fab.setVisibility(View.VISIBLE);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy < 0) {
                        fab.show();

                    } else if (dy > 0) {
                        fab.hide();
                    }
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } else {
            fab.setVisibility(View.GONE);
        }

        getReports();
    }

    @Override
    public void sendClick(TheaterReport report) {
        Toast.makeText(this, report.getDate().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void editClick(String id) {
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();
    }

    @Override
    public void deleteClick(TheaterReport report) {
        Toast.makeText(this, report.getDate().toString() + "delete", Toast.LENGTH_LONG).show();
    }

    @Override
    public void detailClick(String id) {
        Intent intent = new Intent(this, ThReportDetail.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
