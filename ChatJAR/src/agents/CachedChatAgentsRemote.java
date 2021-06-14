package agents;

import java.util.HashMap;

public interface CachedChatAgentsRemote {

	public HashMap<String, AgentChatInt> getRunningAgents();
	public void addRunningAgent(String key, AgentChatInt agent);
	public void stopAgent(String id);
	public AgentChatInt getAgent(String id);
}
