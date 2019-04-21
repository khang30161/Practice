package com.example.preferences.shared.khang.practice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class RecycleviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Manager> managers;
    private Context context;
    private Action action;

    public RecycleviewAdapter(Context context, List managers, Action action) {
        this.context = context;
        this.managers = managers;
        this.action = action;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Viewholder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        if (managers.get(position) == null) {
            return;

        }
        Viewholder holder = (Viewholder) viewHolder;
        holder.tvName.setText(managers.get(position).getName());
        holder.tvMaso.setText(managers.get(position).getMaso());
        holder.tvSoluong.setText(String.valueOf(managers.get(position).getSoluong()));
        holder.llline.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (action != null)
                    action.onLongClickItem(managers.get(position), position);
                return false;
            }
        });
        holder.llline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action != null)
                    action.onClickItem(managers.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (managers != null ? managers.size() : 0);
    }

    public interface Action {
        void onClickItem(Manager manager, int position);

        void onLongClickItem(Manager manager, int position);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvMaso;
        private TextView tvSoluong;
        private RelativeLayout llline;

        public Viewholder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.main_act_tv_name);
            tvMaso = itemView.findViewById(R.id.main_act_tv_maso);
            tvSoluong = itemView.findViewById(R.id.main_act_tv_soluong);
            llline = itemView.findViewById(R.id.line);

        }
    }


}