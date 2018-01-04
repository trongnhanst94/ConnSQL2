package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Excel_Adapter extends BaseAdapter implements Filterable{
    Context c;
    ArrayList<Sanpham> modelList;
    CustomFilter filter;
    ArrayList<Sanpham> filterList;
    public Excel_Adapter(Context ctx,ArrayList<Sanpham> players) {
        this.c=ctx;
        this.modelList=players;
        this.filterList=players;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Sanpham getItem(int position) {
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
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Sanpham item = getItem(position);
        vh.textViewma.setText("MSP: " + item.getMa());
        vh.textViewten.setText("  " + item.getTen());
        vh.textViewnguon.setText("Nguồn: " + item.getNguon());
        vh.textViewbaohanh.setText("Bảo hành: " + item.getBaohanh());
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
        public final RelativeLayout rootView;

        public final TextView textViewten;
        public final TextView textViewma;
        public final TextView textViewnguon;
        public final TextView textViewbaohanh;
        public final TextView textViewgiaban;

        private ViewHolder(RelativeLayout rootView, TextView textViewma, TextView textViewten,
                           TextView textViewbaohanh, TextView textViewnguon, TextView textViewgiaban) {
            this.rootView = rootView;
            this.textViewten = textViewten;
            this.textViewma = textViewma;
            this.textViewbaohanh = textViewbaohanh;
            this.textViewnguon = textViewnguon;
            this.textViewgiaban = textViewgiaban;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewma = (TextView) rootView.findViewById(R.id.textViewma);
            TextView textViewten = (TextView) rootView.findViewById(R.id.textViewten);
            TextView textViewbaohanh = (TextView) rootView.findViewById(R.id.textViewbaohanh);
            TextView textViewnguon = (TextView) rootView.findViewById(R.id.textViewnguon);
            TextView textViewgiaban = (TextView) rootView.findViewById(R.id.textViewgia);

             return new ViewHolder(rootView, textViewma, textViewten, textViewbaohanh, textViewnguon, textViewgiaban);
        }
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
                ArrayList<Sanpham> filters=new ArrayList<Sanpham>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getTen().toUpperCase().contains(constraint) || filterList.get(i).getMa().toUpperCase().contains(constraint))
                    {
                        Sanpham p= filterList.get(i);
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
            modelList=(ArrayList<Sanpham>) results.values;
            notifyDataSetChanged();
        }
    }
}
