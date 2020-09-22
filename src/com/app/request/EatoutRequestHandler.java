package com.app.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.request.zomato.HtmlService;
import com.app.request.zomato.LocationBasedSearch;
import com.app.request.zomato.RestaurantBasedSearch;
import com.app.request.zomato.Result;
import com.app.request.zomato.SearchZomato;
import com.app.request.zomato.exception.CityNotFoundException;
import com.app.request.zomato.exception.LocalityNotFoundException;
import com.app.request.zomato.exception.NetworkException;
import com.org.app.request.RequestHandler;
import com.org.app.utils.UtilFunctions;

public class EatoutRequestHandler extends RequestHandler{
	String cityName,restaurantName,locality, comeWithLocality="Eatout ";
	private boolean inNeedOfAddressAlone=false;
	SearchZomato zomato;
	@Override
	public void init(HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.init(request);
		int i=1;
		cityName=getUserMessage(i++);
		restaurantName=getUserMessage(i++);
		locality=getUserMessage(i++);
		comeWithLocality+=(cityName+" "+restaurantName+" "); 
	}
	 
	@Override
	public boolean hasValidParams() {
		// TODO Auto-generated method stub
		if(isNotEmpty(cityName)&&isNotEmpty(restaurantName)){
			return true;
		}
		return false;
	}
	
	@Override
	public void doProcess() throws Exception { 
		// TODO Auto-generated method stub
		HtmlService htmlService=new HtmlService();
		try{
			// Check whether the user has specified a Locality. If present in our cached City's subzone, then the user might be asking 
			// about list of restaurants in the locality.
			zomato=new LocationBasedSearch(cityName, restaurantName);
		}catch (LocalityNotFoundException e) {
			// if the user has replied with 3rd option, then the user might be asking the restaurant's address in the locality
			if(UtilFunctions.isNotEmpty(locality)){
				zomato=new LocationBasedSearch(cityName, locality);
				((LocationBasedSearch)zomato).setRestaurantName(restaurantName);
				inNeedOfAddressAlone=true;
			}else{
				// Now the user must have specified about restaurant name
				zomato=new RestaurantBasedSearch(cityName, restaurantName);
			}			
		}catch(CityNotFoundException ce){
			htmlService.setHtmlContent(AppConstants.cityNotFoundMsg);
			return;
		}
		List<Result> list=null;
		try{
			list=zomato.search();
			if(zomato instanceof LocationBasedSearch){
				for(Result result:list){
					htmlService.insertMessage(result.getName()+"-"+(inNeedOfAddressAlone?result.getAddress():result.getLocality()));
					htmlService.insertBreak();
				}
			}else{
				for(Result result:list){
					htmlService.insertMessage(result.getName()+"-"+result.getLocality());
					htmlService.insertBreak();
				}
			}
		}catch(NetworkException ne){
			htmlService.setHtmlContent(AppConstants.serverError);
		}
		htmlService.insertBreak();
		htmlService.insertMessage(AppConstants.zomatoMsg);
		setResponseText(htmlService.getHtmlContent());
	}
}