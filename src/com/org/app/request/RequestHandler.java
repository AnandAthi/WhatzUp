package com.org.app.request;

import javax.servlet.http.HttpServletRequest;

import com.app.request.EventRequestHandler;
import com.org.app.utils.UtilFunctions;

public abstract class RequestHandler {
	

    protected String responseText;
    protected String mobileNumber;
    protected String userMessage[];

    public RequestHandler()
    {
    }

    public void init(HttpServletRequest request)
    {
        userMessage = getEntireMessage(request);
        mobileNumber = getMobileNumber(request);
    }

    public boolean hasValidParams()
    {
        return true;
    }

    public void doProcess()
        throws Exception
    {
    }

    public static RequestHandler getRequestHandler(HttpServletRequest request, String packageName, String requestHandler)
    {
        return getRequestHandler(getRequestedService(request), packageName, requestHandler);
    }

    public static RequestHandler getRequestHandler(String requestedService, String packageName, String requestHandler)
    {
        RequestHandler handler = null;
        requestedService = UtilFunctions.makeRequestSane(requestedService);
        if(requestedService != null)
        {
            try
            {
                handler = (RequestHandler)Class.forName((new StringBuilder(String.valueOf(packageName))).append(requestedService).append(requestHandler).toString()).newInstance();
            }
            catch(InstantiationException e)
            {
                e.printStackTrace();
            }
            catch(IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch(ClassNotFoundException e)
            {
                //e.printStackTrace();
            	//This is ok if the user is requesting for events
            	handler = new EventRequestHandler();
            }
        }
        if(handler == null)
        {
            handler = new RequestHandler() {

            };
        }
        return handler;
    }

    public static String getRequestedService(HttpServletRequest request)
    {
        String message[] = getEntireMessage(request);
        return UtilFunctions.checkAndGetString(message, 0);
    }

    public static String[] getEntireMessage(HttpServletRequest request)
    {
        String message = UtilFunctions.decode(request.getParameter("txtweb-message"));
        if(UtilFunctions.isNotEmpty(message))
        {
            return message.split(" ");
        } else
        {
            return null;
        }
    }

    public static String getMobileNumber(HttpServletRequest request)
    {
        return request.getParameter("txtweb-mobile");
    }

    public void setResponseText(String responseText)
    {
        this.responseText = responseText;
    }

    public String getResponseText()
    {
        return responseText;
    }

    protected boolean isEmpty(Object object)
    {
        return UtilFunctions.isEmpty(object);
    }

    protected boolean isNotEmpty(Object object)
    {
        return !isEmpty(object);
    }

    protected String getUserMessage(int i)
    {
        return UtilFunctions.checkAndGetString(userMessage, i);
    }

}
