package agentmanager;

import java.util.List;

import javax.ejb.Remote;

import agents.Agent;
import agents.AgentChatInt;
import agents.AgentClass;
import models.AID;

@Remote
public interface AgentManagerRemote {
	public String startChatAgent(String id, String name);
	public AgentChatInt getAgentById(String agentId);
	public void stopAgent(String id);
	public List<AgentClass> getAvailableAgentClasses();
	public AID startAgent(String type, String name);
	public List<AID> getRunningAgents();
	public void deleteAgent(AID aid);
	public Agent getAgent(AID aid);
}
