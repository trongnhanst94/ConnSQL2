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

public class Adapter_Order extends ArrayAdapter<Order> {

    List<Order> contactList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public Adapter_Order(Context context, List<Order> objects) {
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
            View view = mInflater.inflate(R.layout.adapter_order, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Order item = getItem(position);
        vh.tvMaOrder.setText("Mã: "+item.getMaOrder());
        vh.tvTennvOrder.setText("| NV: "+item.getTenNVOrder());
        vh.tvTenkhOrder.setText("KH: "+item.getTenKH());
        vh.tvSdtkhOrder.setText("| Sđt: "+item.getSdtKH());
        vh.tvDateOrder.setText("| Ngày: "+Keys.setDate(item.getDateOrder()));
        vh.tvTimeOrder.setText(item.getTimeOrder());

        return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView tvTenkhOrder;
        public final TextView tvMaOrder;
        public final TextView tvTennvOrder;
        public final TextView tvSdtkhOrder;
        public final TextView tvDateOrder;
        public final TextView tvTimeOrder;

        public ViewHolder(LinearLayout rootView, TextView tvMaOrder, TextView tvTennvOrder, TextView tvTenkhOrder, TextView tvSdtkhOrder, TextView tvDateOrder, TextView tvTimeOrder) {
            this.rootView = rootView;
            this.tvTenkhOrder = tvTenkhOrder;
            this.tvMaOrder = tvMaOrder;
            this.tvTennvOrder = tvTennvOrder;
            this.tvSdtkhOrder = tvSdtkhOrder;
            this.tvDateOrder = tvDateOrder;
            this.tvTimeOrder = tvTimeOrder;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView tvTenkhOrder = (TextView) rootView.findViewById(R.id.tvTenkhOrder);
            TextView tvSdtkhOrder = (TextView) rootView.findViewById(R.id.tvSdtkhOrder);
            TextView tvDateOrder = (TextView) rootView.findViewById(R.id.tvDateOrder);
            TextView tvTimeOrder = (TextView) rootView.findViewById(R.id.tvTimeOrder);
            TextView tvMaOrder = (TextView) rootView.findViewById(R.id.tvMaOrder);
            TextView tvTennvOrder = (TextView) rootView.findViewById(R.id.tvTennvOrder);
            return new ViewHolder(rootView, tvMaOrder, tvTennvOrder, tvTenkhOrder,tvSdtkhOrder,tvDateOrder,tvTimeOrder);
        }
    }
}