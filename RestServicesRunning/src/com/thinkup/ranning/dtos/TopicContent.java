package com.thinkup.ranning.dtos;

import java.util.HashMap;
import java.util.Map;

public class TopicContent extends Content {

	/**
	 * 
	 */
	private static final long serialVersionUID = -772736724568431441L;
	private String to;
	private Map<String, String> data = new HashMap<String, String>();
	public String getTopic() {
		return to;
	}
	public void setTopic(String to) {
		this.to = to;
	}
	
	public  void setMessage(String message){
		this.data.put("message", message);
	}
	
	public  void setTitle(String message){
		this.data.put("title", message);
	}
	
}
