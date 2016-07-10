package com.hms.project1;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016-06-05.
 */
public class Settings extends AppCompatActivity{
    private int accel=0;
    private int camera=0;
    private String dummy1, dummy2;
    private ProgressDialog progress;
    private String nCubeId = "0.2.481.1.0001.001.7591";
    public static Activity activity;
    CheckBox option1;
    CheckBox option2;
    Context context = this;

    public int getCamera() {
        return camera;
    }

    public void setCamera(int camera) {
        this.camera = camera;
    }

    public int getAccel() {
        return accel;
    }

    public void setAccel(int accel) {
        this.accel = accel;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        activity = Settings.this;

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                option1 = (CheckBox) findViewById(R.id.checkBox1);
                option2 = (CheckBox) findViewById(R.id.checkBox2);
                if (option1.isChecked()) {
                    setAccel(1);
                } else if (!option1.isChecked()) {
                    setAccel(0);
                }
                if (option2.isChecked())
                {
                    setCamera(1);
                }
                if (!option2.isChecked())
                {
                    setCamera(0);
                }
                if (option1.isChecked()) {
                    setAccel(1);
                    new PostClass(Settings.this, "axis", "on").execute();
                }
                else if (!option1.isChecked()) {
                    setAccel(0);
                    new PostClass(Settings.this, "axis", "off").execute();
                }
                if (option2.isChecked())
                {
                    setCamera(1);
                    new PostClass(Settings.this, "motion", "on").execute();
                }
                else if (!option2.isChecked())
                {
                    setCamera(0);
                    new PostClass(Settings.this, "motion", "off").execute();
                }

            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;
        private String thingName;
        private String action;

        public PostClass(Context c, String thingName, String action) {
            this.context = c;
            this.thingName = thingName;
            this.action = action;
        }

        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String u  = String.format("http://es.pagez.kr:9000/Mobius/remoteCSE-%s/mgmtCmd-%scontrol", nCubeId, thingName);
                URL url = new URL(u);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Accept", "application/xml");
                connection.setRequestProperty("Content-Type", "ty=8; application/xml");
                connection.setRequestProperty("locale", "en");
                connection.setRequestProperty("X-M2M-Origin", "Origin");
                connection.setRequestProperty("X-M2M-RI", "12345");

                String urlParameters = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<m2m:mgc\n" +
                        "    xmlns:m2m=\"http://www.onem2m.org/xml/protocols\"\n" +
                        "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                        "    <exra>%s,%s</exra>\n" +
                        "</m2m:mgc>", thingName, action);

                connection.setRequestMethod("POST");


                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                //System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator")
                        + System.getProperty("line.separator") + responseOutput.toString());

                Settings.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progress.dismiss();
                    }
                });

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute() {
            progress.dismiss();
        }
    }
}