package com.example.windows10gamer.connsql.Adapter;

import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Main_MaGiamGia;
import com.example.windows10gamer.connsql.Object.Magiamgia;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 1/10/2018.
 */

public class Adapter_Magiamgia  extends ArrayAdapter<Magiamgia> {

    List<Magiamgia> contactList;
    Main_MaGiamGia context;
    private LayoutInflater mInflater;

    // Constructors
    public Adapter_Magiamgia(Main_MaGiamGia context, List<Magiamgia> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public Magiamgia getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.adapter_magiamgia, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Magiamgia item = getItem(position);
        vh.tvten.setText(item.getTen());
        vh.tvgiatri.setText(Keys.setMoney(Integer.parseInt(item.getGiatri())));
        vh.ivxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    builder = new android.support.v7.app.AlertDialog.Builder(context);
                } else {
                    builder = new android.support.v7.app.AlertDialog.Builder(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                }
                builder.setIcon(R.drawable.icon_delete);
                builder.setMessage("Bạn muốn xóa mã giảm giá "+item.getTen()+"?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        xacNhanXoa(item.getTen());
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
        public final TextView tvten;
        public final TextView tvgiatri;
        public final ImageView ivxoa;

        public ViewHolder(LinearLayout rootView, TextView tvten, TextView tvgiatri, ImageView ivxoa) {
            this.rootView = rootView;
            this.tvten = tvten;
            this.tvgiatri = tvgiatri;
            this.ivxoa = ivxoa;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView tvten = rootView.findViewById(R.id.tvten);
            TextView tvgiatri = rootView.findViewById(R.id.tvgiatri);
            ImageView ivxoa = rootView.findViewById(R.id.ivxoa);
            return new ViewHolder(rootView, tvten, tvgiatri, ivxoa);
        }
    }
}

