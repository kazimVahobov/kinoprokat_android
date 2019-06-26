package com.example.kinoprokat.common_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kinoprokat.R;
import com.example.kinoprokat.models.TheaterReport;

public class ThReportDetailInfoBySessions extends Fragment {

    private static final String ARG_PARAM1 = "report";
    private TheaterReport report;

    public ThReportDetailInfoBySessions() {
        // Required empty public constructor
    }

    public static ThReportDetailInfoBySessions newInstance(TheaterReport report) {
        ThReportDetailInfoBySessions fragment = new ThReportDetailInfoBySessions();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, report);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.th_fr_report_detail_info_by_sessions, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
