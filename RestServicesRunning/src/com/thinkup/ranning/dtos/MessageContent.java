package com.thinkup.ranning.dtos;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.http.client.utils.URLEncodedUtils;
import org.codehaus.jackson.annotate.JsonIgnore;



public class MessageContent extends Content{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -3900621253796674492L;
	private List<String> registration_ids = new Vector<>();
	 private Map<String,String> data = new HashMap<>();
	 private Map<String,String> notification = new HashMap<>();

	 
	 public MessageContent() {
		super();
		this.notification = new HashMap<String, String>();
		this.notification.put("icon", "new");
		this.data = new HashMap<String, String>();
		this.registration_ids = new Vector<String>();
	}

	public void addDestinatario(String token){
		 this.registration_ids.add(token);
	 }

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public void putData(String key, String value) {
		data.put(key, value);
	}


	public void setTitle(String title){
		this.notification.put("title", title);
		
	}
	
	public void setBody(String body){
		try {
			this.notification.put("body", URLEncoder.encode(body, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			this.notification.put("body",body);
		}
	}

	public void addData(String key, String idCarrera) {
		this.data.put(key, idCarrera);
		
	}
	 
	 
}
