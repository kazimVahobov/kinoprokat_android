package com.example.kinoprokat.modules.theater.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kinoprokat.R;
import com.example.kinoprokat.models.TheaterReport;
import com.example.kinoprokat.services.NetworkService;
import com.example.kinoprokat.services.TheaterReportService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThMainPageCommonStatistic extends Fragment {

    private CommonStatisticFragmentListener mListener;
    private View mainView;
    private TextView movies_count, sessions_count, tickets_count, error;
    private Button btn_all_reports;
    private NetworkService networkService;
    private TheaterReportService thReportService;
    private ProgressBar progressBar;
    private LinearLayout data_layout;
    private RelativeLayout load_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.th_fr_main_page_common_statistic, container, false);
        return mainView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (CommonStatisticFragmentListener) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        findView();
        getReports();
    }

    private void getReports() {
        networkService.getApies().getThReportsByThId(networkService.getJobId()).enqueue(new Callback<List<TheaterReport>>() {
            @Override
            public void onResponse(Call<List<TheaterReport>> call, Response<List<TheaterReport>> response) {
                if (response.isSuccessful()) {
                    String str_movies = Integer.toString(thReportService.getMoviesCount(response.body())) + " " + getString(R.string.movies_short_dot);
                    movies_count.setText(str_movies);
                    String str_sessions = Integer.toString(thReportService.getSessionsCount(response.body())) + " " + getString(R.string.sessions_short_dot);
                    sessions_count.setText(str_sessions);
                    String str_tickets = Integer.toString(thReportService.getTicketsCount(response.body())) + " " + getString(R.string.tickets_short_dot);
                    tickets_count.setText(str_tickets);
                    load_layout.setVisibility(View.GONE);
                    data_layout.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<TheaterReport>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void findView() {
        networkService = NetworkService.getInstance();
        thReportService = TheaterReportService.getInstance();

        data_layout = (LinearLayout) mainView.findViewById(R.id.data_layout);
        load_layout = (RelativeLayout) mainView.findViewById(R.id.load_layout);
        progressBar = (ProgressBar) mainView.findViewById(R.id.progress_bar);
        error = (TextView) mainView.findViewById(R.id.error);

        movies_count = (TextView) mainView.findViewById(R.id.movies_count);
        sessions_count = (TextView) mainView.findViewById(R.id.sessions_count);
        tickets_count = (TextView) mainView.findViewById(R.id.tickets_count);
        btn_all_reports = (Button) mainView.findViewById(R.id.all_reports);
        btn_all_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAllThReportPressed();
            }
        });
    }

    public interface CommonStatisticFragmentListener {
        void onAllThReportPressed();
    }
}
