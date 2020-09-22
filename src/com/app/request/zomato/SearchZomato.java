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
import com.org.app.utils.UtilFunctions;

public abstract class SearchZomato extends Zomato {
	
	protected String cityName="",restaurantName="",locality="";
	protected String cityID;

	public SearchZomato(String cityName) throws CityNotFoundException{
		this.cityName=cityName;
		cityID=CityInformation.getCityID(cityName);
	}
	
	public List<Result> search() throws XMLStreamException, IOException, NetworkException{
		HashMap<String, String> params=new HashMap<String, String>();
		addParam(params, "city_id", cityID);
		addParam(params, "q", restaurantName);
		addParam(params, "qaddress", locality);
		return parseResponseXML(post(Constants.zSearch,params));
	}
	
	public List<Result> parseResponseXML(InputStream response) throws XMLStreamException{
		List<Result> items = new ArrayList<Result>();
		XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(response);
		Result result=null;
		while(eventReader.hasNext()){
			XMLEvent event=eventReader.nextEvent();
			switch (event.getEventType()){
			case XMLEvent.START_ELEMENT:
				if(event.asStartElement().getName().getLocalPart().equals("result")){
					result=new Result();
				}else if (event.asStartElement().getName().getLocalPart().equals("name")) {
					event = eventReader.nextEvent();
					String value=event.asCharacters().getData();
	            	result.setName(value);
		        }else if(event.asStartElement().getName().getLocalPart().equals("address")){
					event = eventReader.nextEvent();
		            result.setAddress(event.asCharacters().getData());
				}else if(event.asStartElement().getName().getLocalPart().equals("locality")){
					event = eventReader.nextEvent();
					String value=event.asCharacters().getData();
	            	result.setLocality(value);
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

	protected static void addParam(HashMap<String,String> map, String key, String value){
		if(UtilFunctions.isNotEmpty(value)&&UtilFunctions.isNotEmpty(map)){
			map.put(key, value);
		}
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}