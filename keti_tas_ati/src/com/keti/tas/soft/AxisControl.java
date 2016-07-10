package com.keti.tas.soft;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pi4j.io.gpio.*;

public class AxisControl {
	
	private GpioPinDigitalOutput beep;
	
	private final String ThingName;
	private final String HOST;
	private final int PORT;
	private boolean callState = false;
	
	private boolean isActive;

	private double[] init_acc;
	/*
	 * 0: x 1: y 2: z
	 */

	public AxisControl() {
		ThingName = "AxisControl";
		HOST = "127.0.0.1";
		PORT = 33331;

		init_acc = new double[3];
		
		isActive = false;
		
		GpioController gpio = GpioFactory.getInstance();
		beep = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
	}

	public String getAxisState() {
		String url = String.format("http://%s:%d/", HOST, PORT);
		String res = "";
		try {
			res = HTTPClient.httpGet(url);
		} catch (Exception e) {

		}
		return String.format("axisstate,axisstatus,%s", res);
	}

	public void setAxisState(String thingName, String action) {
		System.out.println("thingName:" + thingName + ", action:" + action);
		switch (thingName) {
		case "axis":
			switch (action) {
			case "on":
				isActive = true;
				try {
					init_acc = getAxisValues();
					createCheckingThread();
				} catch (Exception e) {
					System.out.println("ISTHEREANYPROBLEM?");;
				}
				
				try {
					HTTPClient.httpPost("http://127.0.0.1:33330", new String[] {"line1"}, new String[]{"6AXIS: ON"});
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
			case "off":
				isActive = false;
				beep.setState(false);
				try {
					HTTPClient.httpPost("http://127.0.0.1:33330", new String[] {"line1"}, new String[]{"6AXIS: OFF"});
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.printf("[%s] Command Error: illeal cmd=\n", ThingName);
			}

			break;
		case "beep":
			switch (action) {
			case "on":
				beep.setState(true);
				
				break;
			case "off":
				beep.setState(false);
			}

			break;
		default:
			System.out.printf("[%s] Command Error: target not found =\n", ThingName);
		}

		callState = true;
	}
	
	private double[] getAxisValues() throws Exception {
		String data = getAxisState();
		
		double[] ret = new double[3];
		
		Pattern p = Pattern.compile("[-]{0,1}\\d+\\.\\d+");
		Matcher m = p.matcher(data);

		double[] aa = new double[6];
		for (int i = 0; i < 6; ++i) {
			m.find();
			double cccc = Double.parseDouble(m.group(0));
			aa[i] = cccc;
		}

		ret[0] = aa[4];
		ret[1] = aa[3];
		ret[2] = aa[5];

		return ret;
	}
	
	private void createCheckingThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("[!!]Axis Checking thread started!");
				while (isActive) {
					double[] currunt_acc;
					try {
						currunt_acc = getAxisValues();
					} catch (Exception e) {
						System.out.println("Something in axis values...");
						return;
					}
					if ((Math.abs(init_acc[0] - currunt_acc[0]) > 3) ||
						(Math.abs(init_acc[1] - currunt_acc[1]) > 3) ||
						(Math.abs(init_acc[2] - currunt_acc[2]) > 3)) {
						beep.setState(true);
						try {
							System.out.println(CmdUtil.saved);
							CmdUtil.execute(CmdUtil.saved);
						} catch (IOException | InterruptedException e1) {
							e1.printStackTrace();
						}
					} else {
						beep.setState(false);
					}
					
					try {
						Thread.sleep(780);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("[!!]Axis Checking thread Done...!");
			}
		}).start();
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
			System.out.printf("[%s] Success : axis %s\n", ThingName, cmd);
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
