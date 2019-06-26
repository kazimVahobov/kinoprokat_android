package com.example.kinoprokat.common_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kinoprokat.R;
import com.example.kinoprokat.models.TheaterReport;

public class ThReportDetailCommonInfo extends Fragment {

    private static final String ARG_PARAM = "report";

    private TheaterReport report;

    private View mainView;
    private TextView name, date, status, sessions, child_tickets, child_sum, adult_ticket, adult_sum;

    public ThReportDetailCommonInfo() {
        // Required empty public constructor
    }

    public static ThReportDetailCommonInfo newInstance(TheaterReport report) {
        ThReportDetailCommonInfo fragment = new ThReportDetailCommonInfo();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, report);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            report = (TheaterReport) getArguments().getSerializable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.th_fr_report_detail_common_info, container, false);
        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        findView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (report != null) {

        }
    }

    private void findView() {
        name = (TextView) mainView.findViewById(R.id.name);
        date = (TextView) mainView.findViewById(R.id.date);
        status = (TextView) mainView.findViewById(R.id.status);
        sessions = (TextView) mainView.findViewById(R.id.sessions_count);
        child_tickets = (TextView) mainView.findViewById(R.id.child_tickets_count);
        child_sum = (TextView) mainView.findViewById(R.id.child_sum);
        adult_ticket = (TextView) mainView.findViewById(R.id.adult_tickets_count);
        adult_sum = (TextView) mainView.findViewById(R.id.adult_sum);
    }
}
