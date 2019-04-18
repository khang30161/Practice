package com.example.preferences.shared.khang.practice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RecycleviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Manager> managers;
    private Context context;
    private onItemClick itemClick;
    private ChangeInterface changeInterface;

    public RecycleviewAdapter(Context context, List managers) {
        this.managers = managers;
        this.itemClick = itemClick;
    }

    public RecycleviewAdapter(MainActivity managers, String[] mainActivity) {

    }

    public void changeData(ChangeInterface changeInterface) {
        this.changeInterface = changeInterface;
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
                changeInterface.changeData(v, position, true);

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {


        return (managers.size());
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
    }

    public void setItemClick(onItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public interface onItemClick {
        void onItemClick(View view, int position, boolean b);

    }

    public interface ChangeInterface {
        void changeData(View view, int position, boolean b);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvMaso;
        private TextView tvSoluong;
        private LinearLayout llline;

        public Viewholder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.main_act_tv_name);
            tvMaso = itemView.findViewById(R.id.main_act_tv_maso);
            tvSoluong = itemView.findViewById(R.id.main_act_tv_soluong);
            llline = itemView.findViewById(R.id.line);

        }

        public void onClick(View view) {
            itemClick.onItemClick(view, getAdapterPosition(), false);
        }


    }


}