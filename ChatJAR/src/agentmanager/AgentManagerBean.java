package agentmanager;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import agents.Agent;
import agents.AgentChatInt;
import agents.AgentClass;
import agents.CachedAgentsRemote;
import agents.CachedChatAgentsRemote;
import models.AID;
import models.AgentType;
import util.JNDILookup;
import util.JNDITreeParser;
import util.NodeManager;
import ws.WSEndPoint;

/**
 * Session Bean implementation class AgentManagerBean
 */
@Stateless
@LocalBean
@Remote(AgentManagerRemote.class)
public class AgentManagerBean implements AgentManagerRemote {
	
	@EJB
	private CachedChatAgentsRemote cachedChatAgents;
	
	@EJB
	private JNDITreeParser jndiTreeParser;
	
	@EJB
	private CachedAgentsRemote cachedAgents;
	
	
	
    public AgentManagerBean() {
        
    }

	@Override
	public String startChatAgent(String id, String name) {
		AgentChatInt agent = (AgentChatInt) JNDILookup.lookUp(name, AgentChatInt.class);
		return agent.init(id);
	}

	@Override
	public AgentChatInt getAgentById(String agentId) {
		return cachedChatAgents.getRunningAgents().get(agentId);
	}

	@Override
	public void stopAgent(String id) {
		
		cachedChatAgents.stopAgent(id);
	}
	
	@Override
	public List<AgentClass> getAvailableAgentClasses() {
		try {
			//ws.
			return jndiTreeParser.parse();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}


	@Override
	public AID startAgent(String type, String name) {
		List<AgentClass> classes = getAvailableAgentClasses();
		AgentClass agClass = null;
		for (AgentClass ac : classes) {
			if (ac.getEjbName().equals(type)) {
				agClass = ac;
				break;
			}
		}
		
		System.out.println(agClass);
		
		
		if (agClass != null) {
			String host = NodeManager.getNodeName();
			AgentType at = new AgentType(agClass.getEjbName(), agClass.getModule());
			AID aid = new AID(name, host, at);
			
			if (!cachedAgents.containsKey(aid)) {
				Agent agent = null;
			
				String stringForAgent = String.format("ejb:%s//%s!%s?stateful", agClass.getModule(),
						agClass.getEjbName(), Agent.class.getName());
				
				agent = JNDILookup.lookUp(stringForAgent, Agent.class);
			
				
				cachedAgents.put(aid, agent);
				agent.init(aid);
				return aid;
			}
			
		
			return null;
		} else {
			return null;

		}
		
	}

	@Override
	public List<AID> getRunningAgents() {
		return cachedAgents.getKeySet();
	}

	@Override
	public void deleteAgent(AID aid) {
		cachedAgents.deleteAgent(aid);
	}


}
