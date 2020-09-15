package com.android.rb.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rb.comman.BaseHelper;
import com.android.rb.helper.DialogCropHelper;
import com.android.rb.helper.DialogMultiImageHelper;
import com.android.rb.helper.Preferences;
import com.android.rb.interf.ImageReceiveListener;
import com.android.rb.interf.RBImageCropListener;
import com.android.rb.interf.RBImagePickerListener;
import com.android.rb.interf.RBMultipleImagePickerListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class BaseFragment extends Fragment {

    protected static final int REQUEST_CODE_SELECT_ADD_CASH = 100;
    protected static final int REQUEST_CODE_SELECT_ADD_BANK = 101;
    protected static final int REQUEST_CODE_SELECT_CATEGORY = 102;
    protected static final int REQUEST_CODE_SELECT_SUB_CATEGORY = 103;
    protected static final int REQUEST_CODE_SELECT_LOCATION = 104;
    protected static final int REQUEST_CODE_SELECT_FILTER = 105;
    protected static final int REQUEST_CODE_EDIT_PROFILE = 106;
    protected static final int REQUEST_CODE_EDIT_COVER_PHOTOS = 107;
    protected static final int REQUEST_CODE_EDIT_PRODUCT_PHOTOS = 108;
    protected static final int REQUEST_CODE_EDIT_CARD = 109;
    private ImageReceiveListener listener;

    private static final int REQUEST_IMAGE = 1000;
    private static final int REQUEST_CAMERA = 1001;
    private static final int REQUEST_IMAGE_CROP = 1002;
    private static final int REQUEST_CAMERA_IMAGE_CROP = 1004;

    private static File cameraFile;
    private Context context;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initView(view);
        setListener();
        setLabel();
    }

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
     *
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
     *
     * @param size
     * @return
     */
    protected int getMyDimen(int size) {
        return BaseHelper.getInstance().getMyDimen(getContext(), size);
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

    public String prettyCount(Number number) {
        return BaseHelper.getInstance().prettyCount(number);
    }

    public Context getContext() {
        return context;
    }

    protected abstract void initView(View view);

    protected void setLabel() {
    }

    protected void setListener() {
    }

    protected File saveBitmap(Bitmap bitmap) {
        return BaseHelper.getInstance().saveBitmap(getContext(), bitmap);
    }

    protected void setToolbar() {
    }

    protected void showInfoDialog(String msg) {
        BaseHelper.getInstance().showInfoDialog(getContext(), msg);
    }

    protected void show() {
        BaseHelper.getInstance().show(getContext());
    }

    protected void hide() {
        BaseHelper.getInstance().hide();
    }

    protected Drawable getMyDrawable(int drawable) {
        return BaseHelper.getInstance().getMyDrawable(drawable, getContext());
    }

    protected int getMyColor(int color) {
        return BaseHelper.getInstance().getMyColor(color, getContext());
    }

    protected void setSelection(EditText editText) {
        BaseHelper.getInstance().setSelection(editText);
    }

    public void toast(String msg) {
        BaseHelper.getInstance().toast(msg, getContext());
    }

    protected void loadStorageImage(String url, ImageView imageView) {
        BaseHelper.getInstance().loadStorageImage(url, imageView, getContext());
    }

    protected void loadNetworkImage(String url, ImageView imageView) {
        BaseHelper.getInstance().loadNetworkImage(url, imageView, getContext());
    }

    protected void loadNetworkProfile(String url, ImageView imageView) {
        BaseHelper.getInstance().loadNetworkProfile(url, imageView, getContext());
    }

    protected void loadWebPage(String url) {
        BaseHelper.getInstance().loadWebPage(getContext(), url);
    }

    protected void loadPdf(String url) {
        BaseHelper.getInstance().loadPdf(getContext(), url);
    }

    //Validate email
    protected boolean isEmail(String email) {
        return BaseHelper.getInstance().isEmail(email);
    }

    protected int getPixel(int id) {
        return BaseHelper.getInstance().getPixel(id, getContext());
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

    public void navigateTo(Class<?> cls) {
        BaseHelper.getInstance().navigateTo(cls, getContext());
    }

    public void navigateTo(Intent intent, int requestCode) {
        BaseHelper.getInstance().navigateTo(intent, requestCode, getContext());
    }

    public void navigateTo(Intent intent) {
        BaseHelper.getInstance().navigateTo(intent, getContext());
    }

    //Navigate any activity
    public void navigateTo(Class<?> cls, int requestCode) {
        BaseHelper.getInstance().navigateTo(cls, requestCode, getContext());
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

    //get intent boolean
    public boolean getBooleanExtra(String key) {
        return BaseHelper.getInstance().getBooleanExtra(key, getContext());
    }

    protected Calendar getLocalTime(String time, String timeFormat) {
        return BaseHelper.getInstance().getLocalTime(time, timeFormat);
    }

    //get intent string
    public String getStringExtra(String key) {
        return BaseHelper.getInstance().getStringExtra(key, getContext());
    }

    //get intent int
    public int getIntExtra(String key) {
        return BaseHelper.getInstance().getIntExtra(key, getContext());
    }

    //Hide keyboard
    protected void dismissKeyboard(EditText editText) {
        BaseHelper.getInstance().dismissKeyboard(editText, getContext());
    }

    void whatsAppIntent(String mobile) {
        BaseHelper.getInstance().whatsAppIntent(getContext(), mobile, "");
    }

    //Show keyboard
    public void showKeyboard() {
        BaseHelper.getInstance().showKeyboard(getContext());
    }

    //Network call helper
    protected boolean isConnected() {
        return BaseHelper.getInstance().isConnected(getContext());
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
//    public String getBasicAuth() {
//        return BaseHelper.getBasicAuth(getContext());
//    }
//
    protected String compressImage(String imagePath) {
        return BaseHelper.getInstance().compressImage(getContext(), imagePath);
    }

    //Calling
    protected void phoneCall(String number) {
        BaseHelper.getInstance().phoneCall(number, getActivity());
    }

//    protected RequestBody getRequestBody(String value) {
//        return BaseHelper.getRequestBody(value);
//    }

    protected void rbImagePicker(final RBImagePickerListener listener, final boolean isCrop) {
        BaseHelper.getInstance().rbImagePicker(new RBImagePickerListener() {
            @Override
            public void onRBPickerResult(String imagePath) {
                if (isCrop) {
                    crop(imagePath);
                } else {
                    listener.onRBPickerResult(imagePath);
                }
            }

            private void crop(String imagePath) {
                rbCropImage(imagePath, new RBImageCropListener() {
                    @Override
                    public void onRBCropResult(String imagePath) {
                        listener.onRBPickerResult(imagePath);
                    }
                });
            }
        }, getContext());
    }

    protected void rbCropImage(String imagePath, final RBImageCropListener listener) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(new File(imagePath).getAbsolutePath(), options);
        BitmapFactory.decodeFile(new File(imagePath).getAbsolutePath(), options);
        show();
        new DialogCropHelper(getContext(), bitmap, new DialogCropHelper.OnResultReceived() {
            @Override
            public void onResult(String cropImagePath) {
                hide();
                if (!cropImagePath.equals("")) {
                    listener.onRBCropResult(cropImagePath);
                }
            }
        }, false);
    }

}
