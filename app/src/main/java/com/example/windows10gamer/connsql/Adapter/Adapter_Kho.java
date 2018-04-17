package com.example.windows10gamer.connsql.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Kho.Main_KhoOnline;
import com.example.windows10gamer.connsql.Object.Kho;
import com.example.windows10gamer.connsql.Object.Kho_Dialog;
import com.example.windows10gamer.connsql.Object.Kho_Soluong;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Adapter_Kho extends BaseAdapter implements Serializable, Filterable {
    private Main_KhoOnline context;
    int level;
    private ArrayList<Kho_Soluong> sanphams;
    CustomFilter filter;
    ArrayList<Kho_Soluong> filterList;
    private int layout;
    ArrayList<Kho> orinal = new ArrayList<>();
    ArrayList<Kho_Dialog> kho_dialog = new ArrayList<>();

    public Adapter_Kho(Main_KhoOnline context, int layout, ArrayList<Kho_Soluong> sanphams, ArrayList<Kho> orinal, int level) {
        this.context = context;
        this.orinal = orinal;
        this.level = level;
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
                ArrayList<Kho_Soluong> filters=new ArrayList<Kho_Soluong>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getTen().toUpperCase().contains(constraint) || filterList.get(i).getMa().toUpperCase().contains(constraint))
                    {
                        Kho_Soluong p= filterList.get(i);
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
            sanphams=(ArrayList<Kho_Soluong>) results.values;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder{
        TextView tvTen, tvMa, tvSoluong, tvBaohanh, tvNgaynhap, tvGia;
        LinearLayout lnKho;
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
            holder.tvNgaynhap = view.findViewById(R.id.tvNgaynhap);
            holder.tvMa = view.findViewById(R.id.tvMa);
            holder.lnKho = view.findViewById(R.id.lnKho);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Kho_Soluong sanpham = sanphams.get(i);

        holder.tvTen.setText(sanpham.getTen());
        holder.tvSoluong.setText(sanpham.getSoluong()+"");
        if (level <= Keys.LEVEL_KHO){
            holder.tvGia.setText("Giá vốn: "+ Keys.setMoney(Integer.parseInt(Keys.giaimagiavon(sanpham.getVon()))));
        } else {
            holder.tvGia.setText("Giá bán: "+ Keys.setMoney(Integer.parseInt(sanpham.getGia())));
        }
        holder.tvBaohanh.setText("Bảo hành: " + sanpham.getBaohanh());
        holder.tvNgaynhap.setText("Ngày nhập: "+ sanpham.getNgaynhap());
        holder.tvMa.setText("Mã: " + sanpham.getMa());
        holder.lnKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kho_dialog.clear();
                for (int i1 = 0; i1 < orinal.size(); i1++) {
                    if (orinal.get(i1).getMa().equals(sanphams.get(i).getMa())){
                        int sosanh = iniSosanh(kho_dialog, orinal.get(i1).getChinhanh());
                        if (sosanh == -1){
                            kho_dialog.add(new Kho_Dialog(orinal.get(i1).getChinhanh(), 1));
                        } else {
                            kho_dialog.set(sosanh, new Kho_Dialog(kho_dialog.get(sosanh).getChinhanh(),kho_dialog.get(sosanh).getSoluong()+1));
                        }
                    }
                }
                for (int i1 = 0; i1 < kho_dialog.size(); i1++) {
                }
                ShowListKho(kho_dialog);
            }
        });
        return view;
    }

    private void ShowListKho(final ArrayList<Kho_Dialog> khoDialog) {
        ListView dialog_ListView;
        Adapter_ListKho adapter_listCustomer;
        Dialog dialog = null;
        dialog = new Dialog(context);
        dialog.setTitle("Chi nhánh");
        dialog.setContentView(R.layout.dialog_listkho);
        dialog_ListView = dialog.findViewById(R.id.lvkho);
        adapter_listCustomer = new Adapter_ListKho(context, R.layout.adapter_listkho, khoDialog);
        dialog_ListView.setAdapter(adapter_listCustomer);
        final Dialog finalDialog = dialog;
        dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finalDialog.dismiss();
            }});
        dialog.show();
    }

    private int iniSosanh(ArrayList<Kho_Dialog> khoList, String chinhanh) {
        int result = -1;
        if (khoList.size() > 0){
            for (int i = 0; i < khoList.size(); i++) {
                if (khoList.get(i).getChinhanh().equals(chinhanh)){
                    result = i;
                }
            }
        }
        return result;
    }
}
