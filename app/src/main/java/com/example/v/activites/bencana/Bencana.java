package com.example.v.activites.bencana;

import com.google.gson.annotations.SerializedName;

public class Bencana {
    @SerializedName("id_jenis_bencana")
    private Integer id;

    @SerializedName("nama_bencana")
    private String nama;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
