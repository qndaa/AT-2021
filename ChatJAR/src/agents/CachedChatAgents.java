package agents;

import java.util.HashMap;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class CachedAgents
 */
@Singleton
@LocalBean
@Remote(CachedChatAgentsRemote.class)
public class CachedChatAgents implements CachedChatAgentsRemote{

	HashMap<String, AgentChatInt> runningAgents;

	/**
	 * Default constructor.
	 */
	public CachedChatAgents() {

		runningAgents = new HashMap<>();
	}

	@Override
	public HashMap<String, AgentChatInt> getRunningAgents() {
		return runningAgents;
	}

	@Override
	public void addRunningAgent(String key, AgentChatInt agent) {
		runningAgents.put(key, agent);
	}

	@Override
	public void stopAgent(String id) {
		runningAgents.remove(id);
	}

	@Override
	public AgentChatInt getAgent(String id) {
		
		return this.runningAgents.get(id);
	}
	
	

}
