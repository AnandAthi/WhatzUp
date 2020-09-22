package com.app.request.zomato;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import com.app.request.zomato.exception.CityNotFoundException;
import com.app.request.zomato.exception.LocalityNotFoundException;
import com.app.request.zomato.exception.NetworkException;
import com.app.request.zomato.info.CityInformation;

public class LocationBasedSearch extends SearchZomato {
	
	protected String locationID;
	public LocationBasedSearch(String cityName, String locality) throws CityNotFoundException, LocalityNotFoundException {
		super(cityName);
		this.locality=locality;
		locationID=CityInformation.getSubZoneID(cityName, locality);
	}
	
	@Override
	public List<Result> search() throws XMLStreamException, IOException,
			NetworkException {
		// TODO Auto-generated method stub
		HashMap<String, String> params=new HashMap<String, String>();
		addParam(params, "city_id", cityID);
		addParam(params, "subzone_id", locationID);
		return parseResponseXML(post(Constants.zSearch, params));
	}
	
	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	public void setRestaurantName(String restaurantName) throws XMLStreamException, IOException, NetworkException{
		this.restaurantName=restaurantName;
	}
}
