package com.example.countryinfo;

public class Country {
    private String tennuoc;
    private int danso;
    private double dientich;
    private String maquocgia;
    private String hinhquockiURL;

    public Country() {
    }

    public Country(String tennuoc, int danso, double dientich,String maquocgia) {
        this.tennuoc = tennuoc;
        this.danso = danso;
        this.dientich = dientich;
        this.maquocgia = maquocgia;
        this.hinhquockiURL = "https://img.geonames.org/flags/x/" + maquocgia.toLowerCase() + ".gif";
    }

    public String getTennuoc() {
        return tennuoc;
    }

    public void setTennuoc(String tennuoc) {
        this.tennuoc = tennuoc;
    }

    public int getDanso() {
        return danso;
    }

    public String getHinhquockiURL() {
        return hinhquockiURL;
    }

    public void setHinhquockiURL(String hinhquockiURL) {
        this.hinhquockiURL = hinhquockiURL;
    }

    public void setDanso(int danso) {
        this.danso = danso;
    }

    public double getDientich() {
        return dientich;
    }

    public void setDientich(double dientich) {
        this.dientich = dientich;
    }
}
