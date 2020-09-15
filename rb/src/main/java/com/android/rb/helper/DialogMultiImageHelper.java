package com.android.rb.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;

import com.android.rb.R;
import com.android.rb.adapter.MultiImagePageViewAdapter;
import com.android.rb.adapter.MultiImagesAdapter;
import com.android.rb.comman.BaseHelper;
import com.android.rb.comman.FileUtils;
import com.android.rb.databinding.DialogMultiImagesLayoutBinding;
import com.android.rb.interf.OnChangeImage;
import com.android.rb.interf.RBMultipleImagePickerListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DialogMultiImageHelper extends Dialog {
    private RBMultipleImagePickerListener rbMultipleImagePickerListener;
    private Dialog dialog;
    private ArrayList<ImageData> arrayListForImage;

    public DialogMultiImageHelper(Context context, List<? extends Uri> imageArrayList, RBMultipleImagePickerListener rbMultipleImagePickerListener, boolean cancelable) {
        super(context);
        this.rbMultipleImagePickerListener = rbMultipleImagePickerListener;
        showDialog(context, imageArrayList, cancelable);
    }

    /**
     * Init Dialog
     *
     * @param context
     * @param imageArrayList
     * @param cancelable
     */
    public void showDialog(final Context context, final List<? extends Uri> imageArrayList, final boolean cancelable) {
        final DialogMultiImagesLayoutBinding binding = getDialogBinding(context);
        setListOfImages(binding, context, imageArrayList);
        binding.floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbMultipleImagePickerListener.onRBPickerResult(arrayListForImage);
                hide();
            }
        });
        try {
            dialog = getDialog(context, binding.getRoot(), cancelable);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set Bottom List Data
     *
     * @param binding
     * @param context
     * @param arrayList
     */
    private void setListOfImages(final DialogMultiImagesLayoutBinding binding, final Context context, List<? extends Uri> arrayList) {

        final MultiImagePageViewAdapter[] viewAdapter = {null};
        MultiImagesAdapter adapter = null;

        arrayListForImage = getArrayListForImage(arrayList, context);
        final OnChangeImage onChangeImage = new OnChangeImage() {
            @Override
            public void change(final int position, final String image) {

            }
        };

        viewAdapter[0] = new MultiImagePageViewAdapter(arrayListForImage, onChangeImage);
        binding.pageView.setAdapter(viewAdapter[0]);
        viewAdapter[0].notifyDataSetChanged();

        BaseHelper.getInstance().setLinearLayoutManagerHorizontal(binding.recyclerView, context);
        adapter = new MultiImagesAdapter(arrayListForImage, binding.pageView, onChangeImage);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        final MultiImagesAdapter finalAdapter = adapter;
        final MultiImagePageViewAdapter finalViewAdapter1 = viewAdapter[0];
        binding.ivCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageData imageData = arrayListForImage.get(binding.pageView.getCurrentItem());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(new File(imageData.getImgUri()).getAbsolutePath(), options);
                BitmapFactory.decodeFile(new File(imageData.getImgUri()).getAbsolutePath(), options);
                new DialogCropHelper(context, bitmap, new DialogCropHelper.OnResultReceived() {
                    @Override
                    public void onResult(String cropImagePath) {
                        arrayListForImage.get(binding.pageView.getCurrentItem()).setImgUri(cropImagePath);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int currentItem = binding.pageView.getCurrentItem();
                                finalAdapter.notifyDataSetChanged();
                                viewAdapter[0] = new MultiImagePageViewAdapter(arrayListForImage, onChangeImage);
                                binding.pageView.setAdapter(viewAdapter[0]);
                                binding.pageView.setCurrentItem(currentItem);
                                viewAdapter[0].notifyDataSetChanged();
                            }
                        });
                    }
                }, false);
            }
        });


    }

    private ArrayList<ImageData> getArrayListForImage(List<? extends Uri> arrayList, Context context) {
        ArrayList<ImageData> imageData = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            imageData.add(new ImageData(i == 0, FileUtils.getPath(context, arrayList.get(i))));
        }
        return imageData;
    }

    /**
     * Get Data Binding
     *
     * @param context
     * @return
     */
    private DialogMultiImagesLayoutBinding getDialogBinding(Context context) {
        return DataBindingUtil.inflate(getInflate(context),
                R.layout.dialog_multi_images_layout, null, false);
    }

    /**
     * Get Layout Inflater
     *
     * @param context
     * @return
     */
    private LayoutInflater getInflate(Context context) {
        return (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Create Dialog And Get Instance
     *
     * @param context
     * @param root
     * @param cancelable
     * @return
     */
    private Dialog getDialog(Context context, View root, boolean cancelable) {
        dialog = new Dialog(context, R.style.ProgressBarDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }

    /**
     * Dialog Dismiss
     */
    public void hide() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    /**
     * For Call Back Images
     */
    public interface OnResultReceived {
        public void onResult(String cropImagePath);
    }

    public class ImageData {
        private boolean isSelected;
        private String imgUri;

        public ImageData(boolean isSelected, String imgUri) {
            this.isSelected = isSelected;
            this.imgUri = imgUri;
        }

        public void setImgUri(String imgUri) {
            this.imgUri = imgUri;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public String getImgUri() {
            return imgUri;
        }
    }
}