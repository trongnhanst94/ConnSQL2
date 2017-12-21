package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.CountSanpham;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 10/4/2017.
 */

public class Adapter_Ketqua_Kiemkho extends BaseAdapter{
    private Context context;
    private int layout;
    List<CountSanpham> arrayList;

    public Adapter_Ketqua_Kiemkho(Context context, int layout, List<CountSanpham> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView tvKQKKma, tvKQKKsoluong;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvKQKKma      = (TextView) view.findViewById(R.id.tvKQKKma);
            holder.tvKQKKsoluong = (TextView) view.findViewById(R.id.tvKQKKsoluong);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        CountSanpham countSanpham = arrayList.get(i);

        holder.tvKQKKma.setText("Mã: "+countSanpham.getMasanpham());
        holder.tvKQKKsoluong.setText(countSanpham.getSoluong()+"");
        return view;
    }
}
