package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Main_Sales;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EVRESTnhan on 10/1/2017.
 */

public class Adapter_Sales extends BaseAdapter implements Serializable{
    private Main_Sales context;
    private List<Sanpham> sanphams;
    private int layout;

    public Adapter_Sales(Main_Sales context, int layout, List<Sanpham> sanphams) {
        this.context = context;
        this.sanphams = sanphams;
        this.layout = layout;
    }

    private class ViewHolder{
        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia;
        ImageView ivSalesDelete;
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
            holder.tvAdapterSalesMa = (TextView) view.findViewById(R.id.tvAdapterSalesMa);
            holder.tvAdapterSalesTen = (TextView) view.findViewById(R.id.tvAdapterSalesTen);
            holder.tvAdapterSalesGia = (TextView) view.findViewById(R.id.tvAdapterSalesGia);
            holder.ivSalesDelete = (ImageView) view.findViewById(R.id.ivSalesDelete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Sanpham sanpham = sanphams.get(i);

        holder.tvAdapterSalesTen.setText(sanpham.getTen());
        holder.tvAdapterSalesMa.setText("MSP: "+ sanpham.getMa());
        holder.tvAdapterSalesGia.setText("Giá bán: "+ Keys.getFormatedAmount(Integer.parseInt(sanpham.getGiaban())));

        holder.ivSalesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = sanpham.getTen();
                String msp = sanpham.getMa();
                xacNhanXoa(ten, msp);
            }
        });

        return view;
    }

    private void xacNhanXoa(String ten, final String msp) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Bạn có muốn xóa sản phẩm" + ten + " ?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.DeleteSP(msp);
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.show();
    }

}

