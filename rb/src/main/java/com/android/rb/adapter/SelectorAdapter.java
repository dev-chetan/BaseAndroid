package com.android.rb.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rb.R;
import com.android.rb.base.BaseAdapter;
import com.android.rb.databinding.ItemSelectorBinding;
import com.android.rb.helper.BottomSheetHelper;
import com.android.rb.models.BottomSheetData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class SelectorAdapter extends BaseAdapter<SelectorAdapter.ViewHolder> {

    private static final String TAG = "SelectorAdapter";
    public List<BottomSheetData> arrayList;
    public List<BottomSheetData> arrayListFilter;
    private BottomSheetDialog bottomSheetDialog;
    BottomSheetHelper.OnBottomSheetResult onBottomSheetResult;
    BottomSheetHelper.Type type;

    public SelectorAdapter(List<BottomSheetData> arrayList, BottomSheetDialog bottomSheetDialog, BottomSheetHelper.OnBottomSheetResult onBottomSheetResult, BottomSheetHelper.Type type) {
        this.arrayList = arrayList;
        arrayListFilter = new ArrayList<>();
        arrayListFilter.addAll(this.arrayList);
        this.bottomSheetDialog = bottomSheetDialog;
        this.onBottomSheetResult = onBottomSheetResult;
        this.type = type;
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

        if (bottomSheetData.getDrawable() != 0 || bottomSheetData.getNetworkUrl() != null) {
            holder.binding.iv.setVisibility(View.VISIBLE);
            if (bottomSheetData.getDrawable() != 0) {
                holder.binding.iv.setImageDrawable(getMyDrawable(bottomSheetData.getDrawable()));
            } else {
                loadNetworkImage(bottomSheetData.getNetworkUrl(), holder.binding.iv);
            }
        } else {
            holder.binding.iv.setVisibility(View.GONE);
        }

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
                if (type == BottomSheetHelper.Type.singleSelection || type == BottomSheetHelper.Type.singleSearch) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).setSelect(false);
                    }
                    arrayList.get(position).setSelect(true);
//                    onBottomSheetResult.onResult(getList(arrayList));
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
                if (type == BottomSheetHelper.Type.singleSelection || type == BottomSheetHelper.Type.singleSearch) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).setSelect(false);
                    }
                    arrayList.get(position).setSelect(true);
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

    public List<BottomSheetData> getList(List<BottomSheetData> arrayList) {
        for (int i = 0; i < arrayListFilter.size(); i++) {
            if (isSelected(arrayListFilter.get(i).getTitle(), arrayList)) {
                arrayListFilter.get(i).setSelect(true);
            } else {
                arrayListFilter.get(i).setSelect(false);
            }
        }
        return arrayListFilter;
    }

    private boolean isSelected(String title, List<BottomSheetData> arrayList) {
        for (int j = 0; j < arrayList.size(); j++) {
            if (title.trim().equals(arrayList.get(j).getTitle().trim())) {
                if (arrayList.get(j).isSelect())
                    return true;
            }
        }
        return false;
    }

    @Override
    protected int getListCounter() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSelectorBinding binding;

        public ViewHolder(@NonNull View itemView, @NonNull ItemSelectorBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<BottomSheetData> sheetData = new ArrayList<>();
                for (int i = 0; i < arrayListFilter.size(); i++) {
                    String trimSearchText = constraint.toString().trim().toLowerCase();
                    String str = arrayListFilter.get(i).getTitle().toLowerCase().trim().toLowerCase();
                    if (str.contains(trimSearchText)) {
                        sheetData.add(arrayListFilter.get(i));
                    }
                }
                filterResults.values = sheetData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (List<BottomSheetData>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
