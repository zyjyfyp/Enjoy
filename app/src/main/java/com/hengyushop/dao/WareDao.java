package com.hengyushop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hengyushop.db.BaseDao;
import com.yunsen.enjoy.model.ShopCartData;
import com.yunsen.enjoy.model.UserRegisterData;
import com.yunsen.enjoy.model.WareData;

import java.util.ArrayList;
import java.util.List;

public class WareDao {

	BaseDao helper = null;

	public WareDao(Context context) {

		helper = new BaseDao(context, "productname.db", null, 30);
	}

	static int d = 0;

	// 将josn解析出来的信息加入表格status中
	public boolean insertWare(WareData ware) {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isInsert = false;
		ContentValues cv = new ContentValues();

		cv.put("_id", ware.getID());
		cv.put("productTypeName", ware.getProductTypeName());
		cv.put("parentId", ware.getParentId());
		cv.put("layer", ware.getLayer());
		cv.put("openUrl", ware.getOpenUrl());
		cv.put("SpecCommend", ware.getSpecCommend());
		long recordID = db.insert("status", "", cv);
		if (recordID > 0) {
			isInsert = true;
		}
		return isInsert;
	}

	// 将josn解析出来的信息加入表格status中
	public boolean insertWide(WareData ware) {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isInsert = false;
		ContentValues cv = new ContentValues();

		cv.put("_id", ware.getID());
		cv.put("productTypeName", ware.getProductTypeName());
		cv.put("parentId", ware.getParentId());
		cv.put("layer", ware.getLayer());
		cv.put("openUrl", ware.getOpenUrl());
		cv.put("SpecCommend", ware.getSpecCommend());
		long recordID = db.insert("wide", "", cv);
		if (recordID > 0) {
			isInsert = true;
		}
		return isInsert;
	}

