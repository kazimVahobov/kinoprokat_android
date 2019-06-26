package com.example.kinoprokat.common_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kinoprokat.R;
import com.example.kinoprokat.models.TheaterReport;

public class ThReportDetailWithoutContInfo extends Fragment {

    private static final String ARG_PARAM1 = "report";

    // TODO: Rename and change types of parameters
    private TheaterReport report;

    public ThReportDetailWithoutContInfo() {
        // Required empty public constructor
    }

    public static ThReportDetailWithoutContInfo newInstance(TheaterReport report) {
        ThReportDetailWithoutContInfo fragment = new ThReportDetailWithoutContInfo();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.th_fr_report_detail_without_cont_info, container, false);
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
