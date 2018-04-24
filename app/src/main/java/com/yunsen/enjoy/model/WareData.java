package com.yunsen.enjoy.model;

public class WareData {

	public int ID;
	public String productTypeName;
	public int parentId;
	public int layer;
	public String openUrl;
	public int SpecCommend;
	public String id;
	public String title;
	public String[] strname;
	
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

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getOpenUrl() {
		return openUrl;
	}

	public void setOpenUrl(String openUrl) {
		this.openUrl = openUrl;
	}

	public int getSpecCommend() {
		return SpecCommend;
	}

	public void setSpecCommend(int specCommend) {
		SpecCommend = specCommend;
	}

	public String[] getStrname() {
		return strname;
	}

	public void setStrname(String[] strname) {
		this.strname = strname;
	}

}
