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
        vh.tvMaOrder.setText(Keys.trimText(item.getTenSanpham(), Keys.MAX_LENGHT));
        vh.tvTennvOrder.setText(item.getTenNhanvien());
        vh.tvTimeOrder.setText(" "+item.getCalam()+ " "+item.getNgay());

        return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView tvMaOrder;
        public final TextView tvTennvOrder;
        public final TextView tvTimeOrder;

        public ViewHolder(LinearLayout rootView, TextView tvMaOrder, TextView tvTennvOrder, TextView tvTimeOrder) {
            this.rootView = rootView;
            this.tvMaOrder = tvMaOrder;
            this.tvTennvOrder = tvTennvOrder;
            this.tvTimeOrder = tvTimeOrder;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView tvTimeOrder = (TextView) rootView.findViewById(R.id.tvTimeOrder);
            TextView tvMaOrder = (TextView) rootView.findViewById(R.id.tvMaOrder);
            TextView tvTennvOrder = (TextView) rootView.findViewById(R.id.tvTennvOrder);
            return new ViewHolder(rootView, tvMaOrder, tvTennvOrder,tvTimeOrder);
        }
    }
}