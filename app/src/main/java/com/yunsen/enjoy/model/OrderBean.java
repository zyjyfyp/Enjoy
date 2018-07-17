package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

import java.io.Serializable;

public class OrderBean implements Serializable {

	public String id;
	public String goods_title;
	public String article_title;
	public String img_url;
	public String goods_price;
	public String real_price;
	public int quantity;
	public String sell_price;
	public String market_price;
	public String article_id;
	public String groupon_title;
	public String groupon_no;
	public String timer_time;
	public String start_time;
	public String end_time;
	public String order_no;
	public String foreman_id;
	public String foreman_name;
	public String point_title;
	public String point_price;
	public String point_value;
	public String award_status;
	public String groupon_item_people;
	public String groupon_item_member;
	public String groupon_img_url;
	public String order_id;
	public String company_id;
	public String express_status;
	public String cashing_packet;

	public String getCashing_packet() {
		return cashing_packet;
	}

	public void setCashing_packet(String cashing_packet) {
		this.cashing_packet = cashing_packet;
	}

	public String getArticle_title() {
		return article_title;
	}

	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}

	public String getExpress_status() {
		return express_status;
	}

	public void setExpress_status(String express_status) {
		this.express_status = express_status;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getGroupon_img_url() {
		return groupon_img_url;
	}

	public void setGroupon_img_url(String groupon_img_url) {
		this.groupon_img_url = groupon_img_url;
	}

	public String getGroupon_item_member() {
		return groupon_item_member;
	}

	public void setGroupon_item_member(String groupon_item_member) {
		this.groupon_item_member = groupon_item_member;
	}

	public String getAward_status() {
		return award_status;
	}

	public void setAward_status(String award_status) {
		this.award_status = award_status;
	}

	public String getPoint_title() {
		return point_title;
	}

	public void setPoint_title(String point_title) {
		this.point_title = point_title;
	}

	public String getPoint_price() {
		return point_price;
	}

	public void setPoint_price(String point_price) {
		this.point_price = point_price;
	}

	public String getPoint_value() {
		return point_value;
	}

	public void setPoint_value(String point_value) {
		this.point_value = point_value;
	}

	public String getGroupon_title() {
		return groupon_title;
	}

	public void setGroupon_title(String groupon_title) {
		this.groupon_title = groupon_title;
	}

	public String getGroupon_no() {
		return groupon_no;
	}

	public void setGroupon_no(String groupon_no) {
		this.groupon_no = groupon_no;
	}

	public String getTimer_time() {
		return timer_time;
	}

	public void setTimer_time(String timer_time) {
		this.timer_time = timer_time;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getGroupon_item_people() {
		return groupon_item_people;
	}

	public void setGroupon_item_people(String groupon_item_people) {
		this.groupon_item_people = groupon_item_people;
	}

	public String getForeman_id() {
		return foreman_id;
	}

	public void setForeman_id(String foreman_id) {
		this.foreman_id = foreman_id;
	}

	public String getForeman_name() {
		return foreman_name;
	}

	public void setForeman_name(String foreman_name) {
		this.foreman_name = foreman_name;
	}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public String getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public String getReal_price() {
		return real_price;
	}

	public void setReal_price(String real_price) {
		this.real_price = real_price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoods_title() {
		return goods_title;
	}

	public void setGoods_title(String goods_title) {
		this.goods_title = goods_title;
	}

	public String getImg_url() {
		if (img_url != null && img_url.startsWith("http")) {
			return img_url;
		} else {
			return URLConstants.REALM_URL + img_url;
		}
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

}
