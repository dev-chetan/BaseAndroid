package com.android.rb.models;

public class BottomSheetData {
    private boolean isSelect;
    private int id;
    private String title;

    public BottomSheetData(boolean isSelect, int id, String title) {
        this.isSelect = isSelect;
        this.id = id;
        this.title = title;
    }

    public BottomSheetData(boolean isSelect, String title) {
        this.isSelect = isSelect;
        this.title = title;
    }

    public BottomSheetData() {
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
