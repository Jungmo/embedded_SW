package com.keti.tas.soft.edu;

import com.keti.tas.soft.AdaptorInfomation;
import com.keti.tas.soft.CubeTasClient;
import com.keti.tas.soft.CubeTasServer;
import com.keti.tas.soft.KoreaTimeZone;
import com.keti.tas.soft.MsgReceiveEvent;
import com.keti.tas.soft.MsgReceiveListener;
import com.keti.tas.soft.ThingData;
import com.keti.tas.soft.ThingInformation;
import com.keti.tas.soft.ThingTasConnector;
import com.keti.tas.soft.ThingsManagement;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataAdapter {

	public static void main(String argString[]) throws IOException, InterruptedException {

		final CubeTasServer cubeServer = new CubeTasServer();
		final ThingTasConnector thingServer = new ThingTasConnector();

		cubeServer.start();
		thingServer.start();

		AdaptorInfomation adaptor = new AdaptorInfomation(cubeServer.getServerPort());
		ThingsManagement manager = new ThingsManagement(adaptor);

		/*
		 * ledstate, motorstate : container
		 * ledcontrol, motorcontrol : control
		 */
		
		/*
		manager.addThing("ledstate", "sensoractor", "This is a iLab control board!");
		manager.addControlThing("ledcontrol", "sensoractor", "This is a iLab control board!",
				cubeServer.getServerPort());

		manager.addThing("motorstate", "sensoractor", "This is a control board!");
		manager.addControlThing("motorcontrol", "sensoractor", "This is a control board!",
				cubeServer.getServerPort());
		*/
		
		manager.addThing("motionstate", "motion", "");
		manager.addControlThing("motioncontrol", "motion", "", cubeServer.getServerPort());
		
		manager.addThing("axisstate", "axis", "");
		manager.addControlThing("axiscontrol", "axis", "", cubeServer.getServerPort());

		thingServer.addReceiveListener(new MsgReceiveListener() {
			// Thing -> TAS -> nCube
			@Override
			public void receiveMsgEvent(MsgReceiveEvent event) {
				String strData = event.getMessage();

				System.out.println(strData);

				if (strData.length() >= 21) {
					String[] uploadData = new String[3];//strData.split(",");
					StringTokenizer t = new StringTokenizer(strData);
					
					uploadData[0] = t.nextToken(",");
					uploadData[1] = t.nextToken(",");
					try {
						uploadData[2] = t.nextToken("").substring(1);
					} catch (Exception e) {
						uploadData[2] = "";
					}
					
					
					System.out.println(KoreaTimeZone.getDisplayTimeNow() + "=>");
					System.out.println("containerName: " + uploadData[0]);
					System.out.println("typeOfContent: " + uploadData[1]);
					System.out.println("content: " + uploadData[2]);
					System.out.println("============================");

					ThingData mData = new ThingData();

					try {
						ThingInformation thing = ThingsManagement.getThingIDByName(uploadData[0]);
						CubeTasClient cubeTasClient = new CubeTasClient();

						if (thing != null && thing.getRegistStatus()) {
							cubeTasClient
									.upload(mData.makeThingDataMsg(thing.getThingName(), uploadData[1], uploadData[2]));
						}

					} catch (IOException ex) {
						Logger.getLogger(DataAdapter.class.getName()).log(Level.SEVERE, null, ex);
					}

				}
			}
		});

		cubeServer.addReceiveListener(new MsgReceiveListener() {
			// nCube -> TAS -> Thing
			@Override
			public void receiveMsgEvent(MsgReceiveEvent event) {
				// Generate control meessage and send it to Thing
				System.out.println(KoreaTimeZone.getDisplayTimeNow() + " => Receive a message {" + event.getMessage()
						+ "} from &CUBE");

				if (event.getMessage().length() > 0) {
					String value[] = event.getMessage().split(",");

					if (value.length == 3) {
						String thingName = value[1];// "led"
						String action = value[2]; // "on / off"

						thingServer.sendToThing(thingName, action);
						System.out.println(KoreaTimeZone.getDisplayTimeNow() + " => Send a command {" + thingName + ","
								+ action + "} to Thing");
					}
				}
			}
		});
		
	}
}
