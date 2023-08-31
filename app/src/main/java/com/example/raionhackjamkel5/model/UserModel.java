package com.example.raionhackjamkel5.model;

public class UserModel {
    private String nama;
    private String whatsapp;
    private String asalKota;
    private String key;

    public UserModel(){

    }

    public UserModel(String nama, String whatsapp, String asalKota) {
        this.nama = nama;
        this.whatsapp = whatsapp;
        this.asalKota = asalKota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getAsalKota() {
        return asalKota;
    }

    public void setAsalKota(String asalKota) {
        this.asalKota = asalKota;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
