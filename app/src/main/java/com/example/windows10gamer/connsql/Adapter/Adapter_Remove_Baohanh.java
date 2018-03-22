package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Delete_Baohanh;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 3/20/2018.
 */

public class Adapter_Remove_Baohanh extends BaseAdapter{
    Context context;
    int layout;
    List<Delete_Baohanh> list;

    public Adapter_Remove_Baohanh(Context context, int layout, List<Delete_Baohanh> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder {
        TextView tvgio;
        TextView tvten;
        TextView tvgia;
        TextView tvnv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null){
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            vh.tvgia = convertView.findViewById(R.id.tvgia);
            vh.tvgio = convertView.findViewById(R.id.tvgio);
            vh.tvnv = convertView.findViewById(R.id.tvnv);
            vh.tvten = convertView.findViewById(R.id.tvten);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Delete_Baohanh delete_baohanh = list.get(position);
        vh.tvten.setText(delete_baohanh.getTen());
        vh.tvgio.setText(delete_baohanh.getGio());
        vh.tvgia.setText(delete_baohanh.getGia());
        vh.tvnv.setText(delete_baohanh.getTenNVToday());
        return convertView;
    }
}
