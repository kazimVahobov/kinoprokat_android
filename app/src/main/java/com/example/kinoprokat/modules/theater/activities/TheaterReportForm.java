package com.example.kinoprokat.modules.theater.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kinoprokat.R;
import com.example.kinoprokat.adapters.ViewPagerAdapter;
import com.example.kinoprokat.dialogs.CustomAlertDialog;
import com.example.kinoprokat.models.TheaterReport;
import com.example.kinoprokat.modules.theater.fragments.ThReportFormSessions;
import com.example.kinoprokat.modules.theater.fragments.ThReportFormWithoutCont;
import com.example.kinoprokat.services.NetworkService;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheaterReportForm extends AppCompatActivity implements CustomAlertDialog.CustomDialogListener {

    public Calendar calendar;
    private NetworkService networkService;
    private CustomAlertDialog alertDialog;
    private static String ID_KEY = "id";
    private static String ALERT_KEY = "custom_alert";

    private Toolbar toolbar;
    private boolean isEdit = false;
    private String reportId = null;

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private TextView date;
    private ImageButton edit_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.th_activity_report_form);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString(ID_KEY) != null) {
            isEdit = true;
            reportId = bundle.getString(ID_KEY);
        } else {
            isEdit = false;
        }

        findViews();

    }
    
    private void findViews() {
        networkService = NetworkService.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(isEdit ? R.string.edit : R.string.new_report);
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

        if (calendar == null) {
            calendar = Calendar.getInstance();
            calendar.setTime(new Date());
        }

        date = (TextView) findViewById(R.id.date);
        edit_date = (ImageButton) findViewById(R.id.edit_date);
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit) {
                    alertDialog = CustomAlertDialog.newInstance(false, getString(R.string.warning), getString(R.string.cannot_change_date_on_editing));
                    alertDialog.show(getSupportFragmentManager(), ALERT_KEY);
                } else {
                    alertDialog = CustomAlertDialog.newInstance(true, getString(R.string.warning), getString(R.string.on_date_change_unsaved_date_has_been_lost));
                    alertDialog.show(getSupportFragmentManager(), ALERT_KEY);
                }
            }
        });

        if (reportId == null) {
            initFragments();
            setInitialDateTime();
        } else {
            getReportById(reportId);
        }
    }

    private void getReportById(String id) {
        networkService.getApies().getThReportsById(id).enqueue(new Callback<TheaterReport>() {
            @Override
            public void onResponse(Call<TheaterReport> call, Response<TheaterReport> response) {

            }

            @Override
            public void onFailure(Call<TheaterReport> call, Throwable t) {

            }
        });
    }

    private void initFragments() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ThReportFormSessions(), getString(R.string.by_contract));
        adapter.addFragment(new ThReportFormWithoutCont(), getString(R.string.without_contract));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        date.setText(DateUtils.formatDateTime(this,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    private void showDateDialog() {
        new DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    @Override
    public void customDialogConfirm() {
        showDateDialog();
    }
}