	// 查询所有的商品ID
	public List<WareData> finWideID() {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<WareData> allIds = new ArrayList<WareData>();
		String sql = "select _id from wide";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			WareData ware = new WareData();
			int ID = cur.getInt(cur.getColumnIndex("_id"));
			ware.setID(ID);
			allIds.add(ware);
		}
		cur.close();
		db.close();
		return allIds;
	}

	// 查询所有的商品ID
	public List<WareData> finAddID() {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<WareData> allIds = new ArrayList<WareData>();
		String sql = "select _id from status";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			WareData ware = new WareData();
			int ID = cur.getInt(cur.getColumnIndex("_id"));
			ware.setID(ID);
			allIds.add(ware);
		}
		cur.close();
		db.close();
		return allIds;
	}

	// 根据parentId查找所对应的productTypeName，openUrl
	public ArrayList<WideDo> findAllWide() {
		SQLiteDatabase db = helper.getReadableDatabase();

		ArrayList<WideDo> allwares = new ArrayList<WideDo>();

		Cursor cur = db
				.rawQuery(
						"select _id,productTypeName from wide where parentId =3 order by layer",
						null);
		while (cur.moveToNext()) {
			WideDo deDo = new WideDo();
			String _id = cur.getString(0);
			String name = cur.getString(1);
			SQLiteDatabase db1 = helper.getReadableDatabase();
			// db1.ra
			String sql = "select _id,productTypeName from wide where parentId ="
					+ _id + " order by layer";
			Cursor c1 = db1.rawQuery(sql, null);
			ArrayList<WideMarketDo> list = new ArrayList<WideMarketDo>();
			while (c1.moveToNext()) {
				WideMarketDo marketDo = new WideMarketDo();
				marketDo.setId(c1.getString(0));
				marketDo.setName(c1.getString(1));
				list.add(marketDo);
			}
			c1.close();
			db1.close();
			deDo.setId(_id);
			deDo.setName(name);
			deDo.setList(list);
			allwares.add(deDo);
		}
		cur.close();
		db.close();
		return allwares;
	}

	// 根据parentId查找所对应的productTypeName，openUrl
	public List<WareData> findAllWare(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<WareData> allwares = new ArrayList<WareData>();
		String sql = "select _id,productTypeName,openUrl from status where parentId ="
				+ id + " order by layer";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			WareData ware = new WareData();
			int nameid = cur.getInt(cur.getColumnIndex("_id"));
			String typeName = cur.getString(cur
					.getColumnIndexOrThrow("productTypeName"));
			String url = cur.getString(cur.getColumnIndex("openUrl"));
			ware.setID(nameid);
			ware.setProductTypeName(typeName);
			ware.setOpenUrl(url);
			ware.setStrname(findnameBySpe(nameid));
			allwares.add(ware);
		}
		cur.close();
		db.close();
		return allwares;
	}

	public List<WareData> findAllWare1(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<WareData> allwares = new ArrayList<WareData>();
		String sql = "select _id,productTypeName,openUrl from wide where parentId ="
				+ id + " order by layer";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			WareData ware = new WareData();
			int nameid = cur.getInt(cur.getColumnIndex("_id"));
			String typeName = cur.getString(cur
					.getColumnIndexOrThrow("productTypeName"));
			String url = cur.getString(cur.getColumnIndex("openUrl"));
			ware.setID(nameid);
			ware.setProductTypeName(typeName);
			ware.setOpenUrl(url);
			ware.setStrname(findnameBySpe(nameid));
			allwares.add(ware);
		}
		cur.close();
		db.close();
		return allwares;
	}

	// 查询商品的推荐信息
	public String[] findnameBySpe(int parentId) {
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select productTypeName from status where SpecCommend=1 and parentId in "
				+ "(select _id from status where parentId=" + parentId + ")";
		Cursor cur = db.rawQuery(sql, null);
		String temp[] = new String[cur.getCount()];
		int index = 0;
		while (cur.moveToNext()) {
			temp[index++] = cur
					.getString(cur.getColumnIndex("productTypeName"));
		}

		cur.close();
		db.close();
		return temp;
	}

	// 根据productTypeName查询商品_id
	public WareData findbyTypeName(String typename) {
		SQLiteDatabase db = helper.getReadableDatabase();
		WareData ware = new WareData();
		String sql = "select _id from status where productTypeName='"
				+ typename + "'";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.moveToNext()) {
			int id = cur.getInt(cur.getColumnIndex("_id"));
			ware.setID(id);
		}
		cur.close();
		db.close();
		return ware;
	}

	public WareData findbyTypeName1(String typename) {
		SQLiteDatabase db = helper.getReadableDatabase();
		WareData ware = new WareData();
		String sql = "select _id from wide where productTypeName='" + typename
				+ "'";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.moveToNext()) {
			int id = cur.getInt(cur.getColumnIndex("_id"));
			ware.setID(id);
		}
		cur.close();
		db.close();
		return ware;
	}

	public List<WareData> findAllWareTwo(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<WareData> allwares = new ArrayList<WareData>();
		String sql = "select _id,productTypeName,openUrl from status where parentId ="
				+ id;

		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			WareData ware = new WareData();
			int nameid = cur.getInt(cur.getColumnIndex("_id"));
			String typeName = cur.getString(cur
					.getColumnIndexOrThrow("productTypeName"));
			String url = cur.getString(cur.getColumnIndex("openUrl"));
			ware.setID(nameid);
			ware.setProductTypeName(typeName);
			ware.setOpenUrl(url);
			ware.setStrname(findnameBySpeTwo(nameid));
			allwares.add(ware);
		}
		cur.close();
		db.close();
		return allwares;
	}

	public String[] findnameBySpeTwo(int parentId) {
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select productTypeName from status where SpecCommend=1 and parentId = "
				+ parentId;
		Cursor cur = db.rawQuery(sql, null);
		String temp[] = new String[cur.getCount()];
		int index = 0;
		while (cur.moveToNext()) {
			temp[index++] = cur
					.getString(cur.getColumnIndex("productTypeName"));
		}
		cur.close();
		db.close();
		return temp;
	}

	// 用户注册 插入注册信息
	public boolean insertUser(UserRegisterData data) {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isInsert = false;
		ContentValues cv = new ContentValues();
		cv.put("username", data.getUserName());
		cv.put("phone", data.getPhone());
		cv.put("email", data.getEmail());
		cv.put("password", data.getPassword());
		cv.put("payPassword", data.getPayPassword());
		cv.put("HengYuCode", data.getHengyuCode());
		cv.put("joindata", data.getJoinData());
		cv.put("isEnable", data.getIsEnable());
		cv.put("isLogin", data.getIsLogin());
		long recordID = db.insert("users", "", cv);
		if (recordID > 0) {
			isInsert = true;
		}
		return isInsert;
	}

	// 用户登录 修改用户状态
	public boolean updateIsLogin(String username, UserRegisterData data) {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isUpdate = false;
		ContentValues cv = new ContentValues();
		cv.put("isLogin", data.getIsLogin());
		// cv.put("userkey", data.getUserkey());
		// cv.put("userrnd", data.getUserrnd());
		// cv.put("phone", data.getPhone());
		// cv.put("actualname", data.getActualname());
		// cv.put("joindata", data.getJoinData());
		// cv.put("activeTime", data.getActiveTime());
		cv.put("PassTicketBalance", data.getPassTicketBalance());
		cv.put("shopPassTicket", data.getShopPassTicket());
		cv.put("credits", data.getCredits());
		int updateCount = db.update("users", cv, "HengYuCode=?",
				new String[] { username });
		if (updateCount > 0) {
			// 修改成功
			isUpdate = true;
		}
		return isUpdate;
	}

	// 用户登录时 检测该用户是否已经登录
	public UserRegisterData findLoginCheck(String username) {
		SQLiteDatabase db = helper.getReadableDatabase();
		UserRegisterData data = new UserRegisterData();
		String sql = "select isLogin from users where HengYuCode=" + username;
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			int islogin = cur.getInt(cur.getColumnIndex("isLogin"));
			data.setIsLogin(islogin);
		}
		cur.close();
		db.close();
		return data;
	}

	// 查询是否有用户当前的为登录状态
	public List<UserRegisterData> findisLogin() {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<UserRegisterData> allNames = new ArrayList<UserRegisterData>();
		String sql = "select username from users where isLogin=1";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			UserRegisterData data = new UserRegisterData();
			String name = cur.getString(cur.getColumnIndex("username"));
			data.setUserName(name);
			allNames.add(data);
		}
		cur.close();
		db.close();
		return allNames;
	}

	// 用户退出 修改用户状态为0
	public boolean updateQuitIsLogin(UserRegisterData data) {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isUpdate = false;
		ContentValues cv = new ContentValues();
		cv.put("isLogin", data.getIsLogin());
		int updateCount = db.update("users", cv, null, null);
		if (updateCount > 0) {
			// 修改成功
			isUpdate = true;
		}
		return isUpdate;
	}

	// 用户退出 清空购物车表
	public boolean deleteAllShopCart() {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isdelete = false;
		int deletecount = db.delete("wares", null, null);
		if (deletecount > 0) {
			isdelete = true;
		}
		db.close();
		return isdelete;
	}

	// 如果当前表格中没有当前的用户，将该用户的信息插入表格里面
	public boolean insertLogin(UserRegisterData data) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from users where username='"
				+ data.getUserName() + "'";
		Cursor cursor = db.rawQuery(sql, null);
		boolean isInsert = false;
		int count = cursor.getCount();
		ContentValues cv = new ContentValues();
		cv.put("username", data.getUserName());
		cv.put("password", data.getPassword());
		cv.put("payPassword", data.getPayPassword());
		cv.put("HengYuCode", data.getHengyuCode());
		cv.put("isLogin", data.getIsLogin());
		cv.put("userkey", data.getUserkey());
		cv.put("userrnd", data.getUserrnd());
		cv.put("phone", data.getPhone());
		cv.put("actualname", data.getActualname());
		cv.put("joindata", data.getJoinData());
		cv.put("activeTime", data.getActiveTime());
		cv.put("PassTicketBalance", data.getPassTicketBalance());
		cv.put("shopPassTicket", data.getShopPassTicket());
		cv.put("credits", data.getCredits());
		cv.put("BossUid", data.getBossUid());

		long recordID = -1;
		if (count != 0) {
			recordID = db.update("users", cv, "username=?",
					new String[] { data.getUserName() });
		} else {

			recordID = db.insert("users", "", cv);

		}
		if (recordID > 0) {
			isInsert = true;
		}
		db.close();
		return isInsert;
	}

	// 查询表格里面所有存在的 恒誉号（HengYuCode）
	public List<UserRegisterData> findByCode(String hengyucode) {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<UserRegisterData> allwares = new ArrayList<UserRegisterData>();
		String sql = "select HengYuCode from users where HengYuCode ="
				+ hengyucode;
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			UserRegisterData data = new UserRegisterData();
			String code = cur.getString(cur.getColumnIndex("HengYuCode"));
			data.setHengyuCode(code);
			allwares.add(data);
		}
		cur.close();
		db.close();
		return allwares;
	}

	// 清空用户信息
	public boolean deleteAllUserInformation() {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isdelete = false;
		int deleteCount = db.delete("users", null, null);
		if (deleteCount > 0) {
			isdelete = true;
		}
		return isdelete;
	}

	public String getBossUid() {
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select bossuid from users where isLogin=1";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			return cursor.getString(0);
		}
		return null;
	}

	// 查询当前登录的用户的恒誉号和key值
	public UserRegisterData findIsLoginHengyuCode() {
		SQLiteDatabase db = helper.getReadableDatabase();
		UserRegisterData data = new UserRegisterData();
		String sql = "select HengYuCode,userkey,password,userrnd,shopPassTicket,phone,credits from users where isLogin=1";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			String code = cur.getString(cur.getColumnIndex("HengYuCode"));
			String userkey = cur.getString(cur.getColumnIndex("userkey"));
			String pwd = cur.getString(cur.getColumnIndex("password"));
			String rnd = cur.getString(cur.getColumnIndex("userrnd"));
			String ticket = cur.getString(cur.getColumnIndex("shopPassTicket"));
			String phone = cur.getString(cur.getColumnIndex("phone"));
			String credits = cur.getString(cur.getColumnIndex("credits"));
			data.setHengyuCode(code);
			data.setUserkey(userkey);
			data.setPassword(pwd);
			data.setUserrnd(rnd);
			data.setShopPassTicket(ticket);
			data.setPhone(phone);
			data.setCredits(credits);
		}
		cur.close();
		db.close();
		return data;
	}

	// 查询表格里面是否有该条数据,并返回他的商品数目（商品有两条属性时）
	public ShopCartData findTwoStyle(String wareid, String stylename1,
									 String stylenature1, String stylename2, String stylenature2) {
		SQLiteDatabase db = helper.getReadableDatabase();
		ShopCartData data = new ShopCartData();
		String select = "wareid=? and (stylenameone=? and stylenatureone=?) and (stylenametwo=?"
				+ "and stylenaturetwo=?)";
		Cursor cur = db.query("wares", new String[] { "number", "orderid" },
				select, new String[] { wareid, stylename1, stylenature1,
						stylename2, stylenature2 }, null, null, null);
		while (cur.moveToNext()) {
			int number = cur.getInt(cur.getColumnIndex("number"));
			int orderid = cur.getInt(cur.getColumnIndex("orderid"));
			data.setNumber(number);
			data.setOrderid(orderid);
		}
		cur.close();
		db.close();
		return data;
	}

	// 插入购物车表 如果商品有两种属性
	public boolean insertShopCartTwoStyle(ShopCartData data) {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isInsert = false;
		ContentValues cv = new ContentValues();
		cv.put("wareid", data.getWareid());
		cv.put("imgurl", data.getImgurl());
		cv.put("warename", data.getWarename());
		cv.put("retailprice", data.getRetailprice());
		cv.put("marketprice", data.getMarketprice());
		cv.put("stylenameone", data.getStylenameone());
		cv.put("stylenatureone", data.getStylenatureone());
		cv.put("stylenametwo", data.getStylenametwo());
		cv.put("stylenaturetwo", data.getStylenaturetwo());
		cv.put("number", data.getNumber());
		cv.put("ischecked", data.getIschecked());
		cv.put("orderid", data.getOrderid());
		cv.put("jf", data.getJf());
		long recordId = db.insert("wares", "", cv);
		if (recordId > 0) {
			isInsert = true;
		}
		db.close();
		return isInsert;
	}

	// 查询表格里面是否有该条数据,并返回他的商品数目（商品没有属性时）
	public ShopCartData findNoStyle(String wareid) {
		SQLiteDatabase db = helper.getReadableDatabase();
		ShopCartData data = new ShopCartData();
		String select = "wareid=?";
		Cursor cur = db.query("wares", new String[] { "number", "orderid" },
				select, new String[] { wareid }, null, null, null);
		while (cur.moveToNext()) {
			int number = cur.getInt(cur.getColumnIndex("number"));
			int orderid = cur.getInt(cur.getColumnIndex("orderid"));
			data.setNumber(number);
			data.setOrderid(orderid);
		}
		cur.close();
		db.close();
		return data;
	}

	// 插入购物车表 如果商品没有属性
	public boolean insertShopCartNoStyle(ShopCartData data) {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isInsert = false;
		ContentValues cv = new ContentValues();
		cv.put("wareid", data.getWareid());
		cv.put("imgurl", data.getImgurl());
		cv.put("warename", data.getWarename());
		cv.put("retailprice", data.getRetailprice());
		cv.put("marketprice", data.getMarketprice());
		cv.put("number", data.getNumber());
		cv.put("ischecked", data.getIschecked());
		cv.put("orderid", data.getOrderid());
		cv.put("jf", data.getJf());
		long recordId = db.insert("wares", "", cv);
		if (recordId > 0) {
			isInsert = true;
		}
		db.close();
		return isInsert;
	}

	// 查询表格里面是否有该条数据,并返回他的商品数目（商品有一条属性时）
	public ShopCartData findOneStyle(String wareid, String stylename1,
									 String stylenature1) {
		SQLiteDatabase db = helper.getReadableDatabase();
		ShopCartData data = new ShopCartData();
		String select = "wareid=? and (stylenameone=? and stylenatureone=?)";
		Cursor cur = db.query("wares", new String[] { "number", "orderid" },
				select, new String[] { wareid, stylename1, stylenature1 },
				null, null, null);
		while (cur.moveToNext()) {
			int number = cur.getInt(cur.getColumnIndex("number"));
			int orderid = cur.getInt(cur.getColumnIndex("orderid"));
			data.setNumber(number);
			data.setOrderid(orderid);
		}
		cur.close();
		db.close();
		return data;
	}

	// 插入购物车表 如果商品有一种属性
	public boolean insertShopCartOneStyle(ShopCartData data) {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isInsert = false;
		ContentValues cv = new ContentValues();
		cv.put("wareid", data.getWareid());
		cv.put("imgurl", data.getImgurl());
		cv.put("warename", data.getWarename());
		cv.put("retailprice", data.getRetailprice());
		cv.put("marketprice", data.getMarketprice());
		cv.put("stylenameone", data.getStylenameone());
		cv.put("stylenatureone", data.getStylenatureone());
		cv.put("number", data.getNumber());
		cv.put("ischecked", data.getIschecked());
		cv.put("orderid", data.getOrderid());
		cv.put("jf", data.getJf());
		long recordId = db.insert("wares", "", cv);
		if (recordId > 0) {
			isInsert = true;
		}
		return isInsert;
	}

	// 查询商品的信息 加入购物车
	public List<ShopCartData> findShopCart() {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<ShopCartData> allNames = new ArrayList<ShopCartData>();
		String sql = "select * from wares where ischecked=1";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			ShopCartData data = new ShopCartData();
			int wareid = cur.getInt(cur.getColumnIndex("wareid"));
			String imgurl = cur.getString(cur.getColumnIndex("imgurl"));
			String warename = cur.getString(cur.getColumnIndex("warename"));
			String retailprice = cur.getString(cur
					.getColumnIndex("retailprice"));
			String marketprice = cur.getString(cur
					.getColumnIndex("marketprice"));
			String stylenameone = cur.getString(cur
					.getColumnIndex("stylenameone"));
			String stylenatureone = cur.getString(cur
					.getColumnIndex("stylenatureone"));
			String stylenametwo = cur.getString(cur
					.getColumnIndex("stylenametwo"));
			String stylenaturetwo = cur.getString(cur
					.getColumnIndex("stylenaturetwo"));
			int jf = cur.getInt(cur.getColumnIndex("jf"));
			int number = cur.getInt(cur.getColumnIndex("number"));
			int orderid = cur.getInt(cur.getColumnIndex("orderid"));
			data.setWareid(wareid);
			data.setImgurl(imgurl);
			data.setWarename(warename);
			data.setRetailprice(retailprice);
			data.setMarketprice(marketprice);
			data.setStylenameone(stylenameone);
			data.setStylenatureone(stylenatureone);
			data.setStylenametwo(stylenametwo);
			data.setStylenaturetwo(stylenaturetwo);
			data.setNumber(number);
			data.setOrderid(orderid);
			data.setJf(jf);
			allNames.add(data);
		}
		cur.close();
		db.close();
		return allNames;
	}

	// 用户点击 删除这条信息
	public boolean deleteByOrderid(String orderid) {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isdelete = false;
		String select = "orderid=?";
		int deleteCount = db.delete("wares", select, new String[] { orderid });
		if (deleteCount > 0) {
			isdelete = true;
		}
		return isdelete;
	}

	// 修改表格里面的商品数目 （根据订单id）
	public boolean updateByOrderid(String orderid, ShopCartData data) {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isUpdate = false;
		ContentValues cv = new ContentValues();
		cv.put("number", data.getNumber());
		String select = "orderid=?";
		int updateCount = db.update("wares", cv, select,
				new String[] { orderid });
		if (updateCount > 0) {
			isUpdate = true;
		}
		return isUpdate;
	}

	public ShopCartData findResult() {
		SQLiteDatabase db = helper.getReadableDatabase();
		ShopCartData data = new ShopCartData();
		String sql = "select sum(number),sum(number*marketprice),	(sum(number*marketprice)-sum(number*retailprice)),sum(number*retailprice) ,sum(number*jf)from wares where ischecked=1";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			int endnumber = cur.getInt(cur.getColumnIndex("sum(number)"));
			String endmarketprice = cur.getString(cur
					.getColumnIndex("sum(number*marketprice)"));
			String preferential = cur
					.getString(cur
							.getColumnIndex("(sum(number*marketprice)-sum(number*retailprice))"));
			String endmoney = cur.getString(cur
					.getColumnIndex("sum(number*retailprice)"));
			int jf = cur.getInt(cur.getColumnIndex("sum(number*jf)"));
			data.setEndnumber(endnumber);
			data.setEndmarketprice(endmarketprice);
			data.setPreferential(preferential);
			data.setEndmoney(endmoney);
			data.setJf(jf);
		}
		cur.close();
		db.close();
		return data;
	}

	// 进入聊天页面 获取当前登录用户的 恒誉号和密码
	public UserRegisterData findByChat() {
		SQLiteDatabase db = helper.getReadableDatabase();
		UserRegisterData data = new UserRegisterData();
		String sql = "select HengYuCode,password from users where isLogin=1";
		Cursor cur = db.rawQuery(sql, null);
		while (cur.moveToNext()) {
			String code = cur.getString(cur.getColumnIndex("HengYuCode"));
			String pwd = cur.getString(cur.getColumnIndex("password"));
			data.setHengyuCode(code);
			data.setPassword(pwd);
		}
		cur.close();
		db.close();
		return data;
	}

	// 清空商品分类信息
	public boolean deleteAllWareInformation() {
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean isdelete = false;
		int deleteCount = db.delete("status", null, null);
		if (deleteCount > 0) {
			isdelete = true;
		}
		return isdelete;
	}

}
