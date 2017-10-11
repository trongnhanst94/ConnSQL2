//package com.example.windows10gamer.connsql;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.support.constraint.ConstraintLayout;
//import android.support.v7.app.AlertDialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SanphamAdapter extends BaseAdapter {
//
//    private Context context;
//    private List<Sanpham> sanphams;
//    private int layout;
//    private FragmentList fragmentList;
//    private Put_Sanpham putSanpham;
//
//    public SanphamAdapter(Context context, int layout, List<Sanpham> sanphams, FragmentList fragmentList, Put_Sanpham putSanpham1) {
//        this.context = context;
//        this.sanphams = sanphams;
//        this.layout = layout;
//        this.fragmentList = fragmentList;
//        this.putSanpham = putSanpham1;
//    }
//
//    public SanphamAdapter(Context context, int layout, ArrayList<Sanpham> sanphams) {
//        this.context = context;
//        this.sanphams = sanphams;
//        this.layout = layout;
//    }
//
//    @Override
//    public int getCount() {
//        return sanphams.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    private class ViewHolder{
//        TextView tvMa, tvTen, tvGia;
//        Button btnDelete, btnEdit;
//        ConstraintLayout constraintLayout;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        final ViewHolder holder;
//        if(view == null){
//            holder = new ViewHolder();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(layout, null);
//            holder.tvTen = (TextView) view.findViewById(R.id.tvTen);
//            holder.tvMa = (TextView) view.findViewById(R.id.tvMa);
//            holder.tvGia = (TextView) view.findViewById(R.id.tvGia);
//            holder.btnEdit = (Button) view.findViewById(R.id.btnEdit);
//            holder.btnDelete = (Button) view.findViewById(R.id.btnDelete);
//            holder.constraintLayout = (ConstraintLayout) view.findViewById(R.id.itemClick);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
//
//        final Sanpham sanpham = sanphams.get(i);
//
//        holder.tvTen.setText(sanpham.getTen());
//        holder.tvMa.setText("MSP: "+ sanpham.getMa());
//        holder.tvGia.setText("Giá bán: "+sanpham.getGiaban()+"đ");
//        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, Main_UpdateProduct.class);
//                intent.putExtra("dataSanpham", sanpham);
//                context.startActivity(intent);
//            }
//        });
//        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String ten = sanpham.getTen();
//                int id = sanpham.getId();
//                xacNhanXoa(ten, id);
//            }
//        });
//
//        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                putSanpham.DataSanpham(sanpham);
//            }
//        });
//
//        return view;
//    }
//
//    private void Info(String ten, final int id) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        dialog.setMessage("Bạn có muốn xóa sản phẩm"+ ten+" ??");
//        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                fragmentList.DeleteSP(id);
//            }
//        });
//        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        dialog.show();
//    }
//
//    private void xacNhanXoa(String ten, final int id) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        dialog.setMessage("Bạn có muốn xóa sản phẩm"+ ten+" ??");
//        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                fragmentList.DeleteSP(id);
//            }
//        });
//        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        dialog.show();
//    }
//
//}
