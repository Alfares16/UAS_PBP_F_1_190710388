package com.uas.hospital.models;

import com.google.gson.annotations.SerializedName;

public class Pasien {
    private Long id;
    private String nama;

    @SerializedName("jenis_kelamin")
    private String jenisKelamin;

    private String umur;
    private String Ruangan;

    public Pasien(String nama, String jenisKelamin, String umur, String ruangan) {
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.umur = umur;
        this.Ruangan = ruangan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getRuangan() {
        return Ruangan;
    }

    public void setRuangan(String ruangan) {
        Ruangan = ruangan;
    }
}
