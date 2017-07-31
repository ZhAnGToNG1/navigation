package com.naviTest.util;

import com.naviTest.place.Place;

public class FilterData {
	public static Place filterdata(Place temp) {
		for (int i = 0; i < temp.getmIBeaconList().size(); i++) {
			// 判断每个Ibeacon的rssi值是否小于 x？
			if (temp.getmIBeaconList().get(i).getRssi() < -90) {
				temp.getmIBeaconList().remove(i);
				i--;
			}
		}
		return temp;
	}

}
