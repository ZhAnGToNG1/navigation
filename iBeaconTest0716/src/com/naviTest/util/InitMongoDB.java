package com.naviTest.util;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/*
 * 获得数据库连接，并且连接集合
 */
public class InitMongoDB {
	public static MongoCollection<Document> getDBcollection() {
		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			MongoDatabase mongoDatabase = mongoClient.getDatabase("mycol");
			System.out.println("数据库连接成功，并且成功取出集合");
			return mongoDatabase.getCollection("IbeaconTest");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}
}
