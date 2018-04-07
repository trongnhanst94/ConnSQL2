package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Kho.Main_Nhapkho;
import com.example.windows10gamer.connsql.Object.Kho_Dialog;
import com.example.windows10gamer.connsql.Object.Nhapkho;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by EVRESTnhan on 4/7/2018.
 */

public class Adapter_Nhapkho extends BaseAdapter implements Serializable, Filterable {
    private Main_Nhapkho context;
    private ArrayList<Nhapkho> sanphams;
    CustomFilter filter;
    ArrayList<Nhapkho> filterList;
    private int layout;
    ArrayList<Kho_Dialog> kho_dialog = new ArrayList<>();

    public Adapter_Nhapkho(Main_Nhapkho context, int layout, ArrayList<Nhapkho> sanphams) {
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
                ArrayList<Nhapkho> filters=new ArrayList<Nhapkho>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getTen().toUpperCase().contains(constraint) || filterList.get(i).getMa().toUpperCase().contains(constraint))
                    {
                        Nhapkho p= filterList.get(i);
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
            sanphams=(ArrayList<Nhapkho>) results.values;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder{
        TextView tvTen, tvMa, tvSoluong, tvBaohanh, tvNguon, tvGia;
        LinearLayout lnKho;
        CheckBox cbduyetnhap;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvTen = view.findViewById(R.id.tvTen);
            holder.tvSoluong = view.findViewById(R.id.tvSoluong);
            holder.tvBaohanh = view.findViewById(R.id.tvBaohanh);
            holder.tvGia = view.findViewById(R.id.tvGia);
            holder.tvNguon = view.findViewById(R.id.tvNguon);
            holder.tvMa = view.findViewById(R.id.tvMa);
            holder.lnKho = view.findViewById(R.id.lnKho);
            holder.cbduyetnhap = view.findViewById(R.id.cbduyetnhap);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Nhapkho sanpham = sanphams.get(i);

        holder.tvTen.setText(sanpham.getTen());
        holder.tvSoluong.setText(sanpham.getSoluong()+"");
        holder.tvGia.setText("Giá bán: "+ Keys.setMoney(Integer.parseInt(sanpham.getGia())));
        holder.tvBaohanh.setText("Bảo hành: " + sanpham.getBaohanh());
        holder.tvNguon.setText("Nguồn: "+ sanpham.getNguon());
        holder.tvMa.setText("Mã: " + sanpham.getMa());
        if (sanpham.getTrangthai() == 1){
            holder.cbduyetnhap.setChecked(TRUE);
        } else {
            holder.cbduyetnhap.setChecked(FALSE);
        }
        holder.cbduyetnhap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Check(i);
                } else {
                    Uncheck(i);
                }
            }
        });
        return view;
    }

    private void Check(int position) {
        context.check(position);
    }

    private void Uncheck(int position) {
        context.uncheck(position);
    }
}

