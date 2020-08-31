package com.android.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.rb.base.BaseActivity;
import com.android.rb.helper.BottomSheetHelper;
import com.android.rb.models.BottomSheetData;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private List<BottomSheetData> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
//        loadNetworkPDF(pdfUrl, (ImageView) findViewById(R.id.iv));
        // imageGallery();
        // bottomSheetDialog();
        bottomSheetDialog();
    }

    private void bottomSheetDialog() {
        list.add(new BottomSheetData(false, "Android", "https://i.picsum.photos/id/935/45/25.jpg?hmac=SdeU4zlrDq0ldhD4JvpRiKfmCDPqzCGxF-FLLkFkHs4"));
        list.add(new BottomSheetData(false, "Ios", "https://i.picsum.photos/id/935/45/25.jpg?hmac=SdeU4zlrDq0ldhD4JvpRiKfmCDPqzCGxF-FLLkFkHs4"));
        list.add(new BottomSheetData(false, "Java", "https://i.picsum.photos/id/935/45/25.jpg?hmac=SdeU4zlrDq0ldhD4JvpRiKfmCDPqzCGxF-FLLkFkHs4"));

        //Bottom Sheet
        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BottomSheetHelper(getContext(), "Testing Bottom Sheet", list, new BottomSheetHelper.OnBottomSheetResult() {
                    @Override
                    public void onResult(List<BottomSheetData> arrayList) {
                        list.clear();
                        list.addAll(arrayList);
                        Log.e("TAG", "onResult: ");
                    }
                }, BottomSheetHelper.Type.multipleSelection);
                //BottomSheetHelper.Type.singleSelection  //Single Selection
                //BottomSheetHelper.Type.singleSearch //Single Selection with Search
                //BottomSheetHelper.Type.multipleSelection //Multiple Selection
                //BottomSheetHelper.Type.multipleSearch //Multiple Selection with Search
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