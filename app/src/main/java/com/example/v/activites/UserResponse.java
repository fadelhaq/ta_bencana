package com.example.v.activites;

public class UserResponse {
    private  int id;
    private String iNama;
    private String iJenis;
    private String textView_location;
    private String iLaporan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getiNama() {
        return iNama;
    }

    public void setiNama(String iNama) {
        this.iNama = iNama;
    }

    public String getiJenis() {
        return iJenis;
    }

    public void setiJenis(String iJenis) {
        this.iJenis = iJenis;
    }

    public String getTextView_location() {
        return textView_location;
    }

    public void setTextView_location(String textView_location) {
        this.textView_location = textView_location;
    }

    public String getiLaporan() {
        return iLaporan;
    }

    public void setiLaporan(String iLaporan) {
        this.iLaporan = iLaporan;
    }
}
