package com.yunsen.enjoy.model;

import java.io.Serializable;
import java.util.List;


public class MyOrderData implements Serializable {

	public String id;
	public String area;
	public String status;
	public String order_no;
	public String payment_status;
	public String province;
	public String address;
	public String user_name;
	public String payment_time;
	public String express_status;
	public String express_fee;
	public String company_id;
	public String company_name;
    public String payable_amount;
    public String trade_no;
    public String add_time;
    public String complete_time;
    public String rebate_time;
    public String cashing_packet;
    public String city;
    public String mobile;
    public String accept_name;
    public String accept_no;
    public String exchange_price_total;
    public String exchange_point_total;
	
	public String getExchange_price_total() {
		return exchange_price_total;
	}
	public void setExchange_price_total(String exchange_price_total) {
		this.exchange_price_total = exchange_price_total;
	}
	public String getExchange_point_total() {
		return exchange_point_total;
	}
	public void setExchange_point_total(String exchange_point_total) {
		this.exchange_point_total = exchange_point_total;
	}
	public String getAccept_no() {
		return accept_no;
	}
	public void setAccept_no(String accept_no) {
		this.accept_no = accept_no;
	}
	public String getAccept_name() {
		return accept_name;
	}
	public void setAccept_name(String accept_name) {
		this.accept_name = accept_name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCashing_packet() {
		return cashing_packet;
	}
	public void setCashing_packet(String cashing_packet) {
		this.cashing_packet = cashing_packet;
	}
	public String getRebate_time() {
		return rebate_time;
	}
	public void setRebate_time(String rebate_time) {
		this.rebate_time = rebate_time;
	}
	public String getComplete_time() {
		return complete_time;
	}
	public void setComplete_time(String complete_time) {
		this.complete_time = complete_time;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getPayable_amount() {
		return payable_amount;
	}
	public void setPayable_amount(String payable_amount) {
		this.payable_amount = payable_amount;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getExpress_fee() {
		return express_fee;
	}
	public void setExpress_fee(String express_fee) {
		this.express_fee = express_fee;
	}
	public String getExpress_status() {
		return express_status;
	}
	public void setExpress_status(String express_status) {
		this.express_status = express_status;
	}
	public String getPayment_time() {
		return payment_time;
	}
	public void setPayment_time(String payment_time) {
		this.payment_time = payment_time;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<OrderBean> list;
	
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public List<OrderBean> getList() {
		return list;
	}
	public void setList(List<OrderBean> list) {
		this.list = list;
	}


}
