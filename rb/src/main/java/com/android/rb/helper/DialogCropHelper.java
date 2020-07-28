package com.android.rb.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;

import com.android.rb.R;
import com.android.rb.comman.BaseHelper;
import com.android.rb.databinding.DialogCropLayoutBinding;

import java.io.File;


public class DialogCropHelper extends Dialog {
    private Dialog dialog = null;
    private LayoutInflater inflate = null;
    private OnResultReceived onResultReceived;

    public DialogCropHelper(Context context, Bitmap bitmap, OnResultReceived onResultReceived, boolean cancelable) {
        super(context);
        this.onResultReceived = onResultReceived;
        showDialog(context, bitmap, cancelable);
    }

    public void showDialog(final Context context, final Bitmap bitmap, final boolean cancelable) {
        inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final DialogCropLayoutBinding binding = DataBindingUtil.inflate(inflate,
                R.layout.dialog_crop_layout, null, false);

        binding.CropImageView.setAspectRatio(15, 15);
        binding.CropImageView.setImageBitmap(bitmap);

        final int[] roted = {0};
        binding.imgRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roted[0] = roted[0] + 90;
                binding.CropImageView.setRotation(roted[0]);
            }
        });

        binding.tvCrop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                final Bitmap[] bitmap = new Bitmap[1];
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected void onPreExecute() {
                        dialog.dismiss();
                        super.onPreExecute();
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        bitmap[0] = binding.CropImageView.getCroppedImage();
                        File file = BaseHelper.getInstance().saveBitmap(context, bitmap[0]);
                        return file.getAbsolutePath();
                    }

                    @Override
                    protected void onPostExecute(final String s) {
                        super.onPostExecute(s);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hide();
                                onResultReceived.onResult(s);
                            }
                        });
                    }
                }.execute();
            }
        });
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResultReceived.onResult("");
                dialog.dismiss();
            }
        });

        try {
            dialog = new Dialog(context, R.style.ProgressBarDialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(binding.getRoot());
            dialog.setCancelable(cancelable);
            dialog.show();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void hide() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }


    public interface OnResultReceived {
        public void onResult(String cropImagePath);
    }
}