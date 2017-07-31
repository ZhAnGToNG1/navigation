package com.naviTest.util;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.naviTest.place.IBeacon;
import com.naviTest.place.Place;

public class PlaceInsertDAO {

	public boolean insetPlace(MongoCollection<Document> collection, Place place) throws Exception {

		Document placeDocument = new Document("buildingNumber", place.getBuildingNumber())
				.append("floor", Integer.parseInt(place.getFloor()))
				.append("coordinateX", Double.parseDouble(place.getCoordinateX()))
				.append("coordinateY", Double.parseDouble(place.getCoordinateY())).append("date", place.getDate());
		ArrayList<Document> ibecons = new ArrayList<>();

		for (IBeacon iBeacon : place.getmIBeaconList()) {
			Document ibecon = new Document("name", iBeacon.getName()).append("major", iBeacon.getMajor())
					.append("minor", iBeacon.getMajor()).append("proximityUuid", iBeacon.getProximityUuid())
					.append("bluetoothAddress", iBeacon.getBluetoothAddress()).append("txPower", iBeacon.getTxPower())
					.append("rssi", iBeacon.getRssi());
			ibecons.add(ibecon);
		}
		placeDocument.append("ibeacons", ibecons);

		collection.insertOne(placeDocument);
		System.out.println("文档插入成功");

		return true;
	}

}
