package com.example.streetworkout.Login;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.streetworkout.R;

public class CustomProgressDialog extends Dialog {
    @Override
    public void show() {
        setContentView(R.layout.login_custom_progressdialog);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        super.show();
    }

    public CustomProgressDialog(@NonNull Context context) {
        super(context);

    }
}
