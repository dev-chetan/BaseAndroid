package com.android.rb.interf;

import com.android.rb.helper.DialogMultiImageHelper;

import java.util.ArrayList;

public interface RBMultipleImagePickerListener {
    public void onRBPickerResult(ArrayList<DialogMultiImageHelper.ImageData> arrayList);
}