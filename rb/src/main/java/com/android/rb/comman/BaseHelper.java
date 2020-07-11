package com.android.rb.comman;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rb.AppConfig;
import com.android.rb.R;
import com.android.rb.helper.DialogCropHelper;
import com.android.rb.helper.DialogHelper;
import com.android.rb.helper.DialogStatus;
import com.android.rb.helper.ImageCompress;
import com.android.rb.helper.LoadingDialog;
import com.android.rb.helper.PermissionHandler;
import com.android.rb.helper.PermissionUtil;
import com.android.rb.helper.Preferences;
import com.android.rb.interf.ImageReceiveListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.GsonBuilder;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.ImageEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

public class BaseHelper {

    private static final String TAG = "BaseHelper";
    private static final int REQUEST_IMAGE = 1000;
    private static final int REQUEST_CAMERA = 1001;
    private static final int REQUEST_IMAGE_CROP = 1002;
    private static final int REQUEST_CAMERA_IMAGE_CROP = 1004;

    private static LoadingDialog loadingDialog;

    //Cap word
    public static String capWord(String word) {
        try {
            if (!word.equals("")) {
                return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            }
        } catch (Exception e) {
            return "";
        }

        return "";
    }


    // Dialogs
    public static void show(Context context) {
        loadingDialog = new LoadingDialog(context, false);
    }

    public static void hide() {
        if (loadingDialog != null)
            loadingDialog.hide();
    }

    public static void showInfoDialog(Context context, String msg) {
        new DialogHelper(msg, "", context.getString(R.string.txt_ok), new DialogHelper.DialogCallBack() {
            @Override
            public void onResult(int resultCode) {

            }
        }, DialogStatus.DIALOG_DEFAULT).show(((AppCompatActivity) context).getSupportFragmentManager(), "");
    }


