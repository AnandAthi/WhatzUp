package com.app.request;

import javax.servlet.http.HttpServletRequest;

import com.app.request.zomato.HtmlService;
import com.app.request.zomato.info.City;
import com.app.request.zomato.info.CityInformation;
import com.org.app.request.RequestHandler;
import com.org.app.utils.UtilFunctions;

public class HelpRequestHandler extends RequestHandler {
	String keyWord;
	@Override
	public void init(HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.init(request);
		keyWord=getUserMessage(1);
	}
	@Override
	public void doProcess() throws Exception {
		// TODO Auto-generated method stub
		HtmlService service=new HtmlService();
		if(UtilFunctions.isNotEmpty(keyWord)){
			if(keyWord.equalsIgnoreCase("city")){
				for(City city:CityInformation.getCities()){
					service.insertMessage(city.getName());
					service.insertBreak();
				}
			}else if (keyWord.equalsIgnoreCase("events")){
				service.insertMessage(AppConstants.eventsHelpMsg);
				service.insertBreak();
			}else if (keyWord.equalsIgnoreCase("eatout")){
				service.insertMessage(AppConstants.eatoutHelpMsg);
				service.insertBreak();
			}
			service.insertBreak();
			service.insertMessage(AppConstants.zomatoMsg);
		}else{
			service.insertMessage(AppConstants.generalHelpMsg);
			service.insertBreak();
		}
		setResponseText(service.getHtmlContent());
	}
}
