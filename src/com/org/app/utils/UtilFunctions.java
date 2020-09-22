package com.org.app.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UtilFunctions {

	public UtilFunctions()
    {
    }

    public static int random(int bound)
    {
        int randNo = 0;
        Double doubleStr = Double.valueOf(Math.random());
        String randomStr = new String(doubleStr.toString());
        for(int i = 0; i < randomStr.length(); i++)
        {
            char c = randomStr.charAt(i);
            if(c != '.')
            {
                randNo += c;
            }
        }

        randNo %= bound;
        return randNo;
    }

    public static boolean isEmpty(Object object)
    {
        if(object == null)
        {
            return true;
        }
        return (object instanceof String) && ((String)object).length() == 0;
    }

    public static boolean isNotEmpty(Object object)
    {
        return !isEmpty(object);
    }

    public static String capitalizeInitial(String value)
    {
        return value == null ? null : (new StringBuilder(String.valueOf(value.substring(0, 1).toUpperCase()))).append(value.substring(1)).toString();
    }
    
    public static String makeRequestSane(String value){
    	StringBuffer buff = new StringBuffer();
    	if(value != null) {
    	buff.append(value.toLowerCase());
		String firstChar = buff.substring(0, 1);
		buff.replace(0, 1, firstChar.toUpperCase());
		}
		return buff.toString();
	}

    public static String decode(String value)
    {
        try {
			return value == null ? null : URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
    }

    public static String encode(String value)
    {
        try {
			return value == null ? null : URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

    public static String checkAndGetString(String array[], int indexToBeLooked)
    {
        if(isNotEmpty(array) && array.length > indexToBeLooked)
        {
            return array[indexToBeLooked];
        } else
        {
            return null;
        }
    }

    public static String checkAndGetEmpty(String value)
    {
        if(isEmpty(value))
        {
            value = new String("");
        }
        return value;
    }
}
