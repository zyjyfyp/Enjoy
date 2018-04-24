package com.hengyushop.airplane.data;

import java.io.Serializable;

public class AirDo implements Serializable {
	private String time;
	private String trade_no;
	private String flightNo;
	private String orgCity;
	private String dstCity;
	private String price;
	private String arrTime;
	private String depTime;
	private String airName;
	private String tag;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getOrgCity() {
		return orgCity;
	}
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}
	public String getDstCity() {
		return dstCity;
	}
	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getArrTime() {
		return arrTime;
	}
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}
	public String getDepTime() {
		return depTime;
	}
	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}
	public String getAirName() {
		return airName;
	}
	public void setAirName(String airName) {
		this.airName = airName;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
