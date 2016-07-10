package com.keti.tas.soft;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HTTPClient {
	public static String httpGet(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();

		conn.disconnect();
		return sb.toString();
	}

	public static String httpPost(String urlStr, String[] paramName, String[] paramVal) throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setAllowUserInteraction(false);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		OutputStream out = conn.getOutputStream();
		Writer writer = new OutputStreamWriter(out, "UTF-8");
		for (int i = 0; i < paramName.length; i++) {
			writer.write(paramName[i]);
			writer.write("=");
			writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
			writer.write("&");
		}
		writer.close();
		out.close();

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();

		conn.disconnect();
		return sb.toString();
	}
	/*
	public static String httpPost(String urlStr, String rawParams) throws Exception {
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

        //final StringBuilder output = new StringBuilder("Request URL " + url);
        //output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
        //output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
        //output.append(System.getProperty("line.separator") + "Type " + "POST");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";
        StringBuilder responseOutput = new StringBuilder();
        //System.out.println("output===============" + br);
        while ((line = br.readLine()) != null) {
            responseOutput.append(line);
        }
        br.close();

        //output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator")
        //        + System.getProperty("line.separator") + responseOutput.toString());

        //MainActivity.this.runOnUiThread(new Runnable() {
        //    @Override
        //    public void run() {
        //        outputView.setText(output);
        //        progress.dismiss();
        //    }
        //});
        
        return responseOutput.toString();
	}

	public 
	*/
	

}
