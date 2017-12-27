package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 12/9/2017.
 */

public class Adapter_Realtime_Order extends ArrayAdapter<Order> {

    List<Order> contactList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public Adapter_Realtime_Order(Context context, List<Order> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public Order getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.adapter_realtime_order, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Order item = getItem(position);
        vh.tvRLgio.setText(item.getGio());
        vh.tvRLten.setText(Keys.trimText(item.getTenSanpham(), Keys.MAX_LENGHT));
        vh.tvRLnhanvien.setText(item.getTenNhanvien());
        vh.tvRLgia.setText(Keys.getFormatedAmount(Integer.parseInt(item.getGiaSanpham())));

        return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView tvRLgio;
        public final TextView tvRLten;
        public final TextView tvRLgia;
        public final TextView tvRLnhanvien;

        public ViewHolder(LinearLayout rootView, TextView tvRLgio, TextView tvRLten, TextView tvRLgia, TextView tvRLnhanvien) {
            this.rootView = rootView;
            this.tvRLgio = tvRLgio;
            this.tvRLten = tvRLten;
            this.tvRLgia = tvRLgia;
            this.tvRLnhanvien = tvRLnhanvien;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView tvRLgia = (TextView) rootView.findViewById(R.id.tvRLgia);
            TextView tvRLgio = (TextView) rootView.findViewById(R.id.tvRLgio);
            TextView tvRLten = (TextView) rootView.findViewById(R.id.tvRLten);
            TextView tvRLnhanvien = (TextView) rootView.findViewById(R.id.tvRLnhanvien);
            return new ViewHolder(rootView, tvRLgio, tvRLten, tvRLgia, tvRLnhanvien);
        }
    }
}