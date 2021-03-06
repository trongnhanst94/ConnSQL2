package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by EVRESTnhan on 10/9/2017.
 */

public class Adapter_Info_Order extends BaseAdapter{
    Context c;
    ArrayList<Sanpham_gio> modelList;
    ArrayList<Sanpham_gio> filterList;
    public Adapter_Info_Order(Context ctx,ArrayList<Sanpham_gio> players) {
        this.c=ctx;
        this.modelList=players;
        this.filterList=players;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Sanpham_gio getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        LayoutInflater inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            View view = inflater.inflate(R.layout.adapter_list_excel, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Sanpham_gio item = getItem(position);
        vh.textViewma.setText("MSP: " + item.getMa());
        vh.textViewten.setText("SP: " + item.getTen());
        vh.textViewnguon.setText("Nguồn: " + item.getNguon());
        vh.textViewbaohanh.setText("Bảo hành: " + item.getBaohanh());
        vh.textViewngaynhap.setText("Quét lúc: " + item.getGio());
        if (item.getGiaban().equals("")){
            vh.textViewgiaban.setText("Giá bán: không có.");
        } else {
            vh.textViewgiaban.setText("Giá bán: " + setMoney(Integer.parseInt(item.getGiaban())));
        }

        return vh.rootView;
    }

    private String setMoney(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber+"đ";
    }

    private static class ViewHolder {
        public final LinearLayout rootView;

        public final TextView textViewten;
        public final TextView textViewma;
        public final TextView textViewnguon;
        public final TextView textViewbaohanh;
        public final TextView textViewngaynhap;
        public final TextView textViewgiaban;

        private ViewHolder(LinearLayout rootView, TextView textViewma, TextView textViewten,
                           TextView textViewbaohanh, TextView textViewnguon, TextView textViewngaynhap,  TextView textViewgiaban) {
            this.rootView = rootView;
            this.textViewten = textViewten;
            this.textViewma = textViewma;
            this.textViewngaynhap = textViewngaynhap;
            this.textViewbaohanh = textViewbaohanh;
            this.textViewnguon = textViewnguon;
            this.textViewgiaban = textViewgiaban;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView textViewma = (TextView) rootView.findViewById(R.id.textViewma);
            TextView textViewten = (TextView) rootView.findViewById(R.id.textViewten);
            TextView textViewngaynhap = (TextView) rootView.findViewById(R.id.textViewngaynhap);
            TextView textViewbaohanh = (TextView) rootView.findViewById(R.id.textViewbaohanh);
            TextView textViewnguon = (TextView) rootView.findViewById(R.id.textViewnguon);
            TextView textViewgiaban = (TextView) rootView.findViewById(R.id.textViewgia);

            return new ViewHolder(rootView, textViewma, textViewten, textViewbaohanh, textViewnguon, textViewngaynhap, textViewgiaban);
        }
    }
}
