package com.example.windows10gamer.connsql.Adapter;

        import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Quatang;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EVRESTnhan on 10/23/2017.
 */

public class Adapter_QuaTangInfo extends BaseAdapter implements Serializable {
    private Context context;
    private List<Quatang> sanphams;
    private int layout;

    public Adapter_QuaTangInfo(Context context, int layout, List<Quatang> sanphams) {
        this.context = context;
        this.sanphams = sanphams;
        this.layout = layout;
    }

    private class ViewHolder{
        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia;
    }

    @Override
    public int getCount() {
        return sanphams.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvAdapterSalesTen = view.findViewById(R.id.tvAdapterSalesTenKM);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Quatang sanpham = sanphams.get(i);

        holder.tvAdapterSalesTen.setText("Quà tặng kèm: "+sanpham.getTen());
        return view;
    }
}

