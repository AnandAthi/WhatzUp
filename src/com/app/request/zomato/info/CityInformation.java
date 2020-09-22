package com.app.request.zomato.info;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.app.request.zomato.Constants;
import com.app.request.zomato.Zomato;
import com.app.request.zomato.exception.CityNotFoundException;
import com.app.request.zomato.exception.LocalityNotFoundException;
import com.app.request.zomato.exception.NetworkException;

public class CityInformation extends Zomato{
	
	private static List<City> cities=new ArrayList<City>();
	/*@SuppressWarnings("deprecation")
	private static Date lastLoggedDate=new Date(2010, 1, 1);*/
	
	static{
		refresh();
	}
	
	public static void refresh(){
		try {
			System.out.println("Refreshing City Information.");
			cities=parseResponseCityXML(post(Constants.zCities, null));
			for(City city:cities){
				HashMap<String, String> params=new HashMap<String, String>();
				params.put("city_id", city.getId());
				List<SubZone> zones=parseResponseSubZoneXML(post(Constants.zSubzones,params));
				city.setSubZones(zones);
			}
			//lastLoggedDate=new Date();
			System.out.println("Refreshed Successfully.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NetworkException ne){
			ne.printStackTrace();
		}
	}

	public static List<City> parseResponseCityXML(InputStream response) throws XMLStreamException{
		List<City> items = new ArrayList<City>();
		//XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(new ByteArrayInputStream(response.getBytes()));
		XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(response);
		City city=null;
		while(eventReader.hasNext()){
			XMLEvent event=eventReader.nextEvent();
			switch (event.getEventType()){
			case XMLEvent.START_ELEMENT:
				if(event.asStartElement().getName().getLocalPart().equals("city")){
					city=new City();
				}
				if (event.asStartElement().getName().getLocalPart().equals("id")) {
		            event = eventReader.nextEvent();
		            city.setId(event.asCharacters().getData());
		            break;
		        }
				if(event.asStartElement().getName().getLocalPart().equals("name")){
					event = eventReader.nextEvent();
		            city.setName(event.asCharacters().getData());
		            break;
				}
				if(event.asStartElement().getName().getLocalPart().equals("country_id")){
					event = eventReader.nextEvent();
		            city.setCountryID(event.asCharacters().getData());
		            break;
				}
				break;
			case XMLEvent.END_ELEMENT:
				if(event.asEndElement().getName().getLocalPart().equals("city")){
					items.add(city);
				}
				break;				
			}
		}
		return items;
	}
	
	public static List<SubZone> parseResponseSubZoneXML(InputStream response) throws XMLStreamException{
		List<SubZone> items = new ArrayList<SubZone>();
		XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(response);
		SubZone subzone=null;
		while(eventReader.hasNext()){
			XMLEvent event=eventReader.nextEvent();
			switch (event.getEventType()){
			case XMLEvent.START_ELEMENT:
				if(event.asStartElement().getName().getLocalPart().equals("subzone")){
					subzone=new SubZone();
				}
				if (event.asStartElement().getName().getLocalPart().equals("subzone_id")) {
		            event = eventReader.nextEvent();
		            subzone.setSubzoneID(event.asCharacters().getData());
		            break;
		        }
				if(event.asStartElement().getName().getLocalPart().equals("zone_id")){
					event = eventReader.nextEvent();
		            subzone.setZoneID(event.asCharacters().getData());
		            break;
				}
				if(event.asStartElement().getName().getLocalPart().equals("name")){
					event = eventReader.nextEvent();
		            subzone.setName(event.asCharacters().getData());
		            break;
				}
				break;
			case XMLEvent.END_ELEMENT:
				if(event.asEndElement().getName().getLocalPart().equals("subzone")){
					items.add(subzone);
				}
				break;				
			}
		}
		return items;
	}
	
	public static List<City> getCities() {
		/*if(lastLoggedDate.before(new Date(Calendar.getInstance()))){
			refresh();
		}*/
		return cities;
	}
	
	public static String getCityID(String cityName) throws CityNotFoundException{
		return getCity(cityName).getId();
	}
	
	public static City getCity(String cityName) throws CityNotFoundException{
		
		for(City city:CityInformation.getCities()){
			if(city.getName().equalsIgnoreCase(cityName)){
				return city;
			}
		}
		throw new CityNotFoundException();
	}
	
	public static SubZone getSubzone(String cityName, String localityName) throws CityNotFoundException, LocalityNotFoundException{
		City city=getCity(cityName);
		for(SubZone zone:city.getSubZones()){
			if(zone.getName().equalsIgnoreCase(localityName)){
				return zone;
			}
		}
		throw new LocalityNotFoundException();
	}
	
	public static String getSubZoneID(String cityName,String localityName) throws CityNotFoundException, LocalityNotFoundException{
		return getSubzone(cityName, localityName).getSubzoneID();
	}
}
