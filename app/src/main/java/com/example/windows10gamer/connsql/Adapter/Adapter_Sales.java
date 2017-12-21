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
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EVRESTnhan on 10/1/2017.
 */

public class Adapter_Sales extends BaseAdapter implements Serializable{
    private Main_Sales context;
    private List<Sanpham_gio> sanphams;
    private int layout;

    public Adapter_Sales(Main_Sales context, int layout, List<Sanpham_gio> sanphams) {
        this.context = context;
        this.sanphams = sanphams;
        this.layout = layout;
    }

    private class ViewHolder{
        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia, tvAdapterSalesNguon, tvAdapterSalesBaohanh, tvAdapterSalesNgaynhap;
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
            holder.tvAdapterSalesNguon = (TextView) view.findViewById(R.id.tvAdapterSalesNguon);
            holder.tvAdapterSalesBaohanh = (TextView) view.findViewById(R.id.tvAdapterSalesBaohanh);
            holder.tvAdapterSalesNgaynhap = (TextView) view.findViewById(R.id.tvAdapterSalesNgaynhap);
            holder.tvAdapterSalesTen = (TextView) view.findViewById(R.id.tvAdapterSalesTen);
            holder.tvAdapterSalesGia = (TextView) view.findViewById(R.id.tvAdapterSalesGia);
            holder.ivSalesDelete = (ImageView) view.findViewById(R.id.ivSalesDelete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Sanpham_gio sanpham = sanphams.get(i);

        holder.tvAdapterSalesTen.setText("SP: "+sanpham.getTen());
        holder.tvAdapterSalesMa.setText("MSP: "+ sanpham.getMa());
        holder.tvAdapterSalesGia.setText("Giá bán: "+ Keys.getFormatedAmount(Integer.parseInt(sanpham.getGiaban())));
        holder.tvAdapterSalesBaohanh.setText("Bảo hành: " + sanpham.getBaohanh());
        holder.tvAdapterSalesNgaynhap.setText("Quét lúc: "+sanpham.getGio());
        holder.tvAdapterSalesNguon.setText("Nguồn: " + sanpham.getNguon());

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
        if(!Connect_Internet.checkConnection(context))
            Connect_Internet.buildDialog(context).show();
        else {
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

}

