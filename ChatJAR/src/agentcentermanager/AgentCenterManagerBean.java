package agentcentermanager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;

import models.AgentCenter;
import util.NodeManager;

@Singleton
@Startup
@Remote(AgentCenterManagerRemote.class)
public class AgentCenterManagerBean implements AgentCenterManagerRemote {

	List<AgentCenter> centers = new ArrayList<AgentCenter>();
	
	 public AgentCenterManagerBean() {}
	
	@PostConstruct
	public void init() {
		
	}

	
	
	@Override
	public List<AgentCenter> getAgentCenters() {
		// TODO Auto-generated method stub
		return this.centers;
	}

	@Override
	public AgentCenter addNode(AgentCenter ac) {
		this.centers.add(ac);
		return ac;
	}
	
}
