package com.yunsen.enjoy.model;

import java.io.Serializable;
import java.util.ArrayList;

public class GuigeBean implements Serializable {

	public String id;
	public String title;
	public String icon_url;
	public String summary;
	public String proposal;
	public String cause;
	public String doctor;
	public String img_url;
	
	private ArrayList<GuigellBean> list;
	
	public ArrayList<GuigellBean> getList() {
		return list;
	}
	public void setList(ArrayList<GuigellBean> list) {
		this.list = list;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getProposal() {
		return proposal;
	}
	public void setProposal(String proposal) {
		this.proposal = proposal;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
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
