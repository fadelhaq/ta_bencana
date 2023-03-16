package com.example.v.activites.laporan;

public class Laporan {
    private String nama_pelapor;
    private Integer id_jenis_bencana;
    private String foto;
    private String lokasi;
    private String keterangan;

    public Laporan(String nama_pelapor, Integer id_jenis_bencana, String foto, String lokasi, String keterangan) {
        this.nama_pelapor = nama_pelapor;
        this.id_jenis_bencana = id_jenis_bencana;
        this.foto = foto;
        this.lokasi = lokasi;
        this.keterangan = keterangan;
    }

    public String getNama_pelapor() {
        return nama_pelapor;
    }

    public void setNama_pelapor(String nama_pelapor) {
        this.nama_pelapor = nama_pelapor;
    }

    public Integer getId_jenis_bencana() {
        return id_jenis_bencana;
    }

    public void setId_jenis_bencana(Integer id_jenis_bencana) {
        this.id_jenis_bencana = id_jenis_bencana;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
