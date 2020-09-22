package com.app.request;

import com.app.request.zomato.Constants;

public class AppConstants {
	public static final String filterByLocalityMsg="Please with reply the option to get the locality address.";
	public static final String cityNotFoundMsg="The city you have given is invalid. Please enter a different City";
	public static final String zomatoMsg="Powered by zomato.com";
	public static final String serverError = "Ah Snap ! Something's wrong. please try again later";
	public static final String generalHelpMsg="Thanks for using our application. Use @whatzup Eatout &ltCityName&gt &ltRestaurantName&gt for getting the details on restaurant."+
			Constants.htmlLineBreak+"Use @whatzup help Eatout or @whatzup help Events for specific search";
	public static final String eatoutHelpMsg="Use @whatzup Eatout &ltCityName&gt &ltRestaurantName&gt to find the localities of it." + 
			Constants.htmlLineBreak+"Use @whatzup Eatout &ltCityName&gt &ltLocality&gt to find restaurants in that locality." +
			Constants.htmlLineBreak+"Use @whatzup Eatout &ltCityName&gt &ltRestaurantName&gt &ltLocality&gt to find its address in that locality.";
	public static final String eventsHelpMsg="Use @whatzup Eatout &ltCityName&gt to hear the events on your city";
			
}
