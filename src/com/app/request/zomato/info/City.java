package com.app.request.zomato.info;

import java.util.List;

public class City {

	public String id,name,latitute,longitude,countryID,conuntryName;
	List<SubZone> subZones;
	
	
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

	public String getLatitute() {
		return latitute;
	}

	public void setLatitute(String latitute) {
		this.latitute = latitute;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCountryID() {
		return countryID;
	}

	public void setCountryID(String countryID) {
		this.countryID = countryID;
	}

	public String getConuntryName() {
		return conuntryName;
	}

	public void setConuntryName(String conuntryName) {
		this.conuntryName = conuntryName;
	}	
	
	public List<SubZone> getSubZones() {
		return subZones;
	}

	public void setSubZones(List<SubZone> subZones) {
		this.subZones = subZones;
	}
}
