package com.keti.tas.soft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdUtil {
	public final static String saved = "/nCube/GCM.sh";
	
	public static String execute(String cmd) throws IOException, InterruptedException {
		StringBuffer sb = new StringBuffer();
		
		Process p = Runtime.getRuntime().exec(cmd);
		p.waitFor();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		
		return sb.toString();
	}
}
