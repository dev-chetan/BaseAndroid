package com.android.rb.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rb.comman.BaseHelper;

import java.util.Calendar;

public abstract class BaseAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    private static final String TAG = "BaseAdapter";
    protected static final int REQUEST_CODE_SELECT_ADD_CASH = 100;
    protected static final int REQUEST_CODE_SELECT_ADD_BANK = 101;
    protected static final int REQUEST_CODE_SELECT_LANGUAGE = 100;
    protected static final int REQUEST_CODE_SELECT_CATEGORY = 102;
    protected static final int REQUEST_CODE_SELECT_SUB_CATEGORY = 103;
    protected static final int REQUEST_CODE_SELECT_LOCATION = 104;
    protected static final int REQUEST_CODE_SELECT_FILTER = 105;
    protected static final int REQUEST_CODE_EDIT_PROFILE = 106;
    protected static final int REQUEST_CODE_EDIT_COVER_PHOTOS = 107;
    protected static final int REQUEST_CODE_EDIT_PRODUCT_PHOTOS = 108;
    protected static final int REQUEST_CODE_EDIT_CARD = 109;
    private Context context;

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        return onCreateView(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull V v, int position) {
        bindRViewHolder(v, position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return getListCounter();
    }

    protected abstract V onCreateView(ViewGroup viewGroup, int viewType);

    protected abstract void bindRViewHolder(V v, int position);

    protected void showInfoDialog(String msg) {
        BaseHelper.showInfoDialog(getContext(), msg);
    }

    protected abstract int getListCounter();

    protected void show() {
        BaseHelper.show(getContext());
    }

    protected void hide() {
        BaseHelper.hide();
    }

    protected void toast(String msg) {
        BaseHelper.toast(msg, getContext());
    }

    protected Context getContext() {
        return context;
    }

    protected String capWord(String word) {
        return BaseHelper.capWord(word);
    }

    protected Drawable getMyDrawable(int drawable) {
        return BaseHelper.getMyDrawable(drawable, getContext());
    }

    protected Spanned getHtmlContent(String str) {
        return BaseHelper.getHtmlContent(str);
    }

    //Network call helper
    protected boolean isConnected() {
        return BaseHelper.isConnected(getContext());
    }

//    public ApiInterface getApiInterface() {
//        return BaseHelper.getApiInterface();
//    }
//
//    public String printRes(Response<?> response) {
//        return BaseHelper.printRes(response);
//    }
//
//    protected void printParams(Map<?, ?> map) {
//        BaseHelper.printParams(map);
//    }
//
//    public void printError(Call<?> call, Throwable t) {
//        BaseHelper.printError(call, t);
//    }
//
//
//    public String getBasicAuth() {
//        return BaseHelper.getBasicAuth(getContext());
//    }

    protected String getPrefValue(String key) {
        return BaseHelper.getPrefValue(key, getContext());
    }

    protected String getLangValue(String key) {
        if (BaseHelper.getPrefValue(key, getContext()).equals("")) {
            return "Empty value";
        } else {
            return BaseHelper.getPrefValue(key, getContext());
        }
    }

    protected void navigateTo(Class<?> cls) {
        BaseHelper.navigateTo(cls, getContext());
    }

    protected void navigateTo(Intent intent, int requestCode) {
        BaseHelper.navigateTo(intent, requestCode, getContext());
    }

    protected void navigateTo(Intent intent) {
        BaseHelper.navigateTo(intent, getContext());
    }

    protected int getMyColor(int color) {
        return BaseHelper.getMyColor(color, getContext());
    }

    protected LinearLayoutManager setLinearLayoutManager(RecyclerView recyclerView) {
        return BaseHelper.setLinearLayoutManager(recyclerView, getContext());
    }

    protected LinearLayoutManager setLinearLayoutManagerHorizontal(RecyclerView recyclerView) {
        return BaseHelper.setLinearLayoutManagerHorizontal(recyclerView, getContext());
    }

    protected LinearLayoutManager setGridLayoutManager(RecyclerView recyclerView, int noOfColumn) {
        return BaseHelper.setGridLayoutManager(recyclerView, noOfColumn, getContext());
    }

    protected void loadNetworkImage(String url, ImageView imageView) {
        BaseHelper.loadNetworkImage(url, imageView, getContext());
    }

    protected void loadStorageImage(String url, ImageView imageView) {
        BaseHelper.loadStorageImage(url, imageView, getContext());
    }

    protected void loadNetworkProfile(String url, ImageView imageView) {
        BaseHelper.loadNetworkProfile(url, imageView, getContext());
    }

    protected void whatsAppIntent(String mobile) {
        BaseHelper.whatsAppIntent(getContext(), mobile, "");
    }

    protected void whatsAppIntent(String mobile, String text) {
        BaseHelper.whatsAppIntent(getContext(), mobile, text);
    }

    protected void shareContent(String content, String imageUrl) {
        BaseHelper.shareContent(getContext(), content, imageUrl);
    }

    protected void loadWebPage(String url) {
        BaseHelper.loadWebPage(getContext(), url);
    }

    protected void loadPdf(String url) {
        BaseHelper.loadPdf(getContext(), url);
    }

    protected Calendar getLocalTime(String time) {
        return BaseHelper.getLocalTime(time);
    }

    protected String getTimeAgo(long time) {
        return BaseHelper.getTimeAgo(time);
    }

}

