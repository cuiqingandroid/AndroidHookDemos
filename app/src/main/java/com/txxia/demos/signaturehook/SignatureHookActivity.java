package com.txxia.demos.signaturehook;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.txxia.demos.R;

public class SignatureHookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_hook);
        TextView tvSignature = findViewById(R.id.tvSignature);
        TextView tvStatus = findViewById(R.id.tvHookStatus);

        tvSignature.setText(getSignature(this));
        String signature = getSignature(this);
        if (PmsHookWrapper.ORIGIN_SIGN.equals(signature)) {
            tvStatus.setText("Hook签名成功:");
            tvStatus.setTextColor(Color.GREEN);
        } else {
            tvStatus.setText("Hook签名未成功:");
            tvStatus.setTextColor(Color.RED);
        }
    }


    public static String getSignature(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature signature = info.signatures[0];
            return signature.toCharsString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
