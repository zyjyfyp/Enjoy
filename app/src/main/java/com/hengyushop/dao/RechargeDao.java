package com.hengyushop.dao;

import java.util.ArrayList;

import com.hengyushop.db.DBRecharge;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RechargeDao {

//	private Context context;
//	private SQLiteDatabase db;
//
//	public RechargeDao(Context context) {
//
//		this.context = context;
//		System.out.println(DBRecharge.DB_PATH + "/" + DBRecharge.DB_NAME);
//		db = SQLiteDatabase.openOrCreateDatabase(DBRecharge.DB_PATH + "/"
//				+ DBRecharge.DB_NAME, null);
//	}
//
//	public ArrayList<RechargeQQData> findAllType() {
//		ArrayList<RechargeQQData> list = new ArrayList<RechargeQQData>();
//		String sql = "select type from qq ";
//		Cursor cur = db.rawQuery(sql, null);
//		while (cur.moveToNext()) {
//			RechargeQQData data = new RechargeQQData();
//			String type = cur.getString(cur.getColumnIndex("type"));
//			data.setType(type);
//			list.add(data);
//		}
//		cur.close();
//		return list;
//	}
//
//	public RechargeQQData findCodeByType(String type) {
//		RechargeQQData data = new RechargeQQData();
//		String sql = "select code from qq where type='" + type + "'";
//		Cursor cur = db.rawQuery(sql, null);
//		while (cur.moveToNext()) {
//			int code = cur.getInt(cur.getColumnIndex("code"));
//			data.setCode(code);
//		}
//		return data;
//	}

}
