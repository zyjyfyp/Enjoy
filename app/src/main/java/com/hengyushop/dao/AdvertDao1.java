package com.hengyushop.dao;

import com.yunsen.enjoy.http.URLConstants;

import java.io.Serializable;

public class AdvertDao1 implements Serializable {
	public String id;
	private String image;
	private String detail;
	private String seri;
	private String ad_url;
	public String img_url;
	public String link_url;
	public String recharge_no;
	
	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getLink_url() {
		return link_url;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}

	public String getRecharge_no() {
		return recharge_no;
	}

	public void setRecharge_no(String recharge_no) {
		this.recharge_no = recharge_no;
	}

	public String getAd_url() {
		if (ad_url != null && ad_url.startsWith("http")) {
			return ad_url;
		} else {
			return URLConstants.REALM_URL + ad_url;
		}
	}

	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}

	public String getSeri() {
		return seri;
	}

	public void setSeri(String seri) {
		this.seri = seri;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
