package com.yunsen.enjoy.widget.city;

/**
 * 城市属性实体类
 * @author gugalor
 *
 */

public  class CityModel {
	/**
	 * id : 110100
	 * name : 北京
	 */
	private String firstLetter;
	private String id;
	private String name;

	public CityModel() {
	}

	public CityModel(String firstLetter) {
		this.firstLetter = firstLetter;
	}

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

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
}
