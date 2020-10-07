package com.android.rb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.rb.R;
import com.android.rb.base.BasePagerAdapter;
import com.android.rb.databinding.ItemPageImageBinding;
import com.android.rb.helper.DialogMultiImageHelper;
import com.android.rb.interf.OnChangeImage;

import java.util.ArrayList;

public class MultiImagePageViewAdapter extends BasePagerAdapter {

    private ArrayList<DialogMultiImageHelper.ImageData> arrayList;
    private OnChangeImage onChangeImage;

    public MultiImagePageViewAdapter(ArrayList<DialogMultiImageHelper.ImageData> arrayList, OnChangeImage onChangeImage) {
        this.arrayList = arrayList;
        this.onChangeImage = onChangeImage;
    }

    @Override
    protected View getItem(ViewGroup container, int position) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_page_image, container, false);
        ItemPageImageBinding binding = ItemPageImageBinding.bind(inflate);
        setData(position, binding);
        return binding.getRoot();
    }

    private void setData(int position, ItemPageImageBinding binding) {
        loadNetworkImage(arrayList.get(position).getPath(), binding.iv);
    }

    @Override
    public int getCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    public void getView() {

    }
}
