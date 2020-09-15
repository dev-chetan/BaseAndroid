package com.android.rb.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rb.comman.BaseHelper;
import com.android.rb.helper.DialogMultiImageHelper;
import com.android.rb.helper.Preferences;
import com.android.rb.interf.RBMultipleImagePickerListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
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

    /**
     * Multiple Image Get
     * @param rbMultipleImagePickerListener
     */
    protected void rbImageMultiPicker(final RBMultipleImagePickerListener rbMultipleImagePickerListener) {
        BaseHelper.getInstance().rbImageMultiPicker(new RBMultipleImagePickerListener() {
            @Override
            public void onRBPickerResult(ArrayList<DialogMultiImageHelper.ImageData> arrayList) {
                rbMultipleImagePickerListener.onRBPickerResult(arrayList);
            }
        }, getContext());
    }

    /**
     * Call save contact intent
     *
     * @param context
     * @param phone
     * @param name
     * @param email
     */
    public void saveContact(Context context, String phone, String name, String email) {
        BaseHelper.getInstance().saveContact(context, phone, name, email);
    }

    /**
     * Remove last char form string
     *
     * @param str
     * @return
     */
    public String removeLastChar(String str) {
        return BaseHelper.getInstance().removeLastChar(str);
    }

    /**
     * Remove last char form string
     *
     * @param str   content
     * @param count how many char you want to remove from last of content
     * @return
     */
    public String removeLastChar(String str, int count) {
        return BaseHelper.getInstance().removeLastChar(str, count);
    }


    /**
     * Set input cursor to end of the string.
     * @param content
     * @return
     */
    protected int getSelection(String content) {
        try {
            if (!content.equals("")) {
                return content.length();
            } else {
                return 0;
            }
        } catch (NullPointerException e) {
            return 0;
        }
    }

    /**
     * Set input fields as required. (ex Username*)
     *
     * @param textInputLayout
     */
    protected void setRequired(TextInputLayout textInputLayout) {
        BaseHelper.getInstance().setRequired(textInputLayout);
    }

    /**
     * Give R.dimen.xyz here file
     * @param size
     * @return
     */
    protected int getMyDimen(int size) {
        return BaseHelper.getInstance().getMyDimen(getContext(),size);
    }

    protected void loadNetworkPDF(String pdfUrl, ImageView imageView) {
        BaseHelper.getInstance().loadNetworkPDF(getContext(), pdfUrl, imageView);
    }

    protected <T> T getAuth(Class<T> type) {
        return new Gson()
                .fromJson(getPrefValue(Preferences.USER_DATA)
                        , TypeToken.getParameterized(type, type).getType());
    }

    protected <T> void setAuth(Context context, T cls) {
        String s = new Gson().toJson(cls);
        Preferences.setValue(context, Preferences.USER_DATA, s);
    }

    protected void showInfoDialog(String msg) {
        BaseHelper.getInstance().showInfoDialog(getContext(), msg);
    }

    protected abstract int getListCounter();

    protected File saveBitmap(Bitmap bitmap) {
        return BaseHelper.getInstance().saveBitmap(getContext(), bitmap);
    }

    protected void show() {
        BaseHelper.getInstance().show(getContext());
    }

    protected void hide() {
        BaseHelper.getInstance().hide();
    }

    protected void toast(String msg) {
        BaseHelper.getInstance().toast(msg, getContext());
    }

    protected Context getContext() {
        return context;
    }

    protected String capWord(String word) {
        return BaseHelper.getInstance().capWord(word);
    }

    protected Drawable getMyDrawable(int drawable) {
        return BaseHelper.getInstance().getMyDrawable(drawable, getContext());
    }

    protected Spanned getHtmlContent(String str) {
        return BaseHelper.getInstance().getHtmlContent(str);
    }

    //Network call helper
    protected boolean isConnected() {
        return BaseHelper.getInstance().isConnected(getContext());
    }

    //Pref
    protected void setPrefValue(String key, String value) {
        BaseHelper.getInstance().setPrefValue(key, value, getContext());
    }

    protected void setPrefBool(String key, boolean value) {
        BaseHelper.getInstance().setPrefBool(key, value, getContext());
    }

    protected String getPrefValue(String key) {
        return BaseHelper.getInstance().getPrefValue(key, getContext());
    }

    public boolean getPrefBool(String key) {
        return BaseHelper.getInstance().getPrefBool(key, getContext());
    }

    protected String getLangValue(String key) {
        if (BaseHelper.getInstance().getPrefValue(key, getContext()).equals("")) {
            return "Empty value";
        } else {
            return BaseHelper.getInstance().getPrefValue(key, getContext());
        }
    }

    //Calling
    protected void phoneCall(String number) {
        BaseHelper.getInstance().phoneCall(number, getContext());
    }

    protected void navigateTo(Class<?> cls) {
        BaseHelper.getInstance().navigateTo(cls, getContext());
    }

    protected void navigateTo(Intent intent, int requestCode) {
        BaseHelper.getInstance().navigateTo(intent, requestCode, getContext());
    }

    protected void navigateTo(Intent intent) {
        BaseHelper.getInstance().navigateTo(intent, getContext());
    }

    protected int getMyColor(int color) {
        return BaseHelper.getInstance().getMyColor(color, getContext());
    }

    protected LinearLayoutManager setLinearLayoutManager(RecyclerView recyclerView) {
        return BaseHelper.getInstance().setLinearLayoutManager(recyclerView, getContext());
    }

    protected LinearLayoutManager setLinearLayoutManagerHorizontal(RecyclerView recyclerView) {
        return BaseHelper.getInstance().setLinearLayoutManagerHorizontal(recyclerView, getContext());
    }

    protected LinearLayoutManager setGridLayoutManager(RecyclerView recyclerView, int noOfColumn) {
        return BaseHelper.getInstance().setGridLayoutManager(recyclerView, noOfColumn, getContext());
    }

    protected void loadNetworkImage(String url, ImageView imageView) {
        BaseHelper.getInstance().loadNetworkImage(url, imageView, getContext());
    }

    protected void loadStorageImage(String url, ImageView imageView) {
        BaseHelper.getInstance().loadStorageImage(url, imageView, getContext());
    }

    protected void loadNetworkProfile(String url, ImageView imageView) {
        BaseHelper.getInstance().loadNetworkProfile(url, imageView, getContext());
    }

    protected void whatsAppIntent(String mobile) {
        BaseHelper.getInstance().whatsAppIntent(getContext(), mobile, "");
    }

    protected void whatsAppIntent(String mobile, String text) {
        BaseHelper.getInstance().whatsAppIntent(getContext(), mobile, text);
    }

    protected void shareContent(String content, String imageUrl) {
        BaseHelper.getInstance().shareContent(getContext(), content, imageUrl);
    }

    protected void loadWebPage(String url) {
        BaseHelper.getInstance().loadWebPage(getContext(), url);
    }

    protected void loadPdf(String url) {
        BaseHelper.getInstance().loadPdf(getContext(), url);
    }

    protected Calendar getLocalTime(String time, String timeFormat) {
        return BaseHelper.getInstance().getLocalTime(time, timeFormat);
    }

    protected String getTimeAgo(long time) {
        return BaseHelper.getInstance().getTimeAgo(time);
    }

}

