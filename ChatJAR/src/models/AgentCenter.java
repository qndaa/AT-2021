package models;

import java.io.Serializable;

import javax.ejb.Singleton;


public class AgentCenter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final AgentCenter LOCAL = null;
	
	public static final String MASTER_NODE = "qndaa-ideapad-5-14iil05";
	public static final String MASTER_ADDRESS = "192.168.0.110";

	private String host;
	private int port;
	
	public AgentCenter() {
		
	}
	
	public AgentCenter(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentCenter other = (AgentCenter) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
	
	
	
	
	
}
