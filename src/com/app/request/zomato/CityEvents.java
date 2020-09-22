/**
 * 
 */
package com.app.request.zomato;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.app.request.zomato.exception.NetworkException;
import com.app.request.zomato.info.Events;
import com.app.request.zomato.info.Show;

/**
 * @author Anand
 *
 */
public class CityEvents {

	public static List<Events> getEventDetails(String eventId) throws IOException, XMLStreamException, FactoryConfigurationError, NetworkException {
		List<Events> events = null;
		String urltoHit = Constants.zDetails + eventId;
		InputStream is = Zomato.post(urltoHit,null);
		events = getEventsList(is, true);
		return events;
		
	}

	public static List<Events> getEvents(String cityId, int startResults) throws IOException, XMLStreamException, FactoryConfigurationError, NetworkException {
		
			List<Events> events = null;
		//get the events for the requested city
				String maximumResult = "5";
				String startCount = Integer.toString(startResults);
				HashMap<String, String> params=new HashMap<String, String>();
				params.put("city_id",cityId);
				params.put("start",startCount);
				params.put("count", maximumResult);
				InputStream is = Zomato.post(Constants.zEvents, params);
				events = getEventsList(is,false);
				return events;
	}
	
	/*private static boolean checkforError(InputStream is) throws IOException {
		//Copy Stream
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream bis = null;
		IOUtils.copy(is, out);
		//reset the original stream - Since its a byteArray Input Stream, its marked at 0 by default.
		is.r
		bis = new ByteArrayInputStream(out.toByteArray());
		return response.contains("<status>") ? true : false;
	}*/

	public static List<Events> getEventsList(InputStream response,boolean isDetails) throws XMLStreamException, FactoryConfigurationError{
		Events cityevent = null;
		List<Events> eventsList = new ArrayList<Events>();
		
		Show show = null;
		List<Show> showsList = new ArrayList<Show>();
		
		XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(response);
		XMLEvent event = null;
		while(eventReader.hasNext()){
			event=eventReader.nextEvent();
			switch (event.getEventType()){
				case XMLEvent.START_ELEMENT:
					if(event.asStartElement().getName().getLocalPart().equals("event")){
						cityevent=new Events();
					}
					if (event.asStartElement().getName().getLocalPart().equals("id")) {
			            event = eventReader.nextEvent();
			            cityevent.setId(Integer.parseInt(event.asCharacters().getData()));
			            break;
			        }
					if (event.asStartElement().getName().getLocalPart().equals("name")) {
			            event = eventReader.nextEvent();
			            cityevent.setName(event.asCharacters().getData());
			            break;
			        }
					if (event.asStartElement().getName().getLocalPart().equals("phone_str")) {
			            event = eventReader.nextEvent();
			            cityevent.setPhone(event.asCharacters().getData());
			            break;
			        }
					if (event.asStartElement().getName().getLocalPart().equals("mobile_phone")) {
			            event = eventReader.nextEvent();
			            cityevent.setMobile(event.asCharacters().getData());
			            break;
			        }
					if(isDetails && event.asStartElement().getName().getLocalPart().equals("description")){
						event = eventReader.nextEvent();
						cityevent.setDescription(event.asCharacters().getData());
						break;
					}
					if(isDetails && event.asStartElement().getName().getLocalPart().equals("show")){
						show = new Show();
						break;
					}
					if(isDetails && event.asStartElement().getName().getLocalPart().equals("venue_type")){
						event = eventReader.nextEvent();
						show.setVenuetype(event.asCharacters().getData());
						break;
					}
					if(isDetails && event.asStartElement().getName().getLocalPart().equals("venue_name")){
						event = eventReader.nextEvent();
						show.setVenue(event.asCharacters().getData());
						break;
					}
					if(isDetails && event.asStartElement().getName().getLocalPart().equals("timing_short")){
						event = eventReader.nextEvent();
						show.setShowDate(event.asCharacters().getData());
						break;
					}
					if(isDetails && event.asStartElement().getName().getLocalPart().equals("time")){
						event = eventReader.nextEvent();
						show.setShowTime(event.asCharacters().getData());
						break;
					}
					break;
				
				case XMLEvent.END_ELEMENT:
					
					if(isDetails && event.asEndElement().getName().getLocalPart().equals("show")){
						showsList.add(show);
					}
					
					if(event.asEndElement().getName().getLocalPart().equals("event")){
						cityevent.setShowList(showsList);
						eventsList.add(cityevent);
					}
					
			 }
		}
		return eventsList;
	}

}
