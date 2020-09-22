/**
 * 
 */
package com.app.request.zomato.info;

import java.util.List;

/**
 * @author Anand
 * Models the events in the city
 */
public class Events {
	private int id;
	private String name;
	private String phone;
	private String mobile;
	private String description;
	private List<Show> showList;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Show> getShowList() {
		return showList;
	}
	public void setShowList(List<Show> showList) {
		this.showList = showList;
	}
	
}
