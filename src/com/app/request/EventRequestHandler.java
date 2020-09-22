/**
 * 
 */
package com.app.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.request.zomato.CityEvents;
import com.app.request.zomato.Constants;
import com.app.request.zomato.exception.CityNotFoundException;
import com.app.request.zomato.exception.NetworkException;
import com.app.request.zomato.info.CityInformation;
import com.app.request.zomato.info.Events;
import com.app.request.zomato.info.Show;
import com.org.app.request.RequestHandler;

/**
 * @author Anand
 *
 */
public class EventRequestHandler extends RequestHandler {
	private String cityName,id;
	private int startResults = 0;
	private boolean eventDetails = false;
	
	@Override
	public void init(HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.init(request);
		//Get what the user is really trying to do
		getUserRequest(request);
	}
	
	private void getUserRequest(HttpServletRequest request) {
		
		String message = null;
		String[] userRequest = null;
		int length = 0;
		try {
			 	message = URLDecoder.decode(request.getParameter("txtweb-message"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userRequest = message.split(" ");
		length = userRequest.length;
		//Now we have the user's message, find out what he is really trying to do
		if(length == 2) {
			//he could be asking for more events or details about a particular event
			if("id".equalsIgnoreCase(userRequest[0])) {
				this.eventDetails = true;
				this.id = userRequest[1];
			}
			else {
				this.cityName = userRequest[1];
				startResults = Integer.parseInt(userRequest[0]);
			}
		}
		else if(length == 1) {
			//he wants to know the events in his city;
			this.cityName = userRequest[0];
		}
		else {
			//something's wrong
		}
	}

	@Override
	public void doProcess() throws Exception {
	 
		try{
		List<Events> events = null;
		String response = "";
		//From the type of user request, get the correct details
		if(eventDetails) {
			//the user wants details about a particular event
			events = CityEvents.getEventDetails(this.id);
			response = processEventDetails(events);
		}
		else {
			String cityId = CityInformation.getCityID(this.cityName);
			//get the list of events, based on the count passed
			events = CityEvents.getEvents(cityId,this.startResults);
			response = processEvents(events);
		}
		setResponseText(response);
		
		}catch(CityNotFoundException cnfe){
			setResponseText(AppConstants.cityNotFoundMsg);
		}catch(NetworkException ne){
			setResponseText(AppConstants.serverError);
		}
	}

	private String processEvents(List<Events> events) throws UnsupportedEncodingException {
		StringBuffer temp = new StringBuffer();
		if(events.size() > 0) {
		temp.append(" Here is what's happening in your city for the next 7 days" + Constants.htmlLineBreak);
		for(Events event : events) {
			temp.append("Event id : " + event.getId() + Constants.htmlLineBreak);
			temp.append("Event Name : " + event.getName() + Constants.htmlLineBreak);
			temp.append("Contact : "+ event.getPhone() + " " + event.getMobile());
			temp.append(Constants.htmlLineBreak);
		}
		temp.append("Interested in an event ? ");
		temp.append("reply @whatzup id  ").append("&lt;eventid&gt;");
		temp.append("  to know more about it");
		temp.append(Constants.htmlLineBreak);
		temp.append(" For more events; ");
		temp.append("reply @whatzup ").append(this.startResults+5).append("  &lt;cityname&gt;").append(Constants.htmlLineBreak);
		}
		else {
			temp.append("We are sorry ! Thats all we have for your city.").append(Constants.htmlLineBreak); 
		}
		temp.append(AppConstants.zomatoMsg);
		return temp.toString();
	}

	private String processEventDetails(List<Events> events) {
		StringBuffer temp = new StringBuffer();
		boolean count = true;
		for(Events event : events) {
			if(event.getShowList().size() > 0) {
			temp.append(" Details for the Event "+ event.getId());
			temp.append(Constants.htmlLineBreak).append("What : ").append(event.getName());
			temp.append(", ").append(event.getDescription());
			temp.append(Constants.htmlLineBreak).append("Where : ");
			 for(Show show : event.getShowList()) {
				 if(count) {
					 temp.append(show.getVenue()).append(Constants.htmlLineBreak);
					 temp.append(" What type of place : ");
					 temp.append(" Its a ").append(show.getVenuetype()).append(Constants.htmlLineBreak);
					 temp.append("When : ").append(Constants.htmlLineBreak);
					 count = false;
				 }
				 temp.append(show.getShowDate()).append(" ").append(show.getShowTime());
				 temp.append(Constants.htmlLineBreak);
			 }
		  }
			else {
					temp.append(" You have entered an incorrect eventid. No events matching for this id");
			}
			 temp.append("Contact :");
			 if(event.getPhone() != null) temp.append(event.getPhone());
			 temp.append(" ");
			 if(event.getMobile() != null) temp.append(event.getMobile());
		}
		
		temp.append(Constants.htmlLineBreak);
		temp.append(" To know the latest events in your city,").append("reply @whatzup  &ltcityname&gt");
		temp.append(Constants.htmlLineBreak).append(AppConstants.zomatoMsg);
		return temp.toString();
	}
	
}
