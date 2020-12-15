package com.okex;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.okex.util.IdUtil;
import com.txxia.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PrivacyActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(IdUtil.getLayoutId(this, "activity_protocol_ex"));
        TextView textView = findViewById(IdUtil.getViewId(this, "protocol_ex"));
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.setText(Html.fromHtml(readAssets(this, "privacy.txt")));
    }

    private static String readAssets(Context context, String filename) {
        InputStream is;
        try {
            is = context.getAssets().open(filename);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
