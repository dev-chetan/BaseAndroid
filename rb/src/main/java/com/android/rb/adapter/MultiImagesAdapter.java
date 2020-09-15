package com.android.rb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.rb.R;
import com.android.rb.base.BaseAdapter;
import com.android.rb.databinding.ItemImagesBinding;
import com.android.rb.helper.DialogMultiImageHelper;
import com.android.rb.interf.OnChangeImage;
import com.android.rb.widget.RBNonSwipePageView;

import java.util.ArrayList;


public class MultiImagesAdapter extends BaseAdapter<MultiImagesAdapter.ViewHolder> implements ViewPager.OnPageChangeListener {

    private ArrayList<DialogMultiImageHelper.ImageData> arrayList;
    private RBNonSwipePageView pageView;
    private OnChangeImage onChangeImage;

    public MultiImagesAdapter(ArrayList<DialogMultiImageHelper.ImageData> arrayList, RBNonSwipePageView pageView, OnChangeImage onChangeImage) {
        this.arrayList = arrayList;
        this.pageView = pageView;
        this.onChangeImage = onChangeImage;
        this.pageView.addOnPageChangeListener(this);
    }

    @Override
    protected ViewHolder onCreateView(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(ItemImagesBinding.bind(getLayout(viewGroup)));
    }

    /**
     * Return View For Adapter
     *
     * @param viewGroup
     * @return
     */
    private View getLayout(ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_images, viewGroup, false);
    }

    @Override
    protected void bindRViewHolder(ViewHolder viewHolder, final int position) {
        final DialogMultiImageHelper.ImageData imageData = arrayList.get(position);
        if (imageData.isSelected())
            viewHolder.binding.rlMain.setBackgroundColor(getMyColor(R.color.colorPrimary));
        else {
            viewHolder.binding.rlMain.setBackgroundColor(getMyColor(R.color.colorGray));
        }
        loadNetworkImage(imageData.getImgUri(), viewHolder.binding.iv);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageView.setCurrentItem(position);
            }
        });
    }

    private void setSelection(int position) {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setSelected(false);
        }
        arrayList.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    protected int getListCounter() {
        return arrayList == null ? 0 : arrayList.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemImagesBinding binding;

        public ViewHolder(@NonNull ItemImagesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
