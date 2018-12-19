package com.zoyberlo.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ActivesAdapter extends RecyclerView.Adapter<ActivesAdapter.MyViewHolder> {

    private Context context;
    private List<Active> activesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView active;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            active = view.findViewById(R.id.active);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }


    public ActivesAdapter(Context context, List<Active> activesList) {
        this.context = context;
        this.activesList = activesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Active active = activesList.get(position);
        holder.active.setText(active.getActive());
        holder.timestamp.setText(formatDate(active.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return activesList.size();
    }


    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fmtOut.format(date);

            return dateStr;
        } catch (ParseException e) {
            Logger.e(e, "PARSE EXCEPTION!");
        }
        return null;
    }
}
