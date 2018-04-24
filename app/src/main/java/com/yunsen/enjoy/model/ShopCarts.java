package com.yunsen.enjoy.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ShopCarts implements Serializable {
	private String name;
	private ArrayList<ShopCartData> list;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ShopCartData> getList() {
		return list;
	}

	public void setList(ArrayList<ShopCartData> list) {
		this.list = list;
	}

}
