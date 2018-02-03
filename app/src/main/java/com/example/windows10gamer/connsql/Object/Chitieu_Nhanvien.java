package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 1/30/2018.
 */

public class Chitieu_Nhanvien {
    String id, manhanvien, tennhanvien, soca, tangca, chitieu, doanhthutrenkhach, sanphamtrenkhach, thang, luykeluong;

    public Chitieu_Nhanvien(String id, String manhanvien, String tennhanvien, String soca, String tangca, String chitieu, String doanhthutrenkhach, String sanphamtrenkhach, String thang, String luykeluong) {
        this.id = id;
        this.manhanvien = manhanvien;
        this.tennhanvien = tennhanvien;
        this.soca = soca;
        this.tangca = tangca;
        this.chitieu = chitieu;
        this.doanhthutrenkhach = doanhthutrenkhach;
        this.sanphamtrenkhach = sanphamtrenkhach;
        this.thang = thang;
        this.luykeluong = luykeluong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
    }

    public String getTennhanvien() {
        return tennhanvien;
    }

    public void setTennhanvien(String tennhanvien) {
        this.tennhanvien = tennhanvien;
    }

    public String getSoca() {
        return soca;
    }

    public void setSoca(String soca) {
        this.soca = soca;
    }

    public String getTangca() {
        return tangca;
    }

    public void setTangca(String tangca) {
        this.tangca = tangca;
    }

    public String getChitieu() {
        return chitieu;
    }

    public void setChitieu(String chitieu) {
        this.chitieu = chitieu;
    }

    public String getDoanhthutrenkhach() {
        return doanhthutrenkhach;
    }

    public void setDoanhthutrenkhach(String doanhthutrenkhach) {
        this.doanhthutrenkhach = doanhthutrenkhach;
    }

    public String getSanphamtrenkhach() {
        return sanphamtrenkhach;
    }

    public void setSanphamtrenkhach(String sanphamtrenkhach) {
        this.sanphamtrenkhach = sanphamtrenkhach;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getLuykeluong() {
        return luykeluong;
    }

    public void setLuykeluong(String luykeluong) {
        this.luykeluong = luykeluong;
    }
}
