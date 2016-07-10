package com.keti.tas.soft;

public class ThingTasConnector extends TasAbstractServer {

	private boolean isActive = false;

	private MotionControl motion1;
	private AxisControl axis1;

	public ThingTasConnector() {
		motion1 = new MotionControl();
		axis1 = new AxisControl();
	}

	@Override
	public void start() {
		isActive = true;
		System.out.println(KoreaTimeZone.getDisplayTimeNow() + " => Info: A TAS server for Thing is running now!");
		new Thread(new Runnable() {
			@Override
			public void run() {

				while (isActive) {
					String strData = motion1.getMotionState();
					activeReceiveEvent(strData);
					motion1.setCallState(false);

					strData = axis1.getAxisState();
					activeReceiveEvent(strData);
					axis1.setCallState(false);

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	@Override
	public void stop() {
		isActive = false;
	}

	public void sendToThing(String thingName, String action) {
		switch (thingName) {
		case "motion":
			motion1.setMotionState(thingName, action);
			break;
		case "axis":
			axis1.setAxisState(thingName, action);
			break;
		}

	}

}
