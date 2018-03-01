package com.example.windows10gamer.connsql.Adapter;

import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.TienTraVe;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;
import com.example.windows10gamer.connsql.Remove_Data.Main_Remove_Trave;

import java.util.List;

/**
 * Created by EVRESTnhan on 3/1/2018.
 */

public class Adapter_Remove_Tientrave extends ArrayAdapter<TienTraVe> {

    List<TienTraVe> contactList;
    Main_Remove_Trave context;
    private LayoutInflater mInflater;

    // Constructors
    public Adapter_Remove_Tientrave(Main_Remove_Trave context, List<TienTraVe> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public TienTraVe getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.adapter_removeorder, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final TienTraVe item = getItem(position);
        vh.tvRLgio.setText(item.getCa()+" - "+item.getNgay());
        vh.tvRLten.setText(item.getChinhanh());
        vh.tvRLnhanvien.setText(item.getTenNV());
        vh.tvRLgia.setText(Keys.setMoney(Integer.parseInt(item.getSotien())));
        vh.tvRLgio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    builder = new android.support.v7.app.AlertDialog.Builder(context);
                } else {
                    builder = new android.support.v7.app.AlertDialog.Builder(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                }
                builder.setIcon(R.drawable.ic_settings);
                builder.setMessage("Bạn muốn xóa đơn hàng lúc "+item.getCa()+" ngày "+item.getNgay()+"?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        xacNhanXoa(item.getMaTV());
                    }
                });
                builder.show();
            }
        });

        return vh.rootView;
    }

    private void xacNhanXoa(final String id) {
        if(!Connect_Internet.checkConnection(context))
            Connect_Internet.buildDialog(context).show();
        else {
            context.Delete(id);
        }
    }


    private static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView tvRLgio;
        public final TextView tvRLten;
        public final TextView tvRLgia;
        public final TextView tvRLnhanvien;

        public ViewHolder(LinearLayout rootView, TextView tvRLgio, TextView tvRLten, TextView tvRLgia, TextView tvRLnhanvien) {
            this.rootView = rootView;
            this.tvRLgio = tvRLgio;
            this.tvRLten = tvRLten;
            this.tvRLgia = tvRLgia;
            this.tvRLnhanvien = tvRLnhanvien;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView tvRLgia = rootView.findViewById(R.id.tvRLgia);
            TextView tvRLgio = rootView.findViewById(R.id.tvRLgio);
            TextView tvRLten = rootView.findViewById(R.id.tvRLten);
            TextView tvRLnhanvien = rootView.findViewById(R.id.tvRLnhanvien);
            return new ViewHolder(rootView, tvRLgio, tvRLten, tvRLgia, tvRLnhanvien);
        }
    }
}
