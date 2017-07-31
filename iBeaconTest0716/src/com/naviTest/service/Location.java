package com.naviTest.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.naviTest.place.IBeacon;
import com.naviTest.util.AnalyseData;
import com.naviTest.util.InitMongoDB;

@WebServlet("/locate")
public class Location extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MongoCollection<Document> collection;

	@Override
	public void init() throws ServletException {
		collection = InitMongoDB.getDBcollection();
		System.out.println("服务初始化完成！");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String recordData = req.getParameter("recordData");
		System.out.println(recordData);
		List<IBeacon> IBeaconList = new ArrayList<>();

		try {
			/*
			 * 根据收到ibeacon数组得到该点ibeacon的MAC地址并作为检索条件
			 */
			IBeaconList = AnalyseData.resolvedata(recordData);
			// FilterData.filterdata(place);
			BasicDBObject conds = new BasicDBObject();
			ArrayList<BasicDBObject> cond = new ArrayList<BasicDBObject>();
			for (IBeacon iBeacon : IBeaconList) {
				if (iBeacon.getRssi() > -100) {
					cond.add(new BasicDBObject("ibeacons.bluetoothAddress", iBeacon.getBluetoothAddress()));
				}
			}
			conds.append(QueryOperators.AND, cond);
			System.out.println("检索条件" + cond);
			/*
			 * 根据检索条件向集合中find MAC地址相匹配的点
			 */
			FindIterable<Document> placeDocuments = collection.find(conds);
			MongoCursor<Document> cursor = placeDocuments.iterator();
			int counter = 0;
			ArrayList<Double> RssiAverge = new ArrayList<>();
			double StandardDeviation = 0.0;
			while (cursor.hasNext()) {
				// 将结果转化为Document
				Document cur = cursor.next();
				@SuppressWarnings("unchecked")
				List<Document> ibeaconDocument = (List<Document>) cur.get("ibeacons");
				System.out.println("ibeacon的数目" + ibeaconDocument.size());
				for (IBeacon iBeacon : IBeaconList) {
					for (Document ibeaconDoc : ibeaconDocument) {
						// 根据bluetoothAddress进行匹配
						if (iBeacon.getBluetoothAddress().equals((String) ibeaconDoc.get("bluetoothAddress"))) {
							StandardDeviation += Math.pow(iBeacon.getRssi() - (Integer) ibeaconDoc.get("rssi"), 2);
						}
					}
				}
				RssiAverge.add(StandardDeviation);
				StandardDeviation = 0;
				counter++;
			}
			if (!RssiAverge.isEmpty()) {
				for (Double Standard : RssiAverge) {
					System.out.println(Standard);
				}

			}
			System.out.println("检索到的匹配点数目：" + counter);
			/*
			 * 查找出最相似匹配点的下标
			 */
			int minindex = 0;
			double min = RssiAverge.get(0);
			for (int k = 0; k < counter; k++) {
				if (RssiAverge.get(k) < min) {
					min = RssiAverge.get(k);
					minindex = k;
				}
			}
			System.out.println("在被检索中的匹配点中最相似点下标为" + minindex);
			/*
			 * 根据下标检索出最相似匹配点
			 */
			MongoCursor<Document> Mincursor = placeDocuments.iterator();
			int i = 0;
			Document result = new Document();
			while (Mincursor.hasNext()) {
				Document Mincur = Mincursor.next();
				if (i == minindex) {
					// 遍历得到最匹配点赋值给result
					result = Mincur;
				}
				i++;
			}
			resp.setCharacterEncoding("UTF-8");

			/*
			 * 将app端所需数据进行整理
			 */
			String resultstring = "floor:" + result.getInteger("floor") + "/X:" + result.getDouble("coordinateX")
					+ "/Y:" + result.getDouble("coordinateY");
			System.out.println(resultstring);
			DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
			dos.writeUTF(resultstring);
			// resp.addHeader("坐标", resultstring);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			resp.addHeader("发生错误", "111");
		}
	}

}
