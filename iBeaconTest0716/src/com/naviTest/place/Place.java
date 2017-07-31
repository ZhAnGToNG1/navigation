package com.naviTest.place;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Place {
	private String buildingNumber;
	private String floor;
	private String coordinateX;
	private String coordinateY;
	private Date date;
	private List<IBeacon> mIBeaconList;

	public Place() {
	}

	public Place(String str) {
		// 解析字符串数据
		StringBuffer strbuf = new StringBuffer(str);

		this.buildingNumber = strbuf.substring(0, strbuf.indexOf("*"));
		strbuf.delete(0, strbuf.indexOf("*") + 1);
		this.floor = strbuf.substring(0, strbuf.indexOf("*"));
		strbuf.delete(0, strbuf.indexOf("*") + 1);
		this.coordinateX = strbuf.substring(0, strbuf.indexOf("*"));
		strbuf.delete(0, strbuf.indexOf("*") + 1);
		this.coordinateY = strbuf.substring(0, strbuf.indexOf("*"));
		strbuf.delete(0, strbuf.indexOf("*") + 1);
		this.date = new Date(Long.parseLong(strbuf.substring(0, strbuf.indexOf("*"))));
		strbuf.delete(0, strbuf.indexOf("*") + 1);

		// System.out.println(this.buildingNumber);
		// System.out.println(this.floor);
		// System.out.println(this.coordinateX);
		// System.out.println(this.coordinateY);
		// System.out.println("///////////////////////////////////////////////");
		// System.out.println(strbuf);

		mIBeaconList = new ArrayList<>();
		while (strbuf.length() > 0) {
			// System.out.println(strbuf.substring(0, strbuf.indexOf("*")));
			mIBeaconList.add(new IBeacon(strbuf.substring(0, strbuf.indexOf("*"))));
			strbuf.delete(0, strbuf.indexOf("*") + 1);
		}
		System.out.println("解析完成");
	}

	@Override
	public String toString() {
		String placeStr;
		placeStr = buildingNumber + "*";
		placeStr += floor + "*";
		placeStr += coordinateX + "*";
		placeStr += coordinateY + "*";
		placeStr += date.getTime() + "*";
		if (!mIBeaconList.isEmpty()) {
			for (IBeacon ibeacon : mIBeaconList)
				placeStr += ibeacon.toString() + "*";
		}
		return placeStr;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(String coordinateX) {
		this.coordinateX = coordinateX;
	}

	public String getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(String coordinateY) {
		this.coordinateY = coordinateY;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<IBeacon> getmIBeaconList() {
		return mIBeaconList;
	}

	public void setmIBeaconList(List<IBeacon> mIBeaconList) {
		this.mIBeaconList = mIBeaconList;
	}

}
//
