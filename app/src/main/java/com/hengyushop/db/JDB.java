package com.hengyushop.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class JDB {
	private SQLiteDatabase db;
	public JDB(Context context) {

		db = context.openOrCreateDatabase("jdpiao.db", Context.MODE_PRIVATE, null);
	}
	public ArrayList<String> getProvinceW(String sql,boolean flag) {
		ArrayList<String> items = new ArrayList<String>();
		Cursor cursor = db.rawQuery(sql, null);
		if (flag) {
			items.add("不限");
		}
		while (cursor.moveToNext()) {
			items.add(cursor.getString(0));

		}
		cursor.close();
		db.close();
		return items;

	}
	public ArrayList<String> getProvince(String sql) {
		ArrayList<String> items = new ArrayList<String>();
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			items.add(cursor.getString(0));

		}
		cursor.close();
		db.close();
		return items;

	}
	public String getName(String sql) {
		Cursor cursor = db.rawQuery(sql, null);
		String re = "";
		while (cursor.moveToNext()) {
			re = cursor.getString(0);

		}
		cursor.close();
		db.close();
		return re;
	}
}
