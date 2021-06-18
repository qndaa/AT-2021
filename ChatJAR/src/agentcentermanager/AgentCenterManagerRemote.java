package agentcentermanager;

import java.util.List;

import models.AgentCenter;

public interface AgentCenterManagerRemote {

	public List<AgentCenter> getAgentCenters();

	public AgentCenter addNode(AgentCenter ac);

}
