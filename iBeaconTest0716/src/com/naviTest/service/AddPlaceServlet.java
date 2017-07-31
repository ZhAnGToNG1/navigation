package com.naviTest.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.naviTest.place.Place;
import com.naviTest.util.FilterData;
import com.naviTest.util.InitMongoDB;
import com.naviTest.util.PlaceInsertDAO;

@WebServlet("/post/addPlace")
public class AddPlaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int dirofsum = 4; // 各方向测量次数总和
	MongoCollection<Document> collection;

	@Override
	public void init() throws ServletException {
		collection = InitMongoDB.getDBcollection();
	}

	ArrayList<Place> mangyplace = new ArrayList<Place>();

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String recordData = req.getParameter("recordData");

		/*
		 * 进行存储数据库之前的操作（建立在手机传送数据通过高斯滤波处理后的数据的基础）： 1、过滤信号过小的Ibeacon
		 * 2、每个方向各取n次，最后取平均值
		 */
		Place temp = new Place(recordData);
		FilterData.filterdata(temp);// 原过滤数据
		System.out.println(temp);
		mangyplace.add(temp);
		System.out.println("第" + mangyplace.size() + "次取坐标");
		if (mangyplace.size() == dirofsum) {
			int[] ave_sum = new int[temp.getmIBeaconList().size()];
			for (int i = 0; i < temp.getmIBeaconList().size(); i++) {
				String str = temp.getmIBeaconList().get(i).getBluetoothAddress();
				for (int j = 0; j < dirofsum; j++) {
					for (int k = 0; k < temp.getmIBeaconList().size(); k++) {
						if (mangyplace.get(j).getmIBeaconList().get(k).getBluetoothAddress().equals(str)) {
							ave_sum[i] += mangyplace.get(j).getmIBeaconList().get(k).getRssi();
						}
					}
				}
				ave_sum[i] = ave_sum[i] / dirofsum;
				temp.getmIBeaconList().get(i).setRssi(ave_sum[i]);
			}
			System.out.println("各个方向求平均之后的place:" + temp);
			mangyplace.clear(); // 数组清除，为下一个坐标点采集做准备

			/*
			 * 获取数据库连接，将处理好的temp对象插入collection集合中
			 */
			try {
				new PlaceInsertDAO().insetPlace(collection, temp);
				// 检索插入的信息
				BasicDBObject query = new BasicDBObject();
				query.put("coordinateX", temp.getCoordinateX());
				FindIterable findIterable = collection.find(query);
				// System.out.println(findIterable.toString());
				MongoCursor cursor = findIterable.iterator();
				while (cursor.hasNext()) {
					System.out.println("查询结果:" + cursor.next());
				}
				// 设置回应消息
				resp.setCharacterEncoding("UTF-8");
				PrintWriter out = resp.getWriter();
				out.print("操作完成");
				out.close();
			} catch (Exception e) {
				System.err.println("操作失败" + e.getClass().getName() + ": " + e.getMessage());
			}
		}

	}
}
