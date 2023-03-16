package com.example.v.activites.bencana;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BencanaResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private List<Bencana> data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Bencana> getData() {
        return data;
    }

    public void setData(List<Bencana> data) {
        this.data = data;
    }
}
