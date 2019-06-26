package com.example.kinoprokat.modules.theater.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kinoprokat.R;
import com.example.kinoprokat.models.TheaterReport;
import com.example.kinoprokat.services.NetworkService;
import com.example.kinoprokat.services.TheaterReportService;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThMainPageTodayReport extends Fragment implements View.OnClickListener {

    private FloatingActionButton fab_add, fab_option, fab_edit, fab_send, fab_detail;
    private Animation anim_fab_open, anim_fab_close, anim_rotate_forward, anim_rotate_backward;
    private boolean isOpen = false;
    private View mainView;
    private TextView today_report_status;
    private NetworkService networkService;
    private TheaterReportService thReportService;
    private LinearLayout msg_layout;
    private RelativeLayout option_layout;
    private ProgressBar progressBar;
    private boolean canEdit = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.th_fr_main_page_today_report, container, false);
        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        findViews();
        getTodayReport();
    }

    private void findViews() {
        networkService = NetworkService.getInstance();
        thReportService = TheaterReportService.getInstance();

        fab_add = (FloatingActionButton) mainView.findViewById(R.id.add);
        fab_add.setOnClickListener(this);

        fab_option = (FloatingActionButton) mainView.findViewById(R.id.option);
        fab_option.setOnClickListener(this);

        fab_edit = (FloatingActionButton) mainView.findViewById(R.id.edit);
        fab_edit.setOnClickListener(this);

        fab_send = (FloatingActionButton) mainView.findViewById(R.id.send);
        fab_send.setOnClickListener(this);

        fab_detail = (FloatingActionButton) mainView.findViewById(R.id.detail);
        fab_detail.setOnClickListener(this);

        anim_fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        anim_fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        anim_rotate_forward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        anim_rotate_backward= AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);

        today_report_status = (TextView) mainView.findViewById(R.id.today_report_status);

        msg_layout = (LinearLayout) mainView.findViewById(R.id.msg_layout);
        option_layout = (RelativeLayout) mainView.findViewById(R.id.option_layout);
        progressBar = (ProgressBar) mainView.findViewById(R.id.progress_bar);
    }

    private void getTodayReport() {
        progressBar.setVisibility(View.VISIBLE);
        networkService.getApies().getThReportsByThId(networkService.getJobId()).enqueue(new Callback<List<TheaterReport>>() {
            @Override
            public void onResponse(Call<List<TheaterReport>> call, Response<List<TheaterReport>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {
                        setTodayReportStatus(thReportService.defineReportStatusByDate(response.body(), new Date()));
                    } else {
                        setTodayReportStatus(0);
                    }
                } else {
                    setTodayReportStatus(4);
                }
            }

            @Override
            public void onFailure(Call<List<TheaterReport>> call, Throwable t) {
                setTodayReportStatus(4);
            }
        });
    }

//    if status = 0 - not prepared
//    if status = 1 - prepared && no send
//    if status = 2 - send && no confirm
//    if status = 3 - send && confirm
    private void setTodayReportStatus(int status) {
        progressBar.setVisibility(View.GONE);
        msg_layout.setVisibility(View.VISIBLE);
        switch (status) {
            case 0: {
                today_report_status.setText(getString(R.string.not_prepared));
                option_layout.setVisibility(View.VISIBLE);
                fab_option.setVisibility(View.GONE);
                fab_add.setVisibility(View.VISIBLE);
                canEdit = false;
                break;
            }
            case 1: {
                today_report_status.setText(getString(R.string.prepared_no_send));
                option_layout.setVisibility(View.VISIBLE);
                fab_option.setVisibility(View.VISIBLE);
                fab_add.setVisibility(View.GONE);
                canEdit = true;
                break;
            }
            case 2: {
                today_report_status.setText(getString(R.string.send_no_confirm));
                option_layout.setVisibility(View.VISIBLE);
                fab_option.setVisibility(View.VISIBLE);
                fab_add.setVisibility(View.GONE);
                canEdit = false;
                break;
            }
            case 3: {
                today_report_status.setText(getString(R.string.send_confirm));
                option_layout.setVisibility(View.VISIBLE);
                fab_option.setVisibility(View.VISIBLE);
                fab_add.setVisibility(View.GONE);
                canEdit = false;
                break;
            }
            case 4: {
                today_report_status.setText(getString(R.string.error_of_network));
            }
        }
    }

    private void animateFabs() {
        if (isOpen) {
            fab_option.startAnimation(anim_rotate_backward);
            fab_detail.startAnimation(anim_fab_close);
            fab_detail.setClickable(false);
            if (canEdit) {
                fab_edit.startAnimation(anim_fab_close);
                fab_send.startAnimation(anim_fab_close);
                fab_edit.setClickable(false);
                fab_send.setClickable(false);
            }
            isOpen = false;
        } else {
            fab_option.startAnimation(anim_rotate_forward);
            fab_detail.startAnimation(anim_fab_open);
            fab_detail.setClickable(true);
            if (canEdit) {
                fab_edit.startAnimation(anim_fab_open);
                fab_send.startAnimation(anim_fab_open);
                fab_edit.setClickable(true);
                fab_send.setClickable(true);
            }
            isOpen = true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add: {
                break;
            }
            case R.id.option: {
                animateFabs();
                break;
            }
            case R.id.edit: {
                break;
            }
            case R.id.send: {
                break;
            }
            case R.id.detail: {
                break;
            }
        }
    }
}
