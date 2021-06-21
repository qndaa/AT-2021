package agentcentermanager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimerService;

import models.AgentCenter;

@Singleton
@Startup
@Remote(AgentCenterManagerRemote.class)
public class AgentCenterManagerBean implements AgentCenterManagerRemote {

	List<AgentCenter> centers = new ArrayList<AgentCenter>();
	
	@Resource
	TimerService timerService;
	
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

	

	@Override
	public void remove(AgentCenter ac) {
		if (this.centers.contains(ac)) {
			this.centers.remove(ac);		
		}
	}

	
	
}
