package com.android.rb.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rb.R;
import com.android.rb.comman.BaseHelper;
import com.android.rb.helper.DialogHelper;
import com.android.rb.helper.DialogStatus;
import com.android.rb.helper.Preferences;
import com.android.rb.interf.ImageReceiveListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public abstract class BaseActivity extends AppCompatActivity {

    protected static final int REQUEST_CODE_SELECT_ADD_CASH = 100;
    protected static final int REQUEST_CODE_SELECT_ADD_BANK = 101;
    protected static final int REQUEST_CODE_SELECT_SUB_CATEGORY = 103;
    protected static final int REQUEST_CODE_SELECT_LOCATION = 104;
    protected static final int REQUEST_CODE_SELECT_FILTER = 105;
    protected static final int REQUEST_CODE_EDIT_PROFILE = 106;
    protected static final int REQUEST_CODE_EDIT_COVER_PHOTOS = 107;
    protected static final int REQUEST_CODE_EDIT_PRODUCT_PHOTOS = 108;
    protected static final int REQUEST_CODE_EDIT_CARD = 109;
    private static final String TAG = "BaseActivity";
    private ImageReceiveListener listener;

    private static final int REQUEST_IMAGE = 1000;
    private static final int REQUEST_CAMERA = 1001;
    private static final int REQUEST_IMAGE_CROP = 1002;
    private static final int REQUEST_CAMERA_IMAGE_CROP = 1004;

    private static File cameraFile;

    protected <T> T getAuth(Class<T> type) {
        return new Gson()
                .fromJson(getPrefValue(Preferences.USER_DATA)
                        , TypeToken.getParameterized(type, type).getType());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        setLabel();
        setToolbar();
    }


    protected String capWord(String word) {
        return BaseHelper.capWord(word);
    }

    protected Spanned getHtmlContent(String str) {
        return BaseHelper.getHtmlContent(str);
    }

    protected void showErrorMsg(String message) {
        new DialogHelper(message, "", "OK", new DialogHelper.DialogCallBack() {
            @Override
            public void onResult(int resultCode) {

            }
        }, DialogStatus.DIALOG_DEFAULT).show(getSupportFragmentManager(), "");
    }

    protected Calendar getLocalTime(String time) {
        return BaseHelper.getLocalTime(time);
    }

    protected String getTimeAgo(long time) {
        return BaseHelper.getTimeAgo(time);
    }

    protected void setListDataStatus(int size, String msg, TextView textView) {
        BaseHelper.setListDataStatus(size, msg, textView);
    }

    public Context getContext() {
        return this;
    }

    protected abstract void initView();

    protected abstract void setLabel();

    protected void setListener() {
    }

    protected void setToolbar() {
    }

    protected void show() {
        BaseHelper.show(getContext());
    }

    protected void hide() {
        BaseHelper.hide();
    }

    protected Drawable getMyDrawable(int drawable) {
        return BaseHelper.getMyDrawable(drawable, getContext());
    }

    protected void sendEmail(String email) {
        BaseHelper.sendEmail(getContext(), email);
    }

    protected int getMyColor(int color) {
        return BaseHelper.getMyColor(color, getContext());
    }

    protected void setSelection(EditText editText) {
        BaseHelper.setSelection(editText);
    }

    public void toast(String msg) {
        BaseHelper.toast(msg, getContext());
    }

    protected void loadStorageImage(String url, ImageView imageView) {
        BaseHelper.loadStorageImage(url, imageView, getContext());
    }

    protected void loadNetworkImage(String url, ImageView imageView) {
        BaseHelper.loadNetworkImage(url, imageView, getContext());
    }

    protected void loadNetworkProfile(String url, ImageView imageView) {
        BaseHelper.loadNetworkProfile(url, imageView, getContext());
    }

    protected void loadWebPage(String url) {
        BaseHelper.loadWebPage(getContext(), url);
    }

    protected void loadPdf(String url) {
        BaseHelper.loadPdf(getContext(), url);
    }

    //Validate email
    protected boolean isEmail(String email) {
        return BaseHelper.isEmail(email);
    }

    protected int getPixel(int id) {
        return BaseHelper.getPixel(id, getContext());
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

    public void navigateTo(Class<?> cls) {
        BaseHelper.navigateTo(cls, getContext());
    }

    public void navigateTo(Intent intent, int requestCode) {
        BaseHelper.navigateTo(intent, requestCode, getContext());
    }

    public void navigateTo(Intent intent) {
        BaseHelper.navigateTo(intent, getContext());
    }

    //Navigate any activity
    public void navigateTo(Class<?> cls, int requestCode) {
        BaseHelper.navigateTo(cls, requestCode, getContext());
    }

    //Pref
    protected void setPrefValue(String key, String value) {
        BaseHelper.setPrefValue(key, value, getContext());
    }

    protected void setPrefBool(String key, boolean value) {
        BaseHelper.setPrefBool(key, value, getContext());
    }

    protected String getPrefValue(String key) {
        return BaseHelper.getPrefValue(key, getContext());
    }

    protected void showInfoDialog(String msg) {
        BaseHelper.showInfoDialog(getContext(), msg);
    }


    public boolean getPrefBool(String key) {
        return BaseHelper.getPrefBool(key, getContext());
    }

    protected String getLangValue(String key) {
        if (BaseHelper.getPrefValue(key, getContext()).equals("")) {
            return "Empty value";
        } else {
            return BaseHelper.getPrefValue(key, getContext());
        }
    }

    //get intent boolean
    public boolean getBooleanExtra(String key) {
        return BaseHelper.getBooleanExtra(key, getContext());
    }

    //get intent string
    public String getStringExtra(String key) {
        return BaseHelper.getStringExtra(key, getContext());
    }

    //get intent int
    public int getIntExtra(String key) {
        return BaseHelper.getIntExtra(key, getContext());
    }

    //Hide keyboard
    protected void dismissKeyboard(EditText editText) {
        BaseHelper.dismissKeyboard(editText, getContext());
    }

    void whatsAppIntent(String mobile) {
        BaseHelper.whatsAppIntent(getContext(), mobile, "");
    }

    //Show keyboard
    public void showKeyboard() {
        BaseHelper.showKeyboard(getContext());
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

//    public String getBasicAuth() {
//        return BaseHelper.getBasicAuth(getContext());
//    }

    protected void selectImage(AppCompatActivity activity, ImageReceiveListener listener, boolean isCrop) {
        this.listener = listener;
        cameraFile = BaseHelper.getFilePath(getContext());
        BaseHelper.selectImage(activity, cameraFile, isCrop, getContext());
    }

    protected String compressImage(String imagePath) {
        return BaseHelper.compressImage(getContext(), imagePath);
    }
//
//    protected RequestBody getRequestBody(String value) {
//        return BaseHelper.getRequestBody(value);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        ArrayList<String> images = new ArrayList<>();
        ExifInterface exif = null;
        switch (requestCode) {
            case REQUEST_IMAGE:
                assert data != null;
                if (Matisse.obtainResult(data) != null) {
                    List<Uri> uriList = Matisse.obtainResult(data);
                    if (uriList.size() > 1) {
                        for (int i = 0; i < (uriList.size() > 5 ? 5 : uriList.size()); i++) {
                            String imageUrl = Matisse.obtainPathResult(data).get(i);
                            images.add(imageUrl);
                        }
                        listener.onImageReceived(images);
                        if (uriList.size() > 5) {
                            toast(getContext().getString(R.string.txt_image_limit));
                        }
                    } else {
                        String imageUrl = Matisse.obtainPathResult(data).get(0);
                        images.add(imageUrl);
                        listener.onImageReceived(images);
                    }
                }
                break;
            case REQUEST_CAMERA:
                try {
                    exif = new ExifInterface(cameraFile.getAbsolutePath());
                    int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int rotationInDegrees = BaseHelper.exifToDegrees(rotation);
                    images.add(cameraFile.getAbsolutePath());
                    listener.onImageReceived(images);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_CAMERA_IMAGE_CROP:
                try {
                    exif = new ExifInterface(cameraFile.getAbsolutePath());
                    int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int rotationInDegrees = BaseHelper.exifToDegrees(rotation);
                    BaseHelper.cropImage(cameraFile.getAbsolutePath(), rotationInDegrees, getContext(), new ImageReceiveListener() {
                        @Override
                        public void onImageReceived(ArrayList<String> images) {
                            listener.onImageReceived(images);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_IMAGE_CROP:
                try {
                    exif = new ExifInterface(Matisse.obtainPathResult(data).get(0));
                    int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int rotationInDegrees = BaseHelper.exifToDegrees(rotation);
                    BaseHelper.cropImage(Matisse.obtainPathResult(data).get(0), rotationInDegrees, getContext(), new ImageReceiveListener() {
                        @Override
                        public void onImageReceived(ArrayList<String> images) {
                            listener.onImageReceived(images);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
