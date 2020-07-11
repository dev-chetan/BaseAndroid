package com.android.rb.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.rb.AppConfig;
import com.android.rb.R;
import com.android.rb.adapter.SelectorAdapter;
import com.android.rb.databinding.BottomSheetBinding;
import com.android.rb.models.BottomSheetData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

@SuppressLint("ValidFragment")
public class BottomSheetHelper {
    public interface OnBottomSheetResult {
        public void onResult(List<BottomSheetData> arrayList);
    }

    public BottomSheetHelper(Context context, String title, List<BottomSheetData> arrayList, OnBottomSheetResult onBottomSheetResult) {
        showBottomMediaDialog(context, title, arrayList, onBottomSheetResult);
    }

    private void showBottomMediaDialog(Context context, String title, List<BottomSheetData> arrayList, OnBottomSheetResult onBottomSheetResult) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        @SuppressLint("InflateParams") View sheetView = ((Activity) context).getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        BottomSheetBinding sheetLayoutBinding = BottomSheetBinding.bind(sheetView);

        if (android.R.color.white == AppConfig.getInstance().getColorPrimary()) {
            sheetLayoutBinding.tvTitle.setTextColor(context.getResources().getColor(android.R.color.black));
        } else {
            sheetLayoutBinding.tvTitle.setTextColor(context.getResources().getColor(android.R.color.white));
        }

        sheetLayoutBinding.relTitle.setBackground(context.getDrawable(AppConfig.getInstance().getColorPrimary()));
        sheetLayoutBinding.rlMain.setBackground(context.getDrawable(AppConfig.getInstance().getColorBackground()));
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();

        sheetLayoutBinding.tvTitle.setText(title);

        SelectorAdapter adapter = new SelectorAdapter(arrayList, bottomSheetDialog, onBottomSheetResult);
        sheetLayoutBinding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        sheetLayoutBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }
}