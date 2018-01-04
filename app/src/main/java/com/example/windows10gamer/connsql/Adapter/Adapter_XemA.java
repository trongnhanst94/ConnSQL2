package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Kiem_Kho.Main_Kiemkho_XemA;
import com.example.windows10gamer.connsql.Object.Sanpham_ID;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by EVRESTnhan on 10/1/2017.
 */

public class Adapter_XemA extends BaseAdapter implements Serializable, Filterable {
    private Main_Kiemkho_XemA context;
    private ArrayList<Sanpham_ID> sanphams;
    CustomFilter filter;
    ArrayList<Sanpham_ID> filterList;
    private int layout;

    public Adapter_XemA(Main_Kiemkho_XemA context, int layout, ArrayList<Sanpham_ID> sanphams) {
        this.context = context;
        this.sanphams = sanphams;
        this.layout = layout;
        this.filterList = sanphams;
    }

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter = new CustomFilter();
        }
        return filter;
    }
    //INNER CLASS
    class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
            FilterResults results=new FilterResults();
            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();
                ArrayList<Sanpham_ID> filters=new ArrayList<Sanpham_ID>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getTen().toUpperCase().contains(constraint) || filterList.get(i).getMa().toUpperCase().contains(constraint))
                    {
                        Sanpham_ID p= filterList.get(i);
                        filters.add(p);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }else
            {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            sanphams=(ArrayList<Sanpham_ID>) results.values;
            notifyDataSetChanged();
        }
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

        final Sanpham_ID sanpham = sanphams.get(i);

        holder.tvAdapterSalesTen.setText("SP: "+sanpham.getTen());
        holder.tvAdapterSalesMa.setText("MSP: "+ sanpham.getMa());
        holder.tvAdapterSalesGia.setText("Giá bán: "+ Keys.setMoney(Integer.parseInt(sanpham.getGiaban())));
        holder.tvAdapterSalesBaohanh.setText("Bảo hành: " + sanpham.getBaohanh());
        holder.tvAdapterSalesNgaynhap.setText("Quét lúc: "+ sanpham.getGio());
        holder.tvAdapterSalesNguon.setText("Nguồn: " + sanpham.getNguon());

        holder.ivSalesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = sanpham.getTen();
                String msp = sanpham.getMa();
                String id = sanpham.getId();
                xacNhanXoa(ten, msp, id);
            }
        });

        return view;
    }

    private void xacNhanXoa(String ten, final String msp, final String id) {
        if(!Connect_Internet.checkConnection(context))
            Connect_Internet.buildDialog(context).show();
        else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("Bạn có muốn xóa sản phẩm" + ten + " ?");
            dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    context.DeleteSP(msp, id);
                    notifyDataSetChanged();
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

