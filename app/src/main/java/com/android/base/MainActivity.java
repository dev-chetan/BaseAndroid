package com.android.base;

import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.android.base.databinding.ActivityMainBinding;
import com.android.rb.base.BaseActivity;
import com.android.rb.helper.BottomSheetHelper;
import com.android.rb.helper.DialogMultiImageHelper;
import com.android.rb.interf.RBImagePickerListener;
import com.android.rb.interf.RBMultipleImagePickerListener;
import com.android.rb.models.BottomSheetData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);



        new BottomSheetHelper(getContext(), "Test", getListForBottomSheet(), arrayList -> {

        }, BottomSheetHelper.Type.singleSearch);
    }

    private List<BottomSheetData> getListForBottomSheet() {
        List<BottomSheetData> sheetData = new ArrayList<>();
        sheetData.add(new BottomSheetData(false,"JJJ"));
        sheetData.add(new BottomSheetData(false,"JJJ"));
        sheetData.add(new BottomSheetData(false,"JJJ"));
        sheetData.add(new BottomSheetData(false,"JJJ"));
        return sheetData;
    }
}