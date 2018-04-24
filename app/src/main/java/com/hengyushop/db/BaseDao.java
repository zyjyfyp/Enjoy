package com.hengyushop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDao extends SQLiteOpenHelper {

	String sql = "create table status (_id integer primary key,"
			+ "productTypeName text not null,parentId integer not null,"
			+ "layer integer,openUrl text,SpecCommend integer)";

	String sql2 = "CREATE TABLE users (_uid INTEGER primary key autoincrement, username TEXT NOT NULL, actualname TEXT, "
			+ "password TEXT, payPassword TEXT, gender INTEGER, regip TEXT, joindata TEXT, "
			+ "lastip TEXT, lastvisit TEXT, lastpost TEXT, lastposttitle TEXT, credits TEXT,"
			+ " email TEXT, bday TEXT, showemail INTEGER, invisible INTEGER, newpm INTEGER, "
			+ "newpmcount INTEGER, onlinestate INTEGER, groupid INTEGER, avatarimageId TEXT, "
			+ "qq TEXT, site TEXT, address TEXT, postCode TEXT, phone TEXT, HideCode TEXT,"
			+ " getPwdLastTime TEXT, medals TEXT, openId TEXT, accessToken TEXT, inentityCard TEXT,"
			+ " PassTicket REAL, freezePassTicket REAL, shopPassTicket TEXT,PassTicketBalance TEXT, addressProvinceCode TEXT, "
			+ "addressCityCode TEXT, addressAreaCode TEXT, consigneeAddressId INTEGER, HengYuCode TEXT,"
			+ " commendHengYuCode TEXT, isOldUser INTEGER, isEnable INTEGER, acceptTime TEXT,"
			+ "activeTime TEXT, agentHengYuCode TEXT, businessNum INTEGER, businessTotalNum INTEGER, "
			+ "regValidatacode TEXT, renwuOldSys TEXT, phoneMSISDM TEXT, phoneIMEI TEXT, phoneModel TEXT,"
			+ " phoneIMSI TEXT, BossUid INTEGER, departmentId INTEGER, zhiWu TEXT, Telephone TEXT, "
			+ "Fax TEXT, CompanyName TEXT,isLogin INTEGER,userkey TEXT,userrnd TEXT)";

	String sql3 = "CREATE TABLE wares (_id INTEGER primary key autoincrement, wareid INTEGER,imgurl TEXT, warename TEXT, retailprice TEXT,"
			+ " marketprice TEXT, stylenameone TEXT, stylenatureone TEXT, stylenametwo TEXT,"
			+ "stylenaturetwo TEXT,number INTEGER,ischecked INTEGER,orderid INTEGER,jf INTEGER)";

	String sql4 = "create table foodclassify (_ProductCategoryID integer primary key,"
			+ "ProductTypeName text not null)";

	String sql5 = "CREATE TABLE foodcontent (_ProID INTEGER NOT NULL, "
			+ "ProductCategoryID INTEGER NOT NULL, ProName TEXT NOT NULL, "
			+ "proFaceImg TEXT NOT NULL, proFaceBigImg TEXT NOT NULL, "
			+ "originalityPrice TEXT NOT NULL, retailPrice TEXT NOT NULL, proComputerInfo TEXT);";
	String sql6 = "create table wide (_id integer primary key,"
			+ "productTypeName text not null,parentId integer not null,"
			+ "layer integer,openUrl text,SpecCommend integer)";

	// String sql4 =
	// "create table parameters(_wareid interger,specParameterName text,ParameterValue text)";

	public BaseDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(sql);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		db.execSQL(sql5);
		db.execSQL(sql6);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("drop table if exists wide");
		db.execSQL("drop table if exists status");
		db.execSQL("drop table if exists users");
		db.execSQL("drop table if exists wares");
		db.execSQL("drop table if exists foodclassify");
		db.execSQL("drop table if exists foodcontent");
		// db.execSQL("drop table if exists parameters");
		onCreate(db);
	}

}
