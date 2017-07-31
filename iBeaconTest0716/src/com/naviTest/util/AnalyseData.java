package com.naviTest.util;

import java.util.ArrayList;
import java.util.List;

import com.naviTest.place.IBeacon;

public class AnalyseData {

	public static List<IBeacon> resolvedata(String str) {
		List<IBeacon> mIBeaconList = new ArrayList<>();
		StringBuffer strbuf = new StringBuffer(str);
		while (strbuf.length() > 0) {
			System.out.println(strbuf.substring(0, strbuf.indexOf("*")));
			mIBeaconList.add(new IBeacon(strbuf.substring(0, strbuf.indexOf("*"))));
			strbuf.delete(0, strbuf.indexOf("*") + 1);
		}
		System.out.println("解析完成" + mIBeaconList);
		return mIBeaconList;

	}

}
