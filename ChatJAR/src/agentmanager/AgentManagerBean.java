package agentmanager;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import agents.Agent;
import agents.CachedAgentsRemote;
import util.JNDILookup;

/**
 * Session Bean implementation class AgentManagerBean
 */
@Stateless
@LocalBean
public class AgentManagerBean implements AgentManagerRemote {
	
	@EJB
	private CachedAgentsRemote cachedAgents;
	
    public AgentManagerBean() {
        
    }

	@Override
	public String startAgent(String id, String name) {
		Agent agent = (Agent) JNDILookup.lookUp(name, Agent.class);
		return agent.init(id);
	}

	@Override
	public Agent getAgentById(String agentId) {
		return cachedAgents.getRunningAgents().get(agentId);
	}

	@Override
	public void stopAgent(String id) {
		cachedAgents.stopAgent(id);
	}


}
