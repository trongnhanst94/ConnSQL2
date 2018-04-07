package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Kho.Main_Minstock;
import com.example.windows10gamer.connsql.Object.Minstock;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by EVRESTnhan on 4/4/2018.
 */

public class Adapter_MinStock extends BaseAdapter implements Serializable, Filterable {
    Main_Minstock context;
    int layout;
    ArrayList<Minstock> list;
    CustomFilter filter;
    ArrayList<Minstock> filterList;

    public Adapter_MinStock(Main_Minstock context, int layout, ArrayList<Minstock> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.filterList = list;
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
                ArrayList<Minstock> filters=new ArrayList<Minstock>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getTen().toUpperCase().contains(constraint) || filterList.get(i).getMa().toUpperCase().contains(constraint))
                    {
                        Minstock p= filterList.get(i);
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
            list=(ArrayList<Minstock>) results.values;
            notifyDataSetChanged();
        }
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

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.tvMa = view.findViewById(R.id.tvMa);
            viewHolder.tvTen = view.findViewById(R.id.tvTen);
            viewHolder.tvMinstock = view.findViewById(R.id.tvMinstock);
            viewHolder.tvBaohanh = view.findViewById(R.id.tvBaohanh);
            viewHolder.tvGia = view.findViewById(R.id.tvGia);
            viewHolder.tvNguon = view.findViewById(R.id.tvNguon);
            viewHolder.imageView = view.findViewById(R.id.imageView);
            viewHolder.ln = view.findViewById(R.id.ln);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Minstock minstock = list.get(position);
        viewHolder.tvTen.setText(minstock.getTen());
        viewHolder.tvMinstock.setText(minstock.getMinstock());
        viewHolder.tvGia.setText("Giá bán: "+ Keys.setMoney(Integer.parseInt(minstock.getGia())));
        viewHolder.tvBaohanh.setText("Bảo hành: " + minstock.getBaohanh());
        viewHolder.tvNguon.setText("Nguồn: "+ minstock.getNguon());
        viewHolder.tvMa.setText("Mã: " + minstock.getMa());
        viewHolder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterClickItem(minstock.getId());
            }
        });
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterDelete(minstock.getId());
            }
        });
        return view;
    }

    private void AdapterDelete(String id) {
        context.Delete(id);
    }

    private void AdapterClickItem(String Cid) {
        context.ClickItem(Cid);
    }

    class ViewHolder {
        TextView tvTen, tvMa, tvMinstock, tvBaohanh, tvNguon, tvGia;
        ImageView imageView;
        LinearLayout ln;
    }
}
