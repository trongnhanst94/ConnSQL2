package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Ban_Hang.Main_Sales;
import com.example.windows10gamer.connsql.Object.SanphamAmount;
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
    private List<SanphamAmount> sanphams;
    private int layout;
    private List<String> listsoluong;
    int soluongbegin = 1;

    public Adapter_Sales(Main_Sales context, int layout, List<SanphamAmount> sanphams, List<String> listsoluong) {
        this.context = context;
        this.sanphams = sanphams;
        this.layout = layout;
        this.listsoluong = listsoluong;
    }

    private class ViewHolder{
        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia, tvAdapterSalesBaohanh, tvAdapterSalesNgaynhap;
        EditText edsoluong;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvAdapterSalesMa = view.findViewById(R.id.tvAdapterSalesMa);
            holder.edsoluong = view.findViewById(R.id.edsoluong);
            holder.tvAdapterSalesBaohanh = view.findViewById(R.id.tvAdapterSalesBaohanh);
            holder.tvAdapterSalesNgaynhap = view.findViewById(R.id.tvAdapterSalesNgaynhap);
            holder.tvAdapterSalesTen = view.findViewById(R.id.tvAdapterSalesTen);
            holder.tvAdapterSalesGia = view.findViewById(R.id.tvAdapterSalesGia);
            holder.ivSalesDelete = view.findViewById(R.id.ivSalesDelete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final SanphamAmount sanpham = sanphams.get(i);
        holder.tvAdapterSalesTen.setText("SP: "+ sanpham.getTen());
        holder.tvAdapterSalesMa.setText("MSP: "+ sanpham.getMa());
        holder.tvAdapterSalesGia.setText("Giá bán: "+ Keys.setMoney(Integer.parseInt(sanpham.getGiaban())));
        holder.tvAdapterSalesBaohanh.setText("Bảo hành: " + sanpham.getBaohanh());
        holder.tvAdapterSalesNgaynhap.setText("Quét lúc: "+ sanpham.getGio());
        holder.edsoluong.setText(sanpham.getSoluong());
        soluongbegin = Integer.valueOf(sanpham.getSoluong());
        int kiemtra = sosanh(sanpham.getMa(), listsoluong);
        if (kiemtra == -1){
            holder.edsoluong.setEnabled(false);
        }
        holder.edsoluong.setInputType(InputType.TYPE_NULL);
        holder.edsoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder dialog = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    dialog = new android.app.AlertDialog.Builder(context);
                } else {
                    dialog = new android.app.AlertDialog.Builder(context);
                }
                dialog.setIcon(R.drawable.ic_settings).setTitle("Nhập số lượng");
                View mView = context.getLayoutInflater().inflate(R.layout.dialog_soluongsales, null);
                final EditText edsoluong = mView.findViewById(R.id.edsoluong);
                final ImageView ivtru = mView.findViewById(R.id.ivtru);
                final ImageView ivcong = mView.findViewById(R.id.ivcong);
                edsoluong.setText(sanpham.getSoluong());
                ivtru.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.valueOf(edsoluong.getText().toString()) > 1)
                        sanpham.setSoluong(Integer.valueOf(edsoluong.getText().toString()) - 1+"");
                        edsoluong.setText(sanpham.getSoluong());
                    }
                });
                ivcong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sanpham.setSoluong(Integer.valueOf(edsoluong.getText().toString()) + 1+"");
                        edsoluong.setText(sanpham.getSoluong());
                    }
                });
                dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (edsoluong.getText().toString().equals("") || edsoluong.getText().toString().equals("0")){
                            sanpham.setSoluong(1+"");
                        } else {
                            sanpham.setSoluong(edsoluong.getText().toString());
                        }
                        holder.edsoluong.setText(sanpham.getSoluong());
                        capnhatsoluong();
                        clearsanpham(sanpham, Integer.valueOf(sanpham.getSoluong()) - soluongbegin);
                        soluongbegin = Integer.valueOf(sanpham.getSoluong());
                        holder.edsoluong.setEnabled(false);
                    }
                });
                dialog.setView(mView);
                android.app.AlertDialog al = dialog.create();
                al.show();
            }
        });

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

    private void clearsanpham(SanphamAmount sanpham, int integer) {
        context.clearsanpham(sanpham, integer);
    }

    private int sosanh(String ma, List<String> listsoluong) {
        int result = -1;
        for (int i =  0; i < listsoluong.size(); i++){
            if (listsoluong.get(i).equals(ma)) {
                result = i;
            }
        }
        return result;
    }

    private void capnhatsoluong() {
        context.Capnhatsoluong();
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

