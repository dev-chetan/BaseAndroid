package com.android.rb.models;

public class BottomSheetData {
    private boolean isSelect;
    private int id;
    private String title;
    private String networkUrl;
    private int drawable;

    public BottomSheetData(boolean isSelect, int id, String title) {
        this.isSelect = isSelect;
        this.id = id;
        this.title = title;
    }

    public BottomSheetData(boolean isSelect, String title, int drawable) {
        this.isSelect = isSelect;
        this.title = title;
        this.drawable = drawable;
    }

    public BottomSheetData(boolean isSelect, String title, String networkUrl) {
        this.isSelect = isSelect;
        this.title = title;
        this.networkUrl = networkUrl;
    }

    public BottomSheetData(boolean isSelect, String title) {
        this.isSelect = isSelect;
        this.title = title;
    }

    public BottomSheetData() {
    }

    public String getNetworkUrl() {
        return networkUrl;
    }

    public int getDrawable() {
        return drawable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
