package com.zia.notice.page.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zia on 2018/5/8.
 */
public class CommonDialog {

    private AlertDialog alertDialog;
    private View view;

    public CommonDialog(Context context, int resourceId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        view = LayoutInflater.from(context).inflate(resourceId, null);
        builder.setView(view);
        alertDialog = builder.create();
    }

    public void show() {
        alertDialog.show();
    }

    public void hide() {
        alertDialog.hide();
    }

    public void setText(String text, int id) {
        TextView textView = view.findViewById(id);
        textView.setText(text);
    }

    public void setOnclickListener(int id, View.OnClickListener onClickListener) {
        View v = view.findViewById(id);
        v.setOnClickListener(onClickListener);
    }
}
