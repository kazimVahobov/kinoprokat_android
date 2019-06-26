package com.example.kinoprokat.models;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TheaterReport implements Serializable {

    private String _id;
    private String theaterId;
    private Date date;
    private List<ReportWithCont> withCont;
    private List<ReportWithoutCont> withoutCont;
    private boolean sent;
    private boolean confirm;

    public String get_id() {
        return _id;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(String theaterId) {
        this.theaterId = theaterId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ReportWithCont> getWithCont() {
        return withCont;
    }

    public void setWithCont(List<ReportWithCont> withCont) {
        this.withCont = withCont;
    }

    public List<ReportWithoutCont> getWithoutCont() {
        return withoutCont;
    }

    public void setWithoutCont(List<ReportWithoutCont> withoutCont) {
        this.withoutCont = withoutCont;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public static final Comparator<TheaterReport> compareByDate = new Comparator<TheaterReport>() {
        @Override
        public int compare(TheaterReport report, TheaterReport report1) {
            return report1.getDate().compareTo(report.getDate());
        }
    };
}
