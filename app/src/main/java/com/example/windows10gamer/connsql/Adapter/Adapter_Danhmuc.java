package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Danhmuc;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 3/13/2018.
 */

public class Adapter_Danhmuc extends ArrayAdapter<Danhmuc> {
    Context context;
    List<Danhmuc> list;
    LayoutInflater mInflater;

    public Adapter_Danhmuc(Context context, List<Danhmuc> list) {
        super(context, 0, list);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
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
            TextView tvTimeOrder = rootView.findViewById(R.id.tvTimeOrder);
            TextView tvMaOrder = rootView.findViewById(R.id.tvMaOrder);
            TextView tvTennvOrder = rootView.findViewById(R.id.tvTennvOrder);
            return new ViewHolder(rootView, tvMaOrder, tvTennvOrder,tvTimeOrder);
        }
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

        Danhmuc item = getItem(position);
        vh.tvMaOrder.setText(Keys.trimText(item.getTen(), Keys.MAX_LENGHT));
        vh.tvTennvOrder.setText(item.getTennhanvien());
        vh.tvTimeOrder.setText(" "+item.getLoai());

        return vh.rootView;
    }

    @Override
    public Danhmuc getItem(int position) {
        return list.get(position);
    }


}
