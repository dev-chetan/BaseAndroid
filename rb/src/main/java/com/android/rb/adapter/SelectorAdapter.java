package com.android.rb.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rb.R;
import com.android.rb.base.BaseAdapter;
import com.android.rb.databinding.ItemSelectorBinding;
import com.android.rb.helper.BottomSheetHelper;
import com.android.rb.models.BottomSheetData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class SelectorAdapter extends BaseAdapter<SelectorAdapter.ViewHolder> {

    public List<BottomSheetData> arrayList;
    private BottomSheetDialog bottomSheetDialog;
    BottomSheetHelper.OnBottomSheetResult onBottomSheetResult;
    private boolean isMultiple;

    public SelectorAdapter(List<BottomSheetData> arrayList, BottomSheetDialog bottomSheetDialog, BottomSheetHelper.OnBottomSheetResult onBottomSheetResult, boolean isMultiple) {
        this.arrayList = arrayList;
        this.bottomSheetDialog = bottomSheetDialog;
        this.onBottomSheetResult = onBottomSheetResult;
        this.isMultiple = isMultiple;
    }

    @Override
    protected ViewHolder onCreateView(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_selector, viewGroup, false);
        return new ViewHolder(view, ItemSelectorBinding.bind(view));
    }

    @Override
    protected void bindRViewHolder(ViewHolder holder, final int position) {
        final BottomSheetData bottomSheetData = arrayList.get(position);
        holder.binding.tvCountry.setText(bottomSheetData.getTitle());

        if (bottomSheetData.isSelect()) {
            holder.binding.imgSelect.setImageDrawable(getMyDrawable(R.drawable.ic_baseline_radio_button_checked_24));
            holder.binding.imgSelect.setColorFilter(getMyColor(android.R.color.black));
        } else {
            holder.binding.imgSelect.setImageDrawable(getMyDrawable(R.drawable.ic_baseline_radio_button_unchecked_24));
            holder.binding.imgSelect.setColorFilter(getMyColor(android.R.color.darker_gray));
        }

        holder.binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMultiple) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).setSelect(false);
                    }
                    arrayList.get(position).setSelect(true);
                    notifyDataSetChanged();
                    onBottomSheetResult.onResult(arrayList);
                    bottomSheetDialog.dismiss();
                } else {
                    if (arrayList.get(position).isSelect()) {
                        arrayList.get(position).setSelect(false);
                    } else {
                        arrayList.get(position).setSelect(true);
                    }
                    notifyDataSetChanged();
                }
            }
        });
        holder.binding.imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMultiple) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).setSelect(false);
                    }
                    arrayList.get(position).setSelect(true);
                    notifyDataSetChanged();
                    onBottomSheetResult.onResult(arrayList);
                    bottomSheetDialog.dismiss();
                } else {
                    if (arrayList.get(position).isSelect()) {
                        arrayList.get(position).setSelect(false);
                    } else {
                        arrayList.get(position).setSelect(true);
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected int getListCounter() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSelectorBinding binding;

        public ViewHolder(@NonNull View itemView, @NonNull ItemSelectorBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
