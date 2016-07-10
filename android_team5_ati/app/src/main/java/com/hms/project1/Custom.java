package com.hms.project1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-06-12.
 */
public class Custom extends AppCompatActivity {
    public static Activity activity;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert);

        activity = Settings.activity;

        Settings settings = (Settings) Settings.activity;
        settings.finish();

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 450, getResources().getDisplayMetrics());

        WindowManager.LayoutParams manager = new WindowManager.LayoutParams();
        manager.copyFrom(getWindow().getAttributes());
        manager.width = width;
        manager.height = height;
        getWindow().setAttributes(manager);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("dialog");

        Button cancel = (Button)findViewById(R.id.button1);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
