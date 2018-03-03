package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Order_Plus;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.List;

public class Adapter_Order_Plus extends ArrayAdapter<Order_Plus> {

    List<Order_Plus> contactList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public Adapter_Order_Plus(Context context, List<Order_Plus> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public Order_Plus getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        String string;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.adapter_order, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Order_Plus item = getItem(position);

        vh.tvMaOrder.setText(Keys.trimText(item.getTenSanpham(), 30));
        vh.tvTennvOrder.setText(item.getTenNhanvien());
        vh.tvstt.setText(Keys.sothutu(position+1));
        vh.tvSoluong.setText("Amount: "+Keys.sothutu(item.getSoluong()));
        vh.tvTimeOrder.setText("["+item.getGio()+"] "+item.getCalam()+ " "+item.getNgay());

        return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView tvMaOrder;
        public final TextView tvTennvOrder;
        public final TextView tvTimeOrder;
        public final TextView tvSoluong;
        public final TextView tvstt;

        public ViewHolder(LinearLayout rootView, TextView tvstt, TextView tvMaOrder, TextView tvTennvOrder, TextView tvTimeOrder, TextView tvSoluong) {
            this.rootView = rootView;
            this.tvMaOrder = tvMaOrder;
            this.tvTennvOrder = tvTennvOrder;
            this.tvstt = tvstt;
            this.tvTimeOrder = tvTimeOrder;
            this.tvSoluong = tvSoluong;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView tvstt = rootView.findViewById(R.id.tvstt);
            TextView tvTimeOrder = rootView.findViewById(R.id.tvTimeOrder);
            TextView tvMaOrder = rootView.findViewById(R.id.tvMaOrder);
            TextView tvTennvOrder = rootView.findViewById(R.id.tvTennvOrder);
            TextView tvSoluong = rootView.findViewById(R.id.tvSoluong);
            return new ViewHolder(rootView, tvstt, tvMaOrder, tvTennvOrder,tvTimeOrder, tvSoluong);
        }
    }
}