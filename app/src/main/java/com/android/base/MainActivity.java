package com.android.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.rb.base.BaseActivity;
import com.android.rb.helper.DialogMultiImageHelper;
import com.android.rb.interf.RBMultipleImagePickerListener;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomSheetDialog();
    }
    private void bottomSheetDialog() {
        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbImageMultiPicker(new RBMultipleImagePickerListener() {
                    @Override
                    public void onRBPickerResult(ArrayList<DialogMultiImageHelper.ImageData> arrayList) {
                        Log.e(TAG, "onRBPickerResult: " + arrayList.size());
                    }
                });
            }
        });

    }

    private void imageGallery() {
          /*For Image Gallery
        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbImagePicker(new RBImagePickerListener() {
                    @Override
                    public void onRBPickerResult(String imagePath) {
                        loadStorageImage(imagePath, (ImageView) findViewById(R.id.iv));
                    }
                }, true);
            }
        });*/
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setLabel() {

    }
}