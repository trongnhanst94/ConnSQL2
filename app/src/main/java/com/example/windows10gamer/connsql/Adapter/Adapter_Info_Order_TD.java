package com.example.windows10gamer.connsql.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Bao_Hanh.Main_1doi1;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Choxuly;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Doidungthu;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Doilaykhac;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Hoantien;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by EVRESTnhan on 10/9/2017.
 */

public class Adapter_Info_Order_TD extends BaseAdapter{
    Context c;
    ArrayList<Order> modelList;
    ArrayList<Order> filterList;

    public Adapter_Info_Order_TD(Context ctx,ArrayList<Order> players) {
        this.c=ctx;
        this.modelList=players;
        this.filterList=players;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Order getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        LayoutInflater inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            View view = inflater.inflate(R.layout.adapter_list_excel_td, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Order item = getItem(position);
        vh.textViewma.setText("MSP: " + item.getMaSanpham());
        vh.textViewten.setText("SP: " + item.getTenSanpham());
        vh.textViewnguon.setText("Nguồn: " + item.getNguonSanpham());
        vh.textViewbaohanh.setText("Bảo hành: " + item.getBaohanhSanpham());
        vh.textViewngaynhap.setText("Quét lúc: " + item.getGio());
        if (item.getGiaSanpham().equals("")){
            vh.textViewgiaban.setText("Giá bán: không có.");
        } else {
            vh.textViewgiaban.setText("Giá bán: " + getFormatedAmount(Integer.parseInt(item.getGiaSanpham())));
        }
        vh.ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!Connect_Internet.checkConnection(c))
                    Connect_Internet.buildDialog(c).show();
                else {
                    PopupMenu popup = new PopupMenu(c, v);
                    final PopupMenu popmenu = new PopupMenu(c, vh.ivInfo);
                    popmenu.getMenuInflater().inflate(R.menu.menu_baohanh, popmenu.getMenu());
                    insertMenuItemIcons(c, popmenu);
                    popmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @SuppressLint("RestrictedApi")
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getTitle().toString().trim().equals("Hoàn tiền")) {
                                Intent intent2 = new Intent(c, Main_Hoantien.class);
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("MaOrder", item.getMaDonhang());
                                bundle2.putString("DateOrder", item.getNgay());
                                bundle2.putString("TimeOrder", item.getCalam());
                                bundle2.putString("ChinhanhOrder", item.getChinhanh());
                                bundle2.putString("MaNVOrder", item.getMaNhanvien());
                                bundle2.putString("TenNVOrder", item.getTenNhanvien());
                                bundle2.putString("GhichuOrder", item.getGhichuSanpham());
                                bundle2.putString("TenKH", item.getTenKhachhang());
                                bundle2.putString("SdtKH", item.getSodienthoaiKhachhang());
                                bundle2.putString("GhichuKH", item.getGhichuKhachhang());
                                bundle2.putString("Gio", item.getGio());
                                bundle2.putString("Ma", item.getMaSanpham());
                                bundle2.putString("Ten", item.getTenSanpham());
                                bundle2.putString("Nguon", item.getNguonSanpham());
                                bundle2.putString("Baohanh", item.getBaohanhSanpham());
                                bundle2.putString("Ngaynhap", item.getNgaynhapSanpham());
                                bundle2.putString("Von", item.getVonSanpham());
                                bundle2.putString("Giaban", item.getGiaSanpham());
                                intent2.putExtra("InfoOrder2", bundle2);
                                c.startActivity(intent2);
                            } else if (menuItem.getTitle().toString().trim().equals("Đổi lấy khác")) {
                                Intent intent3 = new Intent(c, Main_Doilaykhac.class);
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("MaOrder", item.getMaDonhang());
                                bundle3.putString("DateOrder", item.getNgay());
                                bundle3.putString("TimeOrder", item.getCalam());
                                bundle3.putString("ChinhanhOrder", item.getChinhanh());
                                bundle3.putString("MaNVOrder", item.getMaNhanvien());
                                bundle3.putString("TenNVOrder", item.getTenNhanvien());
                                bundle3.putString("GhichuOrder", item.getGhichuSanpham());
                                bundle3.putString("TenKH", item.getTenKhachhang());
                                bundle3.putString("SdtKH", item.getSodienthoaiKhachhang());
                                bundle3.putString("GhichuKH", item.getGhichuKhachhang());
                                bundle3.putString("Gio", item.getGio());
                                bundle3.putString("Ma", item.getMaSanpham());
                                bundle3.putString("Ten", item.getTenSanpham());
                                bundle3.putString("Nguon", item.getNguonSanpham());
                                bundle3.putString("Baohanh", item.getBaohanhSanpham());
                                bundle3.putString("Ngaynhap", item.getNgaynhapSanpham());
                                bundle3.putString("Von", item.getVonSanpham());
                                bundle3.putString("Giaban", item.getGiaSanpham());
                                intent3.putExtra("InfoOrder3", bundle3);
                                c.startActivity(intent3);
                            } else if (menuItem.getTitle().toString().trim().equals("1 Đổi 1")) {
                                Intent intent4 = new Intent(c, Main_1doi1.class);
                                Bundle bundle4 = new Bundle();
                                bundle4.putString("MaOrder", item.getMaDonhang());
                                bundle4.putString("DateOrder", item.getNgay());
                                bundle4.putString("TimeOrder", item.getCalam());
                                bundle4.putString("ChinhanhOrder", item.getChinhanh());
                                bundle4.putString("MaNVOrder", item.getMaNhanvien());
                                bundle4.putString("TenNVOrder", item.getTenNhanvien());
                                bundle4.putString("GhichuOrder", item.getGhichuSanpham());
                                bundle4.putString("TenKH", item.getTenKhachhang());
                                bundle4.putString("SdtKH", item.getSodienthoaiKhachhang());
                                bundle4.putString("GhichuKH", item.getGhichuKhachhang());
                                bundle4.putString("Gio", item.getGio());
                                bundle4.putString("Ma", item.getMaSanpham());
                                bundle4.putString("Ten", item.getTenSanpham());
                                bundle4.putString("Nguon", item.getNguonSanpham());
                                bundle4.putString("Baohanh", item.getBaohanhSanpham());
                                bundle4.putString("Ngaynhap", item.getNgaynhapSanpham());
                                bundle4.putString("Von", item.getVonSanpham());
                                bundle4.putString("Giaban", item.getGiaSanpham());
                                intent4.putExtra("InfoOrder4", bundle4);
                                c.startActivity(intent4);
                            } else if (menuItem.getTitle().toString().trim().equals("Chờ xử lý")) {
                                Intent intent5 = new Intent(c, Main_Choxuly.class);
                                Bundle bundle5 = new Bundle();
                                bundle5.putString("MaOrder", item.getMaDonhang());
                                bundle5.putString("DateOrder", item.getNgay());
                                bundle5.putString("TimeOrder", item.getCalam());
                                bundle5.putString("ChinhanhOrder", item.getChinhanh());
                                bundle5.putString("MaNVOrder", item.getMaNhanvien());
                                bundle5.putString("TenNVOrder", item.getTenNhanvien());
                                bundle5.putString("GhichuOrder", item.getGhichuSanpham());
                                bundle5.putString("TenKH", item.getTenKhachhang());
                                bundle5.putString("SdtKH", item.getSodienthoaiKhachhang());
                                bundle5.putString("GhichuKH", item.getGhichuKhachhang());
                                bundle5.putString("Gio", item.getGio());
                                bundle5.putString("Ma", item.getMaSanpham());
                                bundle5.putString("Ten", item.getTenSanpham());
                                bundle5.putString("Nguon", item.getNguonSanpham());
                                bundle5.putString("Baohanh", item.getBaohanhSanpham());
                                bundle5.putString("Ngaynhap", item.getNgaynhapSanpham());
                                bundle5.putString("Von", item.getVonSanpham());
                                bundle5.putString("Giaban", item.getGiaSanpham());
                                intent5.putExtra("InfoOrder5", bundle5);
                                c.startActivity(intent5);
                            } else if (menuItem.getTitle().toString().trim().equals("Đổi dùng thử")) {
                                Intent intent6 = new Intent(c, Main_Doidungthu.class);
                                Bundle bundle6 = new Bundle();
                                bundle6.putString("MaOrder", item.getMaDonhang());
                                bundle6.putString("DateOrder", item.getNgay());
                                bundle6.putString("TimeOrder", item.getCalam());
                                bundle6.putString("ChinhanhOrder", item.getChinhanh());
                                bundle6.putString("MaNVOrder", item.getMaNhanvien());
                                bundle6.putString("TenNVOrder", item.getTenNhanvien());
                                bundle6.putString("GhichuOrder", item.getGhichuSanpham());
                                bundle6.putString("TenKH", item.getTenKhachhang());
                                bundle6.putString("SdtKH", item.getSodienthoaiKhachhang());
                                bundle6.putString("GhichuKH", item.getGhichuKhachhang());
                                bundle6.putString("Gio", item.getGio());
                                bundle6.putString("Ma", item.getMaSanpham());
                                bundle6.putString("Ten", item.getTenSanpham());
                                bundle6.putString("Nguon", item.getNguonSanpham());
                                bundle6.putString("Baohanh", item.getBaohanhSanpham());
                                bundle6.putString("Ngaynhap", item.getNgaynhapSanpham());
                                bundle6.putString("Von", item.getVonSanpham());
                                bundle6.putString("Giaban", item.getGiaSanpham());
                                intent6.putExtra("InfoOrder6", bundle6);
                                c.startActivity(intent6);
                            }
                            return true;
                        }
                    });
                    popmenu.show();
                }
            }
        });

        return vh.rootView;
    }

    /**
     * Moves icons from the PopupMenu's MenuItems' icon fields into the menu title as a Spannable with the icon and title text.
     */
    public static void insertMenuItemIcons(Context context, PopupMenu popupMenu) {
        Menu menu = popupMenu.getMenu();
        if (hasIcon(menu)) {
            for (int i = 0; i < menu.size(); i++) {
                insertMenuItemIcon(context, menu.getItem(i));
            }
        }
    }

    /**
     * @return true if the menu has at least one MenuItem with an icon.
     */
    private static boolean hasIcon(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getIcon() != null) return true;
        }
        return false;
    }

    /**
     * Converts the given MenuItem's title into a Spannable containing both its icon and title.
     */
    private static void insertMenuItemIcon(Context context, MenuItem menuItem) {
        Drawable icon = menuItem.getIcon();

        // If there's no icon, we insert a transparent one to keep the title aligned with the items
        // which do have icons.
        if (icon == null) icon = new ColorDrawable(Color.TRANSPARENT);

        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.menu_icon);
        icon.setBounds(0, 0, iconSize, iconSize);
        ImageSpan imageSpan = new ImageSpan(icon);

        // Add a space placeholder for the icon, before the title.
        SpannableStringBuilder ssb = new SpannableStringBuilder("       " + menuItem.getTitle());

        // Replace the space placeholder with the icon.
        ssb.setSpan(imageSpan, 1, 2, 0);
        menuItem.setTitle(ssb);
        // Set the icon to null just in case, on some weird devices, they've customized Android to display
        // the icon in the menu... we don't want two icons to appear.
        menuItem.setIcon(null);
    }

    private String getFormatedAmount(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber+" đ";
    }

    private static class ViewHolder {
        public final LinearLayout rootView;

        public final TextView textViewten;
        public final TextView textViewma;
        public final TextView textViewnguon;
        public final TextView textViewbaohanh;
        public final TextView textViewngaynhap;
        public final TextView textViewgiaban;
        public final ImageView ivInfo;

        private ViewHolder(LinearLayout rootView, TextView textViewma, TextView textViewten,
                           TextView textViewbaohanh, TextView textViewnguon, TextView textViewngaynhap,  TextView textViewgiaban, ImageView ivInfo) {
            this.rootView = rootView;
            this.textViewten = textViewten;
            this.textViewma = textViewma;
            this.textViewngaynhap = textViewngaynhap;
            this.textViewbaohanh = textViewbaohanh;
            this.textViewnguon = textViewnguon;
            this.textViewgiaban = textViewgiaban;
            this.ivInfo = ivInfo;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView textViewma = (TextView) rootView.findViewById(R.id.textViewma);
            TextView textViewten = (TextView) rootView.findViewById(R.id.textViewten);
            TextView textViewngaynhap = (TextView) rootView.findViewById(R.id.textViewngaynhap);
            TextView textViewbaohanh = (TextView) rootView.findViewById(R.id.textViewbaohanh);
            TextView textViewnguon = (TextView) rootView.findViewById(R.id.textViewnguon);
            TextView textViewgiaban = (TextView) rootView.findViewById(R.id.textViewgia);
            ImageView ivInfo = (ImageView) rootView.findViewById(R.id.ivInfo);

            return new ViewHolder(rootView, textViewma, textViewten, textViewbaohanh, textViewnguon, textViewngaynhap, textViewgiaban, ivInfo);
        }
    }
}
