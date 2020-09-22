package com.app.request.zomato;

public class HtmlService {
	final static String applicationURL="/whatzup?txtweb-message=";
	
	String htmlContent=new String();

	public void insertBreak(){
		htmlContent+=Constants.htmlLineBreak;
	}
	
	public void generateLink(String linkToTxtweb, String linkName){
		htmlContent+=generateLinkWithHref(applicationURL+linkToTxtweb, linkName);
	}
	
	public void insertMessage(String message){
		htmlContent+=message;
	}
	
	public static String generateLinkWithHref(String href,String linkName){
		return "<a href=\""+href+"\" class=\"txtweb-menu-for\">"+linkName+"</a>";
	}
	
	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	
}
