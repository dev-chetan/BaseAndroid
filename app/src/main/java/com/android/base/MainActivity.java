package com.android.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.rb.base.BaseActivity;
import com.android.rb.interf.RBImagePickerListener;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setLabel() {

    }
}