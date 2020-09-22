package com.app.request.zomato;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.app.request.zomato.exception.CityNotFoundException;
import com.app.request.zomato.exception.NetworkException;
import com.app.request.zomato.info.CityInformation;

public class SearchRestaurant extends Zomato {
	
	public static List<Result> searchRestaurant(String cityName,String restaurantName) throws IOException, XMLStreamException, CityNotFoundException, NetworkException{
		//Changes start here, since the response is converted to ByteInputStream
		//Lets skip the other conversion process and have it as ByteArrayInputStream itself
		int cityID=Integer.parseInt(CityInformation.getCityID(cityName));
		InputStream response=getRestaurant(cityID, restaurantName);
		return parseResponseXML(response);
	}
	
	public static List<Result> searchRestaurant(String cityName,String restaurantName,String locality) throws IOException, XMLStreamException, CityNotFoundException, NetworkException{
		//Same here
		int cityID=Integer.parseInt(CityInformation.getCityID(cityName));
		InputStream response=getRestaurant(cityID, restaurantName, locality);
		List<Result> list=new ArrayList<Result>();
		for(Result result:parseResponseXML(response)){
			if(locality.equalsIgnoreCase(result.getLocality())){
				list.add(result);
			}
		}
		return list;
	}
	
	public static InputStream getRestaurant(int cityID, String restaurantName) throws IOException, NetworkException{
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("city_id", cityID+"");
		params.put("q", restaurantName);
		return post(Constants.zSearch, params);
	}
	
	public static InputStream getRestaurant(int cityID, String restaurantName, String locality) throws IOException, NetworkException{
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("city_id", cityID+"");
		params.put("q", restaurantName);
		params.put("qaddress", locality);
		return post(Constants.zSearch, params);
	}
	
	public static List<Result> parseResponseXML(InputStream response) throws XMLStreamException{
		List<Result> items = new ArrayList<Result>();
		//XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(new ByteArrayInputStream(response.getBytes()));
		XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(response);
		Result result=null;
		while(eventReader.hasNext()){
			XMLEvent event=eventReader.nextEvent();
			switch (event.getEventType()){
			case XMLEvent.START_ELEMENT:
				if(event.asStartElement().getName().getLocalPart().equals("result")){
					result=new Result();
				}
				if (event.asStartElement().getName().getLocalPart().equals("name")) {
		            event = eventReader.nextEvent();
		            result.setName(event.asCharacters().getData());
		            break;
		        }
				if(event.asStartElement().getName().getLocalPart().equals("address")){
					event = eventReader.nextEvent();
		            result.setAddress(event.asCharacters().getData());
		            break;
				}
				if(event.asStartElement().getName().getLocalPart().equals("locality")){
					event = eventReader.nextEvent();
		            result.setLocality(event.asCharacters().getData());
		            break;
				}
				break;
			case XMLEvent.END_ELEMENT:
				if(event.asEndElement().getName().getLocalPart().equals("result")){
					items.add(result);
				}
				break;				
			}
		}
		return items;
	}
}
