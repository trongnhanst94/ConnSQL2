package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Datcoc;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.List;

public class Adapter_Datcoc extends ArrayAdapter<Datcoc> {

    List<Datcoc> contactList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public Adapter_Datcoc(Context context, List<Datcoc> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public Datcoc getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.adapter_datcoc, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Datcoc item = getItem(position);
        String trangthai = "0";
        if (Integer.valueOf(item.getTrangthai()) == 0){
            trangthai = "Đặt cọc";
        } else {
            trangthai = "Đã hoàn cọc";
        }
        vh.tvMaOrder.setText(Keys.trimText(item.getTenkhachhang(), Keys.MAX_LENGHT));
        vh.tvTennvOrder.setText(item.getTenNV());
        vh.tvtrangthai.setText(trangthai+"");
        if (item.getTrangthai().equals("0")){
            vh.tvtrangthai.setTextColor(ContextCompat.getColor(context, R.color.holo_xanh));
        } else {
            vh.tvtrangthai.setTextColor(ContextCompat.getColor(context, R.color.xanh_la));
        }
        vh.tvTimeOrder.setText(" "+item.getCa()+ " "+item.getNgay());
        vh.tvSoluong.setText(Keys.setMoney(Integer.valueOf(item.getSotien())));
        return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView tvMaOrder;
        public final TextView tvTennvOrder;
        public final TextView tvTimeOrder;
        public final TextView tvSoluong;
        public final TextView tvtrangthai;

        public ViewHolder(LinearLayout rootView, TextView tvMaOrder, TextView tvTennvOrder, TextView tvTimeOrder, TextView tvSoluong, TextView tvtrangthai) {
            this.rootView = rootView;
            this.tvMaOrder = tvMaOrder;
            this.tvTennvOrder = tvTennvOrder;
            this.tvTimeOrder = tvTimeOrder;
            this.tvSoluong = tvSoluong;
            this.tvtrangthai = tvtrangthai;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView tvTimeOrder = (TextView) rootView.findViewById(R.id.tvTimeOrder);
            TextView tvMaOrder = (TextView) rootView.findViewById(R.id.tvMaOrder);
            TextView tvTennvOrder = (TextView) rootView.findViewById(R.id.tvTennvOrder);
            TextView tvSoluong = (TextView) rootView.findViewById(R.id.tvSoluong);
            TextView tvtrangthai = (TextView) rootView.findViewById(R.id.tvtrangthai);
            return new ViewHolder(rootView, tvMaOrder, tvTennvOrder,tvTimeOrder, tvSoluong, tvtrangthai);
        }
    }
}