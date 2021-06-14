package models;

import java.io.Serializable;

import javax.ejb.Singleton;

@Singleton
public class AgentCenter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final AgentCenter LOCAL = null;

	private String host;
	private String port;
	
	public AgentCenter() {
		
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	
	
}
