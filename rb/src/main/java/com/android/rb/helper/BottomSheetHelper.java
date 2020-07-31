package com.android.rb.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.rb.AppConfig;
import com.android.rb.R;
import com.android.rb.adapter.SelectorAdapter;
import com.android.rb.databinding.BottomSheetBinding;
import com.android.rb.models.BottomSheetData;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

@SuppressLint("ValidFragment")
public class BottomSheetHelper {

    public enum Type {
        multipleSelection,
        singleSelection,
        singleSearch,
        multipleSearch
    }

    private Type type = Type.singleSelection;

    public interface OnBottomSheetResult {
        public void onResult(List<BottomSheetData> arrayList);
    }


    public BottomSheetHelper(Context context, String title, List<BottomSheetData> arrayList, OnBottomSheetResult onBottomSheetResult, Type type) {
        this.type = type;
        showBottomMediaDialog(context, title, arrayList, onBottomSheetResult);
    }

    public BottomSheetHelper(Context context, String title, List<BottomSheetData> arrayList, OnBottomSheetResult onBottomSheetResult) {
        showBottomMediaDialog(context, title, arrayList, onBottomSheetResult);
    }

//    public BottomSheetHelper(Context context, String title, List<BottomSheetData> arrayList, OnBottomSheetResult onBottomSheetResult, boolean isMultiple) {
//        this.isMultiple = isMultiple;
//        showBottomMediaDialog(context, title, arrayList, onBottomSheetResult);
//    }
//
//    public BottomSheetHelper(Context context, String title, List<BottomSheetData> arrayList, OnBottomSheetResult onBottomSheetResult, boolean isMultiple, String isSearch) {
//        this.isMultiple = isMultiple;
//        this.isSearch = isSearch;
//        showBottomMediaDialog(context, title, arrayList, onBottomSheetResult);
//    }
//
//    public BottomSheetHelper(Context context, String title, List<BottomSheetData> arrayList, OnBottomSheetResult onBottomSheetResult, String isSearch) {
//        this.isSearch = isSearch;
//        showBottomMediaDialog(context, title, arrayList, onBottomSheetResult);
//    }

    private void showBottomMediaDialog(Context context, String title, final List<BottomSheetData> arrayList, final OnBottomSheetResult onBottomSheetResult) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        @SuppressLint("InflateParams") View sheetView = ((Activity) context).getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        final BottomSheetBinding sheetLayoutBinding = BottomSheetBinding.bind(sheetView);

//        if (this.type == Type.multipleSearch || this.type == Type.multipleSelection)
//            sheetLayoutBinding.tvSave.setVisibility(View.VISIBLE);

        if (this.type == Type.singleSearch || this.type == Type.multipleSearch)
            sheetLayoutBinding.rlSearch.setVisibility(View.VISIBLE);

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

        final SelectorAdapter adapter = new SelectorAdapter(arrayList, bottomSheetDialog, onBottomSheetResult, this.type);
        sheetLayoutBinding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        sheetLayoutBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        if (this.type == Type.singleSearch || this.type == Type.multipleSearch) {
            sheetLayoutBinding.search.edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (adapter != null)
                        adapter.getFilter().filter(s.toString());
                }
            });

            sheetLayoutBinding.search.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!sheetLayoutBinding.search.edtSearch.getText().toString().isEmpty()) {
                        sheetLayoutBinding.search.edtSearch.setText("");
                    }
                }
            });
        }

        //BottomSheetBehavior.from(sheetLayoutBinding.rlMain).setState(BottomSheetBehavior.STATE_EXPANDED);
        if (this.type == Type.singleSearch || this.type == Type.multipleSearch) {
            setupFullHeight(bottomSheetDialog, context);
        }
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                onBottomSheetResult.onResult(adapter.getList(adapter.arrayList));
                bottomSheetDialog.dismiss();
                dialog.dismiss();
            }
        });
    }


    private void setupFullHeight(BottomSheetDialog bottomSheetDialog, Context context) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight(context);
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight(Context context) {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}