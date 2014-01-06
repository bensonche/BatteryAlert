package com.bensonche.batteryalert;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class Email {
	public static void sendEmail(String to, String subject) {
		String url = "http://bensonche.com/email/";
		
		List<NameValuePair> args = new ArrayList<NameValuePair>(2);
		args.add(new BasicNameValuePair("to", to));
		args.add(new BasicNameValuePair("subject", subject));
		
		postUrl(url, args);
	}
	
	private static String getUrl(String url) {
		String reponseString = "";
		
		try {
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(new HttpGet(url));
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				reponseString = out.toString();
			}
			else
			{
				response.getEntity().getContent().close();
			}			
		} catch (Exception e) {
			e.toString();
		}
		
		return reponseString;
	}
	
	private static String postUrl(String url, List<NameValuePair> args) {
		String reponseString = "";
		
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			
			post.setEntity(new UrlEncodedFormEntity(args));
			
			HttpResponse response = client.execute(post); 
			
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				reponseString = out.toString();
			}
			else
			{
				response.getEntity().getContent().close();
			}			
		} catch (Exception e) {
			e.toString();
		}
		
		return reponseString;
	}
}
