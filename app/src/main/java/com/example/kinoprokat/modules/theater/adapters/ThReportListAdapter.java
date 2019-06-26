package com.example.kinoprokat.modules.theater.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kinoprokat.R;
import com.example.kinoprokat.models.TheaterReport;
import com.example.kinoprokat.services.RoleService;
import com.example.kinoprokat.services.TheaterReportService;

import java.util.Calendar;
import java.util.List;

public class ThReportListAdapter extends RecyclerView.Adapter<ThReportListAdapter.ViewHolder> {

    private List<TheaterReport> reports;
    private Context context;
    private ThReportListListener listListener;
    private TheaterReportService service;
    private RoleService roleService;
    private String permissionKey;

    public ThReportListAdapter(List<TheaterReport> reports, Context context) {
        this.reports = reports;
        this.context = context;
        this.service = TheaterReportService.getInstance();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        listListener = (ThReportListListener) context;
        roleService = RoleService.getInstance();
        permissionKey = roleService.th_report_key;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.th_item_report_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final TheaterReport report = reports.get(position);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(report.getDate());

        String date_str = DateUtils.formatDateTime(context, calendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
        String session_str = Integer.toString(report.getWithCont().size()) + " " + context.getResources().getString(R.string.sessions_short_dot);
        String tickets_str = Integer.toString(service.getTicketsCount(report)) + " " + context.getResources().getString(R.string.tickets_short_dot);
        String sum_str = Integer.toString(service.getSum(report)) + " " + context.getResources().getString(R.string.sum_short_dot);
        String status_str = service.getReportStatus(context, report);

        holder.date.setText(date_str);
        holder.status.setText(status_str);
        holder.sessions_count.setText(session_str);
        holder.tickets_count.setText(tickets_str);
        holder.sum.setText(sum_str);

        if (service.getReportStatus(report) == 1) {

            if (roleService.checkPermission(0, permissionKey)) {
                holder.send.setVisibility(View.VISIBLE);
                holder.send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listListener.sendClick(report);
                    }
                });
            } else {
                holder.send.setVisibility(View.GONE);
            }

            if (roleService.checkPermission(2, permissionKey)) {
                holder.edit.setVisibility(View.VISIBLE);
                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listListener.editClick(report.get_id());
                    }
                });
            } else {
                holder.edit.setVisibility(View.GONE);
            }

            if (roleService.checkPermission(3, permissionKey)) {
                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listListener.deleteClick(report);
                    }
                });
            } else {
                holder.delete.setVisibility(View.GONE);
            }
        } else {
            holder.send.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }



        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listListener.detailClick(report.get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, status, sessions_count, tickets_count, sum;
        ImageButton send, edit, delete, detail;

        public ViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date);
            status = (TextView) itemView.findViewById(R.id.status);
            sessions_count = (TextView) itemView.findViewById(R.id.sessions_count);
            tickets_count = (TextView) itemView.findViewById(R.id.tickets_count);
            sum = (TextView) itemView.findViewById(R.id.sum);

            send = (ImageButton) itemView.findViewById(R.id.send);
            edit = (ImageButton) itemView.findViewById(R.id.edit);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
            detail = (ImageButton) itemView.findViewById(R.id.detail);
        }
    }

    public interface ThReportListListener{
        void sendClick(TheaterReport report);
        void editClick(String id);
        void deleteClick(TheaterReport report);
        void detailClick(String id);
    }
}
