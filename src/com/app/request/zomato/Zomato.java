package com.app.request.zomato;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import com.app.request.zomato.exception.NetworkException;

public class Zomato {
	public static final String url=Constants.zomatoURL;
	
	private static void setHeaders(URLConnection connection){
		connection.addRequestProperty("X-Zomato-API-Key", Constants.appKey);
	}
	
	protected static InputStream post(String pageName,HashMap<String, String> params) throws IOException, NetworkException{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		HttpURLConnection connection = null;
		try{
		URL req=new URL(url+pageName+"?"+getQueryString(params));
		
		connection =(HttpURLConnection) req.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		//connection.setRequestMethod("GET");
		
		setHeaders(connection);
		connection.setConnectTimeout(10000);
		//Making use of Apache IO Utils
		
		IOUtils.copy(connection.getInputStream(), baos);
		is = new ByteArrayInputStream(baos.toByteArray());
	
		/*BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer responseText=new StringBuffer();
		while ((inputLine = in.readLine()) != null){
			responseText.append(inputLine);
		}*/
		}catch(Exception e){
			e.printStackTrace();
			throw new NetworkException();
		}
		
		finally {
			connection.disconnect();
		}
		return is;
	}
	
	protected static String getQueryString(HashMap<String, String> params){	
		if(params==null){
			return "";
		}
		StringBuffer buffer=new StringBuffer();
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
	    while (it.hasNext()) {
			Map.Entry<String,String> pairs = (Map.Entry<String,String>)it.next();
	        buffer.append(pairs.getKey()+"="+pairs.getValue()+"&");
	    }
	    return buffer.toString();
	}
	
}
