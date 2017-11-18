//package com.example.windows10gamer.connsql.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.windows10gamer.connsql.Object.Sanpham;
//import com.example.windows10gamer.connsql.Other.Keys;
//import com.example.windows10gamer.connsql.R;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * Created by EVRESTnhan on 10/29/2017.
// */
//
//public class Adapter_BH extends BaseAdapter implements Serializable {
//    private Context context;
//    private List<Sanpham> sanphams;
//    private int layout;
//
//    public Adapter_BH(Context context, int layout, List<Sanpham> sanphams) {
//        this.context = context;
//        this.sanphams = sanphams;
//        this.layout = layout;
//    }
//
//    private class ViewHolder{
//        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia;
//        ImageView ivSalesDelete;
//    }
//
//    @Override
//    public int getCount() {
//        return sanphams.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        final Adapter_BH.ViewHolder holder;
//        if(view == null){
//            holder = new Adapter_BH.ViewHolder();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(layout, null);
//            holder.tvAdapterSalesMa = (TextView) view.findViewById(R.id.tvAdapterBHMa);
//            holder.tvAdapterSalesTen = (TextView) view.findViewById(R.id.tvAdapterBHTen);
//            holder.tvAdapterSalesGia = (TextView) view.findViewById(R.id.tvAdapterBHGia);
//            view.setTag(holder);
//        } else {
//            holder = (Adapter_BH.ViewHolder) view.getTag();
//        }
//
//        final Sanpham sanpham = sanphams.get(i);
//
//        holder.tvAdapterSalesTen.setText(sanpham.getTen());
//        holder.tvAdapterSalesMa.setText("MSP: "+ sanpham.getMa());
//        holder.tvAdapterSalesGia.setText("Giá bán: "+ Keys.getFormatedAmount(Integer.parseInt(sanpham.getGiaban())));
//        return view;
//    }
//
//}
//
