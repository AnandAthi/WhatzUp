package com.app.request.zomato;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import com.app.request.zomato.exception.CityNotFoundException;
import com.app.request.zomato.exception.NetworkException;
import com.app.request.zomato.info.Events;
import com.app.request.zomato.info.Show;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws XMLStreamException 
	 * @throws CityNotFoundException 
	 * @throws NetworkException 
	 * @throws FactoryConfigurationError 
	 */
	public static void main(String[] args) throws IOException, XMLStreamException, CityNotFoundException, FactoryConfigurationError, NetworkException {
		// TODO Auto-generated method stub
		/*List<Result> results=SearchRestaurant.searchRestaurant("Chennai", "KFC");
		for(Result result:results){
			System.out.println(result.getName()+" "+result.getAddress());
		}*/
		
		//List<Events> event = CityEvents.getEvents("1", 0);
		List<Events> event = CityEvents.getEventDetails("100001482");
		for(Events e : event) {
			System.out.println(e.getId() + " " +e.getName() + " " +e.getPhone() + " "+ e.getMobile());
			for(Show s : e.getShowList()) {
				System.out.println(s.getVenue() + s.getVenuetype() + s.getShowDate() + s.getShowTime());
			}
		}
	}
	
//100001482
}
