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
	private String str;
	
	public AID() {
		this.str = "";
	}

	
	public AID(String name, String host, String type) {
		this.name = name;
		this.host = host;
		this.agentType = new AgentType(type);
		this.str = name + "@" + host;
	}
	
	public AID(String name, String host, AgentType agentType) {
		this.name = name;
		this.host = host;
		this.agentType = agentType;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentType == null) ? 0 : agentType.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AID other = (AID) obj;
		if (agentType == null) {
			if (other.agentType != null)
				return false;
		} else if (!agentType.equals(other.agentType))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String getStr() {
		return str;
	}
	
	
	
}
