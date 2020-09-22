package com.org.app.request;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.org.app.utils.UtilFunctions;

public class ApplicationServlet extends HttpServlet {
	
	 private static final long serialVersionUID = 1L;
	    String applicationName;
	    String defaultMsg;
	    String errorMsg;
	    String handlerPackage;
	    String handlerSuffix;
	    String applicationKey;

	    public void init(ServletConfig config)
	        throws ServletException
	    {
	        super.init(config);
	        applicationName = UtilFunctions.checkAndGetEmpty(config.getInitParameter("ApplicationName"));
	        defaultMsg = UtilFunctions.checkAndGetEmpty(config.getInitParameter("DefaultMessage"));
	        errorMsg = UtilFunctions.checkAndGetEmpty(config.getInitParameter("ErrorMessage"));
	        handlerPackage = UtilFunctions.checkAndGetEmpty(config.getInitParameter("HandlerPackage"));
	        handlerSuffix = UtilFunctions.checkAndGetEmpty(config.getInitParameter("HandlerSuffix"));
	        applicationKey = UtilFunctions.checkAndGetEmpty(config.getInitParameter("ApplicationKey"));
	    }

	    public ApplicationServlet()
	    {
	    }

	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	    {
	        doPost(request, response);
	    }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	    {
	        System.out.println((new StringBuilder("Application: ")).append(applicationName).toString());
	        System.out.println((new StringBuilder("Default: ")).append(defaultMsg).toString());
	        System.out.println((new StringBuilder("Error: ")).append(errorMsg).toString());
	        System.out.println((new StringBuilder("Handler: ")).append(handlerPackage).toString());
	        System.out.println((new StringBuilder("Suffix: ")).append(handlerSuffix).toString());
	        StringBuffer buffer = new StringBuffer();
	        buffer.append("<html>").append("<head>").append("<title>").append(applicationName).append("</title>").append("<meta http-equiv='Content-Type' content='text/html'; charset='UTF-8'>").append((new StringBuilder("<meta name='txtweb-appkey' content='")).append(applicationKey).append("'").toString()).append("/>").append("</head>").append("<body>");
	        String responseText = defaultMsg;
	        try
	        {
	            RequestHandler handler = RequestHandler.getRequestHandler(request, handlerPackage, handlerSuffix);
	            System.out.println((new StringBuilder("HandlerFound: ")).append(handler.getClass().getName()).toString());
	            handler.setResponseText(defaultMsg);
	            System.out.println("Calling handler.init");
	            handler.init(request);
	            System.out.println("Calling handler.hasValidParams");
	            if(handler.hasValidParams())
	            {
	                System.out.println("Calling handler.doProcess");
	                handler.doProcess();
	            }
	            System.out.println("Calling handler.getResponseText");
	            responseText = handler.getResponseText();
	        }
	        catch(Exception e)
	        {
	            System.out.println((new StringBuilder("Error Occured: ")).append(e.getMessage()).toString());
	            e.printStackTrace();
	            responseText = errorMsg;
	        }
	        buffer.append(responseText).append("</body>").append("</html>");
	        response.getWriter().print(buffer);
	    }

}
