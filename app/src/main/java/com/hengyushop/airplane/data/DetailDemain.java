package com.hengyushop.airplane.data;

import java.io.Serializable;

public class DetailDemain implements Serializable{
	private int	productItemId ;
	private String proName ;
	private String retailPrice;
	private String marketPrice;
	private String proFaceImg;
	private String proInverseImg;
	private String proDoDetailImg;
	private String proDesignImg ;
	private String proSupplementImg ;
	public int getProductItemId() {
		return productItemId;
	}
	public void setProductItemId(int productItemId) {
		this.productItemId = productItemId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getProFaceImg() {
		return proFaceImg;
	}
	public void setProFaceImg(String proFaceImg) {
		this.proFaceImg = proFaceImg;
	}
	public String getProInverseImg() {
		return proInverseImg;
	}
	public void setProInverseImg(String proInverseImg) {
		this.proInverseImg = proInverseImg;
	}
	public String getProDoDetailImg() {
		return proDoDetailImg;
	}
	public void setProDoDetailImg(String proDoDetailImg) {
		this.proDoDetailImg = proDoDetailImg;
	}
	public String getProDesignImg() {
		return proDesignImg;
	}
	public void setProDesignImg(String proDesignImg) {
		this.proDesignImg = proDesignImg;
	}
	public String getProSupplementImg() {
		return proSupplementImg;
	}
	public void setProSupplementImg(String proSupplementImg) {
		this.proSupplementImg = proSupplementImg;
	}
	
}
