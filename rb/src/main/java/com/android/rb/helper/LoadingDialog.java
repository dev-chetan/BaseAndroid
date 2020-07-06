package com.android.rb.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.android.rb.R;

public class LoadingDialog extends Dialog {
    private Dialog dialog = null;
    private LayoutInflater inflate = null;

    public LoadingDialog(Context context, boolean cancelable) {
        super(context);
        showDialog(context, cancelable);
    }

    public void showDialog(Context context,
                           boolean cancelable) {
        inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view_lyt = inflate.inflate(R.layout.dialog_loading, null);
        try {
            dialog = new Dialog(context, R.style.ProgressBarDialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view_lyt);
            dialog.setCancelable(cancelable);
            dialog.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void hide() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

}