package com.hengyushop.dao;

import java.util.ArrayList;

public class WideDo {
	private String id;
	private String name;
	private ArrayList<WideMarketDo> list;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<WideMarketDo> getList() {
		return list;
	}
	public void setList(ArrayList<WideMarketDo> list) {
		this.list = list;
	}
	
}
