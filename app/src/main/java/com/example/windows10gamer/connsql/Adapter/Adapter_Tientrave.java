package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.TienTraVe;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 2/23/2018.
 */

public class Adapter_Tientrave extends ArrayAdapter<TienTraVe> {
    List<TienTraVe> contactList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public Adapter_Tientrave(Context context, List<TienTraVe> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public TienTraVe getItem(int position) {
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

        TienTraVe item = getItem(position);
        vh.tvMaOrder.setText(Keys.trimText(item.getNoidung(), Keys.MAX_LENGHT));
        vh.tvTennvOrder.setText(item.getTenNV());
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

        public ViewHolder(LinearLayout rootView, TextView tvMaOrder, TextView tvTennvOrder, TextView tvTimeOrder, TextView tvSoluong) {
            this.rootView = rootView;
            this.tvMaOrder = tvMaOrder;
            this.tvTennvOrder = tvTennvOrder;
            this.tvTimeOrder = tvTimeOrder;
            this.tvSoluong = tvSoluong;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView tvTimeOrder = rootView.findViewById(R.id.tvTimeOrder);
            TextView tvMaOrder = rootView.findViewById(R.id.tvMaOrder);
            TextView tvTennvOrder = rootView.findViewById(R.id.tvTennvOrder);
            TextView tvSoluong = rootView.findViewById(R.id.tvSoluong);
            return new ViewHolder(rootView, tvMaOrder, tvTennvOrder,tvTimeOrder, tvSoluong);
        }
    }
}
