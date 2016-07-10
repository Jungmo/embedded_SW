package com.hms.project1;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    Dialog dialog;
    Context context = this;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(mListener);
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(mListener);
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(mListener);
        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(mListener);

    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent;
            switch(v.getId()){
                case R.id.button1:
                    intent = new Intent(context, Settings.class);
                    startActivity(intent);
                    break;
                case R.id.button2:
                        intent = new Intent(context, SavedFile.class);
                        startActivity(intent);
                    break;
                case R.id.button3:
                    Settings settings = new Settings();
                    int camera = settings.getCamera();
                    if(camera == 1){
                        intent = new Intent(context, Streaming.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplication(), "camera is not settled", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.button4: //설정
                    intent = new Intent(context, Settings.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    public void custom_dialog(String message){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert);
        dialog.setTitle("값이 도착했습니다.");

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, context.getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 450, context.getResources().getDisplayMetrics());

        WindowManager.LayoutParams manager = new WindowManager.LayoutParams();
        manager.copyFrom(dialog.getWindow().getAttributes());
        manager.width = width;
        manager.height = height;
        dialog.getWindow().setAttributes(manager);

        TextView textView = (TextView)dialog.findViewById(R.id.textView);
        textView.setText(message);

        Button cancel = (Button)dialog.findViewById(R.id.button1);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
