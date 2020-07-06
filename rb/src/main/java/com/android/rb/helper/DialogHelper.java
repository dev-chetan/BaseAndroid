package com.android.rb.helper;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

@SuppressLint("ValidFragment")
public class DialogHelper extends DialogFragment {

    private DialogCallBack dialogCallBack;
    private String title = "", msg, btnNegative = "", btnPositive = "";
    private String TYPE = DialogStatus.DIALOG_DEFAULT;
    private String TAG = getClass().getSimpleName();
    //List<LanguageItem> languageList;

    @SuppressLint("ValidFragment")
    public DialogHelper(String msg, String btnNegative, String btnPositive, DialogCallBack dialogCallBack, String ENUMDIALOGType) {
        this.msg = msg;
        this.btnNegative = btnNegative;
        this.btnPositive = btnPositive;
        this.dialogCallBack = dialogCallBack;
        this.TYPE = ENUMDIALOGType;
    }

    @SuppressLint("ValidFragment")
    public DialogHelper(String btnNegative, String btnPositive, DialogCallBack dialogCallBack, String ENUMDIALOGType) {
        this.btnNegative = btnNegative;
        this.btnPositive = btnPositive;
        this.dialogCallBack = dialogCallBack;
        this.TYPE = ENUMDIALOGType;
    }

//    @SuppressLint("ValidFragment")
//    public DialogHelper(String btnNegative, String btnPositive, List<LanguageItem> languageList, DialogCallBack dialogCallBack, int dialogType) {
//        this.languageList = languageList;
//        this.btnNegative = btnNegative;
//        this.btnPositive = btnPositive;
//        this.dialogCallBack = dialogCallBack;
//        this.TYPE = dialogType;
//    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        switch (TYPE) {
            case DialogStatus.DIALOG_SELECT_LANGUAGE:
                return simpleDialog();
            default:
                return simpleDialog();
        }
    }

    /*private Dialog selectLanguage() {
        DialogSelectLanguageBinding binding;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select_language, null);
        builder.setView(dialogView);
        binding = DialogSelectLanguageBinding.bind(dialogView);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SelectLanguageAdapter adapter = new SelectLanguageAdapter(languageList);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (!btnPositive.equals("") || btnPositive != null)
            builder.setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialogCallBack.onResult(1);
                }
            });
        if (!btnNegative.equals("") || btnNegative != null)
            builder.setNegativeButton(btnNegative, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialogCallBack.onResult(0);
                }
            });
        final AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }*/


    @SuppressLint("SetTextI18n")
    private Dialog simpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (!title.equals("") || title != null) builder.setTitle(title);
        builder.setMessage(msg);

        if (!btnPositive.equals("") || btnPositive != null)
            builder.setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialogCallBack.onResult(1);
                }
            });

        if (!btnNegative.equals("") || btnNegative != null)
            builder.setNegativeButton(btnNegative, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialogCallBack.onResult(0);
                }
            });

        return builder.create();
    }

    public interface DialogCallBack {
        public void onResult(int resultCode);
    }
}