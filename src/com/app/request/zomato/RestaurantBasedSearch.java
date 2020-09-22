package com.app.request.zomato;

import com.app.request.zomato.exception.CityNotFoundException;

public class RestaurantBasedSearch extends SearchZomato {
	
	public RestaurantBasedSearch(String cityName, String restaurantName) throws CityNotFoundException {
		// TODO Auto-generated constructor stub
		super(cityName);
		this.restaurantName=restaurantName;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
}
