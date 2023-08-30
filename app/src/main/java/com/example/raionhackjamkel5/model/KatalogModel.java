package com.example.raionhackjamkel5.model;

public class KatalogModel {

    private String namaProduk, hargaBeli, hargaJual, lokasiProduk, deskripsiProduk, kategoriProduk, fotoProduk;
    private String key;

    public KatalogModel() {
    }

    public KatalogModel(String namaProduk, String hargaBeli, String hargaJual, String lokasiProduk, String deskripsiProduk, String kategoriProduk, String fotoProduk) {
        this.namaProduk = namaProduk;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.lokasiProduk = lokasiProduk;
        this.deskripsiProduk = deskripsiProduk;
        this.kategoriProduk = kategoriProduk;
        this.fotoProduk = fotoProduk;
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
