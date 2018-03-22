package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Icon;
import com.example.windows10gamer.connsql.R;

import java.util.List;


/**
 * Created by EVRESTnhan on 3/13/2018.
 */

public class Adapter_Icon extends RecyclerView.Adapter<Adapter_Icon.ViewHolder>{
    List<Icon> list;
    OnCallBack onCallBack;
    Context context;

    public Adapter_Icon(Context context, OnCallBack onCallBack, List<Icon> list) {
        this.list = list;
        this.context = context;
        this.onCallBack = onCallBack;
    }

    @Override
    public Adapter_Icon.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_icon, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(Adapter_Icon.ViewHolder holder, int position) {
        final Icon icon = list.get(position);
        holder.ivicon.setImageResource(icon.getLink());
        holder.tvname.setText(icon.getName());
        holder.ivicon.setBackgroundResource(icon.getColor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvname;
        ImageButton ivicon;

        public ViewHolder(View itemView) {
            super(itemView);
            ivicon = itemView.findViewById(R.id.ivicon);
            tvname = itemView.findViewById(R.id.tvname);
            ivicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCallBack.ItemClicked(getPosition());
                }
            });
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_icon);
            itemView.startAnimation(animation);
        }
    }

    public interface OnCallBack{
        void ItemClicked(int position);
    }
}
