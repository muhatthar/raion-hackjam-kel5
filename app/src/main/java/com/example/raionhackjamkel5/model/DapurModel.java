package com.example.raionhackjamkel5.model;

public class DapurModel {

    private String namaProduk, namaPenjual, noWhatsapp, hargaBeli, hargaJual, lokasiProduk, deskripsiProduk, kategoriProduk, fotoProduk;
    private String key;

    public DapurModel() {
    }

    public DapurModel(String namaProduk, String namaPenjual, String noWhatsapp, String hargaBeli, String hargaJual, String lokasiProduk, String deskripsiProduk, String kategoriProduk, String fotoProduk) {
        this.namaProduk = namaProduk;
        this.namaPenjual = namaPenjual;
        this.noWhatsapp = noWhatsapp;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.lokasiProduk = lokasiProduk;
        this.deskripsiProduk = deskripsiProduk;
        this.kategoriProduk = kategoriProduk;
        this.fotoProduk = fotoProduk;
    }

    public String getNamaPenjual() {
        return namaPenjual;
    }

    public void setNamaPenjual(String namaPenjual) {
        this.namaPenjual = namaPenjual;
    }

    public String getNoWhatsapp() {
        return noWhatsapp;
    }

    public void setNoWhatsapp(String noWhatsapp) {
        this.noWhatsapp = noWhatsapp;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(String hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public String getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual = hargaJual;
    }

    public String getLokasiProduk() {
        return lokasiProduk;
    }

    public void setLokasiProduk(String lokasiProduk) {
        this.lokasiProduk = lokasiProduk;
    }

    public String getDeskripsiProduk() {
        return deskripsiProduk;
    }

    public void setDeskripsiProduk(String deskripsiProduk) {
        this.deskripsiProduk = deskripsiProduk;
    }

    public String getKategoriProduk() {
        return kategoriProduk;
    }

    public void setKategoriProduk(String kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
    }

    public String getFotoProduk() {
        return fotoProduk;
    }

    public void setFotoProduk(String fotoProduk) {
        this.fotoProduk = fotoProduk;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
