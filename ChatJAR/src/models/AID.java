package models;

import java.io.Serializable;

public class AID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String name;
	private String host;
	private AgentType agentType;
	
	public AID() {}

	
	public AID(String name, String host, String type) {
		this.name = name;
		this.host = host;
		this.agentType = new AgentType(type);
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public AgentType getAgentType() {
		return agentType;
	}
	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}
	
	
	
	
}
