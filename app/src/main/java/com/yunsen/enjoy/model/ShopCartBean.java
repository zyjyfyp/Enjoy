package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

import java.io.Serializable;

public class ShopCartBean implements Serializable {
	/*
	 * 
	 */
	public int wareid;
	public String imgurl;
	public String warename;
	public String retailprice;
	public String marketprice;
	public String stylenameone;
	public String stylenatureone;
	public String stylenametwo;
	public String stylenaturetwo;
	public int number;
	public int ischecked;
	public int orderid;
	public int jf;
	public int endnumber;
	public String endmarketprice;
	public String preferential;
	public String endmoney;
	public String totalProductPrice;
	public boolean isCheck;
	public boolean uploadStatus;
	public boolean IsDeleted;
	public String title;
	public String img_url;
	public String market_price;
	public String sell_price;
	public String id;
	public String quantity;
	public String goods_title;
	public String article_id;
	public String goods_id;



	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_title() {
		return goods_title;
	}

	public void setGoods_title(String goods_title) {
		this.goods_title = goods_title;
	}

	public boolean isUploadStatus() {
		return uploadStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg_url() {
		if (img_url != null && img_url.startsWith("http")) {
			return img_url;
		} else {
			String trim = "";
			if (img_url != null) {
				trim = img_url.trim();
			}
			return URLConstants.REALM_URL + trim;
		}
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getMarket_price() {
		return market_price;
	}

	public void setMarket_price(String market_price) {
		this.market_price = market_price;
	}

	public String getSell_price() {
		return sell_price;
	}

	public void setSell_price(String sell_price) {
		this.sell_price = sell_price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUploadStatus(boolean uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public boolean isIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		IsDeleted = isDeleted;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getTotalProductPrice() {
		return totalProductPrice;
	}

	public void setTotalProductPrice(String totalProductPrice) {
		this.totalProductPrice = totalProductPrice;
	}

	public int getJf() {
		return jf;
	}

	public void setJf(int jf) {
		this.jf = jf;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getIschecked() {
		return ischecked;
	}

	public void setIschecked(int ischecked) {
		this.ischecked = ischecked;
	}

	public int getWareid() {
		return wareid;
	}

	public void setWareid(int wareid) {
		this.wareid = wareid;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getWarename() {
		return warename;
	}

	public void setWarename(String warename) {
		this.warename = warename;
	}

	public String getRetailprice() {
		return retailprice;
	}

	public void setRetailprice(String retailprice) {
		this.retailprice = retailprice;
	}

	public String getMarketprice() {
		return marketprice;
	}

	public void setMarketprice(String marketprice) {
		this.marketprice = marketprice;
	}

	public String getStylenameone() {
		return stylenameone;
	}

	public void setStylenameone(String stylenameone) {
		this.stylenameone = stylenameone;
	}

	public String getStylenatureone() {
		return stylenatureone;
	}

	public void setStylenatureone(String stylenatureone) {
		this.stylenatureone = stylenatureone;
	}

	public String getStylenametwo() {
		return stylenametwo;
	}

	public void setStylenametwo(String stylenametwo) {
		this.stylenametwo = stylenametwo;
	}

	public String getStylenaturetwo() {
		return stylenaturetwo;
	}

	public void setStylenaturetwo(String stylenaturetwo) {
		this.stylenaturetwo = stylenaturetwo;
	}

	public int getEndnumber() {
		return endnumber;
	}

	public void setEndnumber(int endnumber) {
		this.endnumber = endnumber;
	}

	public String getEndmarketprice() {
		return endmarketprice;
	}

	public void setEndmarketprice(String endmarketprice) {
		this.endmarketprice = endmarketprice;
	}

	public String getPreferential() {
		return preferential;
	}

	public void setPreferential(String preferential) {
		this.preferential = preferential;
	}

	public String getEndmoney() {
		return endmoney;
	}

	public void setEndmoney(String endmoney) {
		this.endmoney = endmoney;
	}

}
