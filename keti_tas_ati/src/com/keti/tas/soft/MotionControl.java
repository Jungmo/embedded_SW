package com.keti.tas.soft;

public class MotionControl {

	private final String ThingName;
	private final String HOST;
	private final int PORT;
	private boolean callState = false;

	public MotionControl() {
		ThingName = "MotionControl";
		HOST = "127.0.0.1";
		PORT = 33332;
	}

	public String getMotionState() {
		String url = String.format("http://%s:%d/", HOST, PORT);
		String res = "";
		try {
			res = HTTPClient.httpGet(url);
		} catch (Exception e) {

		}
		return String.format("motionstate,motionstatus,%s", res);
	}

	public void setMotionState(String thingName, String action) {

		switch (thingName) {
		case "motion":
			switch (action) {
			case "on":
				//executeCommand("service motion start");
				_request("turnon");
				try {
					HTTPClient.httpPost("http://127.0.0.1:33330", new String[] {"line2"}, new String[]{"MOTION: ON"});
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "off":
				//executeCommand("service motion stop");
				_request("turnoff");
				try {
					HTTPClient.httpPost("http://127.0.0.1:33330", new String[] {"line2"}, new String[]{"MOTION: OFF"});
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.printf("[%s] Commaand Error: illeal cmd=\n", ThingName);
			}

			break;
		default:
			System.out.printf("[%s] Command Error: target not found =\n", ThingName);
		}

		callState = true;
	}

	private void _request(String cmd) {
		String url = String.format("http://%s:%d/", HOST, PORT);
		String res = "";
		String paramName[] = new String[] { "cmd" };
		String paramVal[] = new String[] { cmd };
		try {
			res = HTTPClient.httpPost(url, paramName, paramVal);
		} catch (Exception e) {

		}

		if (res.equals("OK")) {
			System.out.printf("[%s] Success : motion %s\n", ThingName, cmd);
		} else {
			System.out.printf("[%s] Unknown Error...\n", ThingName);
		}
	}

	public boolean getCallState() {
		return callState;
	}

	public void setCallState(boolean state) {
		this.callState = state;
	}
}