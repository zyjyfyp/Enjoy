package com.hengyushop.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yunsen.enjoy.model.CityData;
import com.yunsen.enjoy.model.HotelCity;

import java.util.ArrayList;

public class CityDao {

	private Context context;
	private SQLiteDatabase db;

	public CityDao(Context context) {

		this.context = context;
		db = context
				.openOrCreateDatabase("city.db", Context.MODE_PRIVATE, null);
	}

	/**
	 * 携程酒店
	 *
	 * @return
	 */
	public ArrayList<HotelCity> getHotelCitys() {

		ArrayList<HotelCity> lists = new ArrayList<HotelCity>();
		String sql = "select city,cityName from hotel_city_group order by cityTag asc";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			HotelCity city = new HotelCity();
			city.setId(cur.getString(0));
			city.setName(cur.getString(1));
			lists.add(city);
		}
		cur.close();
		db.close();
		return lists;

	}

	// 查询 所有 的省份
	public ArrayList<CityData> findSheng() {
		ArrayList<CityData> list = new ArrayList<CityData>();
		String sql = "select name from province";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			CityData data = new CityData();
			String name = cur.getString(cur.getColumnIndex("name"));
			data.setName(name);
			list.add(data);
		}
		cur.close();
		db.close();
		return list;
	}

	public String pCode(String name) {
		String sql = "select code from province where name= '" + name + "'";
		String code = null;
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			code = cur.getString(cur.getColumnIndex("code"));

		}
		cur.close();
		db.close();
		return code;
	}

	public String cCode(String name) {
		String sql = "select code from city where name= '" + name + "'";
		String code = null;
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			code = cur.getString(cur.getColumnIndex("code"));

		}
		cur.close();
		db.close();
		return code;
	}

	public String dCode(String name) {
		String sql = "select code from area where name= '" + name + "'";
		String code = null;
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			code = cur.getString(cur.getColumnIndex("code"));

		}
		cur.close();
		db.close();
		return code;
	}

	// 查询 选中 省份 的 code
	public CityData findShengCode(String name) {
		CityData data = new CityData();
		String sql = "select code from province where name= '" + name + "'";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			int code = cur.getInt(cur.getColumnIndex("code"));
			data.setCode(code);
		}
		cur.close();
		db.close();
		return data;

	}

	// 查询 选中 省份 下的 城市
	public ArrayList<CityData> findCity(int sheng_id) {
		ArrayList<CityData> list = new ArrayList<CityData>();
		String sql = "select name from city where provinceId = " + sheng_id;
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			CityData data = new CityData();
			String name2 = cur.getString(cur.getColumnIndex("name"));
			data.setName(name2);
			list.add(data);
		}
		cur.close();
		db.close();
		return list;
	}

	// 查询选中城市的 code
	public CityData findCityCode(String name) {
		CityData data = new CityData();
		String sql = "select code from city where name= '" + name + "'";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			int code = cur.getInt(cur.getColumnIndex("code"));
			data.setCode(code);
		}
		cur.close();
		db.close();
		return data;
	}

	// 查询 选中 城市的 地区
	public ArrayList<CityData> findArea(int city_id) {
		ArrayList<CityData> list = new ArrayList<CityData>();
		String sql = "select name from area where cityId = " + city_id;
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			CityData data = new CityData();
			String name3 = cur.getString(cur.getColumnIndex("name"));
			data.setName(name3);
			list.add(data);
		}
		cur.close();
		db.close();
		return list;
	}

	// 查询选中地区的 code
	public CityData findAreaCode(String name) {
		CityData data = new CityData();
		String sql = "select code from area where name= '" + name + "'";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			int code = cur.getInt(cur.getColumnIndex("code"));
			data.setCode(code);
		}
		cur.close();
		db.close();
		return data;
	}

	public ArrayList<CityData> findAllArea(int citycode) {
		ArrayList<CityData> list = new ArrayList<CityData>();
		String sql = "select name,code from area where cityId = " + citycode;
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			CityData data = new CityData();
			String name3 = cur.getString(cur.getColumnIndex("name"));
			int code = cur.getInt(cur.getColumnIndex("code"));
			data.setName(name3);
			data.setCode(code);
			list.add(data);
		}
		cur.close();
		db.close();
		return list;
	}

}
