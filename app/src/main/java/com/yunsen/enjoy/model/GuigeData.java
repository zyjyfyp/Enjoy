package com.yunsen.enjoy.model;

import java.io.Serializable;
import java.util.ArrayList;


public class GuigeData implements Serializable {

	public String id;
	public String title;
	
	private ArrayList<GuigeBean> list;
	
	
	public ArrayList<GuigeBean> getList() {
		return list;
	}
	public void setList(ArrayList<GuigeBean> list) {
		this.list = list;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
