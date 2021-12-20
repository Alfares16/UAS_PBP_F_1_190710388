package com.uas.hospital.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PasienResponse {

    private String message;
    @SerializedName("pasien")
    private List<Pasien> pasienList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Pasien> getPasienList() {
        return pasienList;
    }

    public void setPasienList(List<Pasien> pasienList) {
        this.pasienList = pasienList;
    }
}
