package com.hengyushop.airplane.data;

import java.io.Serializable;

public class ManagerTrainDo implements Serializable {
	private String orderNum;
	private String orderTime;
	private String statusTag;
	private String fromStation;
	private String toStation;
	private String lishi;
	private String startTime;
	private String arriveTime;
	private String orderTag;
	private String trade_no;
	private String checi;
	private String price;

	public String getCheci() {
		return checi;
	}

	public void setCheci(String checi) {
		this.checi = checi;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getStatusTag() {
		return statusTag;
	}

	public void setStatusTag(String statusTag) {
		this.statusTag = statusTag;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public String getLishi() {
		return lishi;
	}

	public void setLishi(String lishi) {
		this.lishi = lishi;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getOrderTag() {
		return orderTag;
	}

	public void setOrderTag(String orderTag) {
		this.orderTag = orderTag;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

}
