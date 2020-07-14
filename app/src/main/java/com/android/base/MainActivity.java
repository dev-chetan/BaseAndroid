package com.android.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.rb.base.BaseActivity;
import com.android.rb.helper.BottomSheetHelper;
import com.android.rb.models.BottomSheetData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                List<BottomSheetData> list = new ArrayList<>();
//                list.add(new BottomSheetData(true, "dsdsd"));
//                list.add(new BottomSheetData(true, "dsdsd 1"));
//                list.add(new BottomSheetData(true, "dsdsd 3"));
//                list.add(new BottomSheetData(true, "dsdsd 5"));
//                list.add(new BottomSheetData(true, "dsdsd e"));
//                new BottomSheetHelper(MainActivity.this, "TEST", list, new BottomSheetHelper.OnBottomSheetResult() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onResult(List<BottomSheetData> arrayList) {
//                        for (int i = 0; i < arrayList.size(); i++) {
//                            if (arrayList.get(i).isSelect()) {
//                                Log.e("TAG", "onResult: " + arrayList.get(i).getTitle().toString());
//                                break;
//                            }
//                        }
//                    }
//                }, true);
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