package com.example.kinoprokat.services;

import android.content.Context;

import com.example.kinoprokat.R;
import com.example.kinoprokat.models.ReportWithCont;
import com.example.kinoprokat.models.TheaterReport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TheaterReportService {

    private static TheaterReportService mInstance;

    public static TheaterReportService getInstance() {
        if (mInstance == null) {
            mInstance = new TheaterReportService();
        }
        return mInstance;
    }

    public int defineReportStatusByDate(List<TheaterReport> reports, Date date) {
        int result = 0;
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(date);
        Calendar reportCalendar = Calendar.getInstance();
        for (TheaterReport report : reports) {
            reportCalendar.setTime(report.getDate());
            if (currentCalendar.get(Calendar.YEAR) == reportCalendar.get(Calendar.YEAR) && currentCalendar.get(Calendar.DAY_OF_YEAR) == reportCalendar.get(Calendar.DAY_OF_YEAR)) {
                result = getReportStatus(report);
            }
        }
        return result;
    }

    public int getReportStatus(TheaterReport report) {
        int result = 1;
        if (report.isSent() && !report.isConfirm()) {
            result = 2;
        } else if (report.isSent() && report.isConfirm()) {
            result = 3;
        }
        return result;
    }

    public String getReportStatus(Context context, TheaterReport report) {
        String result = context.getString(R.string.prepared_no_send);
        if (report.isSent() && !report.isConfirm()) {
            result = context.getString(R.string.send_no_confirm);
        } else if (report.isSent() && report.isConfirm()) {
            result = context.getString(R.string.send_confirm);
        }
        return result;
    }

    public int getMoviesCount(List<TheaterReport> reports) {
        List<TheaterReport> filteredReports = new ArrayList<>();
        for (TheaterReport report : reports) {
            if (report.isSent() && report.isConfirm()) filteredReports.add(report);
        }
        List<String> moviesId = new ArrayList<>();
        if (filteredReports.size() > 0) {
            for (TheaterReport report : filteredReports) {
                for (ReportWithCont session : report.getWithCont()) {
                    if (!moviesId.contains(session.getMovieId()))
                        moviesId.add(session.getMovieId());
                }
            }
            return moviesId.size();
        } else return 0;
    }

    public int getMoviesCount(TheaterReport report) {
        List<String> moviesId = new ArrayList<>();
        if (report.getWithCont().size() == 0) return 0;
        for (ReportWithCont session : report.getWithCont()) {
            if (!moviesId.contains(session.getMovieId()))
                moviesId.add(session.getMovieId());
        }
        return moviesId.size();
    }

    public int getSessionsCount(List<TheaterReport> reports) {
        List<TheaterReport> filteredReports = new ArrayList<>();
        for (TheaterReport report : reports) {
            if (report.isSent() && report.isConfirm()) filteredReports.add(report);
        }
        int sessions = 0;
        if (filteredReports.size() > 0) {
            for (TheaterReport report : filteredReports) {
                sessions += report.getWithCont().size();
            }
            return sessions;
        } else return 0;
    }

    public int getTicketsCount(List<TheaterReport> reports) {
        List<TheaterReport> filteredReports = new ArrayList<>();
        for (TheaterReport report : reports) {
            if (report.isSent() && report.isConfirm()) filteredReports.add(report);
        }
        int tickets = 0;
        if (filteredReports.size() > 0) {
            for (TheaterReport report : filteredReports) {
                for (ReportWithCont session : report.getWithCont()) {
                    tickets += session.getAdultTicketCount() + session.getChildTicketCount();
                }
            }
            return tickets;
        } else return 0;
    }

    public int getTicketsCount(TheaterReport report) {
        int tickets = 0;
        for (ReportWithCont session : report.getWithCont()) {
            tickets += session.getAdultTicketCount() + session.getChildTicketCount();
        }
        return tickets;
    }

    public int getSum(TheaterReport report) {
        int sum = 0;
        for (ReportWithCont session: report.getWithCont()) {
            sum += (session.getChildTicketCount() * session.getChildTicketPrice()) + (session.getAdultTicketCount() * session.getAdultTicketPrice());
        }
        return sum;
    }
}
