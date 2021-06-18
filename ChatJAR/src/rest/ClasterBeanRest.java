package rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.resource.spi.ConnectionManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agentcentermanager.AgentCenterManagerRemote;
import models.AgentCenter;
import util.NodeManager;

@Singleton
@Startup
@Path("/handshake")
@Remote(ClasterRest.class)
public class ClasterBeanRest implements ClasterRest{
	
//	@EJB
//	AgentCenterManagerRemote agentCenterManager;
//	
	private List <AgentCenter> centers = new ArrayList<AgentCenter>();
	
	
	@PostConstruct
	private void init() {
		
//		if (NodeManager.getNodeName() == AgentCenter.MASTER_NODE) {
//			System.out.println("UUUUUUUUUUUUUU master cvoru smo");
//			centers.add(new AgentCenter(NodeManager.getNodeName(), 8080));
//		}
		
		
		
	}
 
	@Override
	public void addNewNode(AgentCenter ac) {
//		AgentCenter agentCenter = agentCenterManager.addNode(ac);
//		List<AgentCenter> list = agentCenterManager.getAgentCenters();
//
//		System.out.println(ac.getHost());
//		System.out.println(list);
//		for(AgentCenter a: list) {
//			System.out.println(a.getHost());
//		}
		
		System.err.println(AgentCenter.MASTER_NODE);
		System.out.println(NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE));

		
		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			if (!centers.contains(ac)) {
				centers.add(ac);
				System.out.println("CENTRI:");
				System.out.println(centers);	
			}
			
		} else {
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget rtarget = client.target("http://" + AgentCenter.MASTER_NODE + ":8080/ChatWAR/rest/handshake/node");
			ClasterRest cr = rtarget.proxy(ClasterRest.class);
			cr.addNewNode(ac);
		}
		
	}

	@Override
	public void getAgentTypeFromNewNode(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void sendAgentTypeToAllNodes(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNodesToNewNode(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return NodeManager.getNodeName();
		
	}

}