    public static Spanned getHtmlContent(String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(s);
        }
    }

    // Validate Methods
    public static boolean isEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    //Resources methods
    public static int getPixel(int id, Context context) {
        return (int) (context.getResources().getDimension(id) / context.getResources().getDisplayMetrics().density);
    }

    public static int getMyColor(int color, Context context) {
        return ResourcesCompat.getColor(context.getResources(), color, null);
    }

    //List layout manager
    public static LinearLayoutManager setLinearLayoutManager(RecyclerView recyclerView, Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        return linearLayoutManager;
    }

    public static LinearLayoutManager setLinearLayoutManagerHorizontal(RecyclerView recyclerView, Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        return linearLayoutManager;
    }

    public static GridLayoutManager setGridLayoutManager(RecyclerView recyclerView, int noOfColumn, Context context) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, noOfColumn);
        recyclerView.setLayoutManager(gridLayoutManager);
        return gridLayoutManager;
    }

    public static Drawable getMyDrawable(int drawable, Context context) {
        return ResourcesCompat.getDrawable(context.getResources(), drawable, null);
    }


    //Other supported methods
    public static void toast(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }


    //Navigation methods
    public static void navigateTo(Class<?> cls, Context context) {
        context.startActivity(new Intent(context, cls));
        showAnimWhileChangeScreen(context);
    }

    public static void navigateTo(Intent intent, int requestCode, Context context) {
        ((AppCompatActivity) context).startActivityForResult(intent, requestCode);
        showAnimWhileChangeScreen(context);
    }

    public static void navigateTo(Class<?> cls, int requestCode, Context context) {
        ((AppCompatActivity) context).startActivityForResult(new Intent(context, cls), requestCode);
        showAnimWhileChangeScreen(context);
    }

    public static void navigateTo(Intent intent, Context context) {
        context.startActivity(intent);
        showAnimWhileChangeScreen(context);
    }

    private static void showAnimWhileChangeScreen(Context context) {
        ((AppCompatActivity) context).overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }


    //Implicit intents
    public static void whatsAppIntent(Context context, String mobile, String text) {
        PackageManager packageManager = context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=91"
                    + mobile
                    + "&text="
                    + URLEncoder.encode(text, "UTF-8"
            );
            i.setPackage("com.whatsapp.w4b");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                context.startActivity(i);
            } else {
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                if (i.resolveActivity(packageManager) != null) {
                    context.startActivity(i);
                } else {
                    Uri uri = Uri.parse("market://details?id=com.whatsapp");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(goToMarket);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Message : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadWebPage(Context context, String url) {
        try {
            if (!url.equals("")) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                } catch (Exception e) {
                    if (url.startsWith("www.") || url.startsWith("http://")) {
                        if (!url.startsWith("www.") && !url.startsWith("http://")) {
                            url = "www." + url;
                        }
                    }
                    if (url.startsWith("www.") || url.startsWith("https://")) {
                        if (!url.startsWith("www.") && !url.startsWith("https://")) {
                            url = "www." + url;
                        }
                    }
                    if (!url.startsWith("http://")) {
                        url = "http://" + url;
                    }
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                    e.printStackTrace();
                }
            } else {
                toast(context.getString(AppConfig.getInstance().getTxtPageNotFound()), context);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            toast(context.getString(AppConfig.getInstance().getTxtPageNotFound()), context);
        }
    }

    public static String prettyCount(Number number) {
        char[] suffix = {' ', 'K', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

    public static void loadPdf(Context context, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setPackage("com.google.android.apps.docs");
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static void sendEmail(Context context, String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "" + email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(Intent.createChooser(emailIntent, ""));
    }

    public static void shareContent(final Context context, final String content, String imageUrl) {
        if (!imageUrl.equals("")) {
            Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            try {
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.setType("image/*");
                                sendIntent.putExtra(Intent.EXTRA_TEXT, content);
                                sendIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(context, resource));
                                try {
                                    context.startActivity(Intent.createChooser(sendIntent, "Share"));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        } else {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, content);
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        }
    }


    //Preference methods
    public static void setPrefValue(String key, String value, Context context) {
        Preferences.setValue(context, key, value);
    }

    public static void setPrefBool(String key, boolean value, Context context) {
        Preferences.setValue(context, key, value);
    }

    public static String getPrefValue(String key, Context context) {
        return Preferences.getValueString(context, key);
    }

    public static void phoneCall(String number, Context context) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    public static boolean getPrefBool(String key, Context context) {
        return Preferences.getValueBoolean(context, key, false);
    }

    public static boolean getBooleanExtra(String key, Context context) {
        return ((Activity) context).getIntent().getBooleanExtra(key, false);
    }

    public static String getStringExtra(String key, Context context) {
        if (((Activity) context).getIntent().getStringExtra(key) != null) {
            return ((Activity) context).getIntent().getStringExtra(key);
        } else {
            return "";
        }
    }

    public static int getIntExtra(String key, Context context) {
        return ((Activity) context).getIntent().getIntExtra(key, 0);
    }


    //Network helper
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            } else {
                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            showInfoDialog(context, context.getString(R.string.txt_internet_warning));
            return false;
        }
        return false;
    }


//    // Api Helper
//    public static ApiInterface getApiInterface() {
//        return ApiClient.getClient().create(ApiInterface.class);
//    }
//
//    public static String getBasicAuth(Context context) {
//        String auth = "";
//        if (!getPrefValue(Preferences.ACCESS_TOKEN, context).equals("")) {
//            auth = "Bearer " + getPrefValue(Preferences.ACCESS_TOKEN, context);
//            Log.e("TAG", "Basic Auth : " + auth);
//        }
//        return auth;
//    }

//    public static String printRes(Response<?> response) {
//        Log.e(TAG, "Url : " + response.raw().request().url());
//        Log.e(TAG, "Response Code : " + response.code());
//        Log.e(TAG, "Response : " +
//                new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
//        Gson gson = new GsonBuilder().create();
//        ErrorData error;
//        if (response.code() != 200) {
//            try {
//                assert response.errorBody() != null;
//                error = gson.fromJson(response.errorBody().string(), ErrorData.class);
//                return error.getMessage();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return "";
//    }

    public static void printParams(Map<?, ?> map) {
        Log.e(TAG, "Params Map : " + map.toString());
        Log.e(TAG, "Params : " +
                new GsonBuilder().setPrettyPrinting().create().toJson(map));
    }

//    public static void printError(Call<?> call, Throwable t) {
//        Log.e(TAG, "Url : " + t.getMessage());
//        if (t instanceof SocketTimeoutException) {
//            Log.e(TAG, "Connection Timeout");
//        } else if (t instanceof IOException) {
//            Log.e(TAG, "Timeout");
//        } else {
//            if (call.isCanceled()) {
//                Log.e(TAG, "Call was cancelled forcefully");
//            } else {
//                Log.e(TAG, "Network Error :: " + t.getLocalizedMessage());
//            }
//        }
//    }

//    public static RequestBody getRequestBody(String value) {
//        try {
//            return RequestBody.create(value, MediaType.parse("multipart/form-data"));
//        } catch (Exception e) {
//            return RequestBody.create("", MediaType.parse("multipart/form-data"));
//        }
//    }


    //Keyboard handler
    public static void dismissKeyboard(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    // Image loader
    public static void loadStorageImage(final String url, final ImageView imageView, Context context) {
        startLoading(url, imageView, false, false, context);
    }

    public static void loadNetworkImage(final String url, final ImageView imageView, Context context) {
        startLoading(url, imageView, false, true, context);
    }

    public static void loadNetworkProfile(final String url, final ImageView imageView, Context context) {
        startLoading(url, imageView, true, true, context);
    }

    private static void startLoading(final String url, final ImageView imageView, final boolean isProfile, boolean isNetwork, final Context context) {
        try {
            if (!url.equals("")) {
                Glide.with(context)
                        .load(isNetwork ? url : Uri.fromFile(new File(url)))
                        .placeholder(isProfile
                                ? getMyDrawable(AppConfig.getInstance().getProfilePlaceholder(), context)
                                : getMyDrawable(AppConfig.getInstance().getNoImagePlaceholder(), context))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                imageError(imageView, isProfile, context);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(imageView);
            } else {
                imageError(imageView, isProfile, context);
            }
        } catch (NullPointerException e) {
            imageError(imageView, isProfile, context);
        }
    }

    private static void imageError(final ImageView imageView, boolean isProfile, Context context) {
        imageView.setImageDrawable(isProfile
                ? getMyDrawable(AppConfig.getInstance().getProfilePlaceholder(), context)
                : getMyDrawable(AppConfig.getInstance().getNoImagePlaceholder(), context));
    }


    //Calendar & time
    public static Calendar getLocalTime(String time) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = df.parse(time);
            df.setTimeZone(TimeZone.getDefault());
            assert date != null;
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTimeAgo(long time) {
        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;
        int WEEK_MILLIS = 7 * DAY_MILLIS;

        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a min ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " min ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hr ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hr ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else if (diff < 7 * DAY_MILLIS) {
            return diff / DAY_MILLIS + " d ago";
        } else if (diff < 2 * WEEK_MILLIS) {
            return "a w ago";
        } else {
            return diff / WEEK_MILLIS + " w ago";
        }
    }


    //Image processing
    public static void selectImage(final AppCompatActivity activity, final File cameraFile, final boolean isCrop, final Context context) {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA};
        PermissionUtil.permission(activity, perms, new PermissionHandler() {
            @Override
            public void onGranted() {
                openGallery(activity, false, isCrop, context);
            }

            @Override
            public void onDenied() {
                toast(context.getString(R.string.txt_permission_deny), context);
            }
        });
    }

    //Image processing
    public static void selectImage(final Fragment fragment, final File cameraFile, final boolean isCrop, final Context context) {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA};
        PermissionUtil.permission(((AppCompatActivity) context), perms, new PermissionHandler() {
            @Override
            public void onGranted() {
                openGallery(fragment, false, isCrop, context);
            }

            @Override
            public void onDenied() {
                toast(context.getString(R.string.txt_permission_deny), context);
            }
        });
    }


    private static void openCamera(File cameraFile, boolean isFront, boolean isCrop, Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(context.getPackageManager()) != null) {

            Uri photoURI;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoURI = FileProvider.getUriForFile(context,
                        AppConfig.getInstance().getApplicationId() + ".provider",
                        cameraFile);
            } else {
                photoURI = Uri.fromFile(cameraFile);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            intent.putExtra("return-data", true);
            if (isFront) {
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            }
            ((AppCompatActivity) context).startActivityForResult(intent, isCrop ? REQUEST_CAMERA_IMAGE_CROP : REQUEST_CAMERA);
        }
    }

    private static void openGallery(AppCompatActivity activity, boolean isMultiple, boolean isCrop, Context context) {
        Matisse.from(activity)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .countable(false)
                .maxSelectable(isMultiple ? 5 : 1)
                .showSingleMediaType(true)
                .gridExpectedSize(context.getResources().getDimensionPixelSize(R.dimen._100sdp))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new MyGlideEngine())
                .theme(R.style.Matisse_Zhihu)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, context.getPackageName() + ".provider"))
                .forResult(isCrop ? REQUEST_IMAGE_CROP : REQUEST_IMAGE);
    }


    private static void openGallery(Fragment fragment, boolean isMultiple, boolean isCrop, Context context) {
        Matisse.from(fragment)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .countable(false)
                .maxSelectable(isMultiple ? 5 : 1)
                .showSingleMediaType(true)
                .gridExpectedSize(context.getResources().getDimensionPixelSize(R.dimen._100sdp))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new MyGlideEngine())
                .theme(R.style.Matisse_Zhihu)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, context.getPackageName() + ".provider"))
                .forResult(isCrop ? REQUEST_IMAGE_CROP : REQUEST_IMAGE);
    }


    public static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public static void cropImage(String path, int rotation, Context context, final ImageReceiveListener listener) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(new File(path).getAbsolutePath(), options);
        if (rotation != 0) {
            bitmap = rotateImage(bitmap, rotation);
        } else {
            bitmap = BitmapFactory.decodeFile(new File(path).getAbsolutePath(), options);
        }
        show(context);
        new DialogCropHelper(context, bitmap, new DialogCropHelper.OnResultReceived() {
            @Override
            public void onResult(String cropImagePath) {
                hide();
                if (!cropImagePath.equals("")) {
                    ArrayList<String> images = new ArrayList<>();
                    images.add(cropImagePath);
                    listener.onImageReceived(images);
                }
            }
        }, false);
    }

    private static Bitmap rotateImage(Bitmap source, float rotation) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public static void setSelection(EditText editText) {
        try {
            if (!editText.getText().toString().equals("")) {
                editText.setSelection(editText.getText().toString().length());
            } else {
                editText.setSelection(0);
            }
        } catch (NullPointerException e) {
            editText.setSelection(0);
        }
    }

    public static void setListDataStatus(int size, String msg, TextView textView) {
        textView.setText(msg);
        if (size > 0) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    static class MyGlideEngine implements ImageEngine {

        @Override
        public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
            RequestOptions requestOptions = new RequestOptions()
                    .override(resize, resize)
                    .centerCrop()
                    .placeholder(placeholder);
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .apply(requestOptions)
                    .into(imageView);
        }

        @Override
        public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
            RequestOptions requestOptions = new RequestOptions()
                    .override(resize, resize)
                    .centerCrop()
                    .placeholder(placeholder);
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .apply(requestOptions)
                    .into(imageView);
        }

        @Override
        public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
            RequestOptions requestOptions = new RequestOptions()
                    .override(resizeX, resizeY)
                    .priority(Priority.HIGH)
                    .centerCrop();
            Glide.with(context)
                    .load(uri)
                    .apply(requestOptions)
                    .into(imageView);
        }

        @Override
        public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {

        }


        @Override
        public boolean supportAnimatedGif() {
            return true;
        }

    }

    private static Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    public static String compressImage(Context context, String path) {
        return ImageCompress.compressImage(
                context,
                1516.0f,
                1212.0f,
                path,
                getCompressFilePath(context).toString());
    }

    //Path generator
    public static File getFilePath(Context context) {
        String state = Environment.getExternalStorageState();
        File file;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            file = new File(Environment.getExternalStorageDirectory(), AppConfig.getInstance().getAPP_NAME() + "/");
        } else {
            file = new File(context.getFilesDir(), AppConfig.getInstance().getAPP_NAME() + "/");
        }
        if (!file.exists())
            file.mkdirs();
        return new File(file, getRandomImageName(context));
    }

    public static String getRandomImageName(Context context) {
        Random r = new Random();
        int i1 = r.nextInt(1000 - 1) + 65;
        return context.getString(R.string.app_name).replace(" ", "_") + "_" + i1 + ".png";
    }

    public static File getCompressFilePath(Context context) {
        String state = Environment.getExternalStorageState();
        File file;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            file = new File(Environment.getExternalStorageDirectory(), AppConfig.getInstance().getAPP_NAME() + "/compress/");
        } else {
            file = new File(context.getFilesDir(), AppConfig.getInstance().getAPP_NAME() + "/compress/");
        }
        if (!file.exists())
            file.mkdirs();
        return new File(file, getRandomImageName(context));
    }

}
