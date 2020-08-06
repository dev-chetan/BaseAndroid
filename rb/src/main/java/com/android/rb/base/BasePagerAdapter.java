package com.android.rb.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.android.rb.comman.BaseHelper;
import com.android.rb.helper.Preferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;

public abstract class BasePagerAdapter extends PagerAdapter {
    private Context context;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        context = container.getContext();
        View view = getItem(container, position);
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view); // <- fix
        }
        container.addView(view);
        return view;
    }

    protected abstract View getItem(ViewGroup container, int position);

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    //Calling
    protected void phoneCall(String number) {
        BaseHelper.getInstance().phoneCall(number, getContext());
    }

    @Override
    public final boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    protected String removeLastChar(String str) {
        return BaseHelper.getInstance().removeLastChar(str);
    }

    protected void loadNetworkPDF(String pdfUrl, ImageView imageView) {
        BaseHelper.getInstance().loadNetworkPDF(getContext(), pdfUrl, imageView);
    }

    protected File saveBitmap(Bitmap bitmap) {
        return BaseHelper.getInstance().saveBitmap(getContext(), bitmap);
    }

    protected void navigateTo(Class<?> cls) {
        BaseHelper.getInstance().navigateTo(cls, getContext());
    }

    protected void navigateTo(Intent intent) {
        BaseHelper.getInstance().navigateTo(intent, getContext());
    }

    protected void navigateTo(Intent intent, int requestCode) {
        BaseHelper.getInstance().navigateTo(intent, requestCode, getContext());
    }

    protected String getPrefValue(String key) {
        return BaseHelper.getInstance().getPrefValue(key, getContext());
    }

    protected String getLangValue(String key) {
        if (BaseHelper.getInstance().getPrefValue(key, getContext()).equals("")) {
            return "Empty value";
        } else {
            return BaseHelper.getInstance().getPrefValue(key, getContext());
        }
    }

    protected  <T> void setAuth(Context context, T cls) {
        String s = new Gson().toJson(cls);
        Preferences.setValue(context, Preferences.USER_DATA, s);
    }

    protected <T> T getAuth(Class<T> type) {
        return new Gson()
                .fromJson(getPrefValue(Preferences.USER_DATA)
                        , TypeToken.getParameterized(type, type).getType());
    }

    protected Context getContext() {
        return context;
    }

    protected Drawable getMyDrawable(int drawable) {
        return BaseHelper.getInstance().getMyDrawable(drawable, getContext());
    }

    protected void loadNetworkImage(String url, ImageView imageView) {
        BaseHelper.getInstance().loadNetworkImage(url, imageView, getContext());
    }

    protected void loadStorageImage(String url, ImageView imageView) {
        BaseHelper.getInstance().loadStorageImage(url, imageView, getContext());
    }

}
