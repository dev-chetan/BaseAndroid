package com.android.base;

import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.android.base.databinding.ActivityMainBinding;
import com.android.rb.base.BaseActivity;
import com.android.rb.helper.DialogMultiImageHelper;
import com.android.rb.interf.RBMultipleImagePickerListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        rbImageMultiPicker(new RBMultipleImagePickerListener() {
            @Override
            public void onRBPickerResult(ArrayList<DialogMultiImageHelper.ImageData> arrayList) {
                Log.e("TAG", "onRBPickerResult: "+arrayList.size());
            }
        },4);
    }
}