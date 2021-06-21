package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agentcentermanager.AgentCenterManagerRemote;
import agentmanager.AgentManagerRemote;
import agents.AgentClass;
import console.Console;
import models.AgentCenter;
import util.JSON;
import util.NodeManager;


@Path("/handshake")
@Remote(ClasterRest.class)
public class ClasterBeanRest implements ClasterRest{
	
	@EJB
	AgentCenterManagerRemote agentCenterManager;
//	
	//private List <AgentCenter> centers = new ArrayList<AgentCenter>();
	
	
	@EJB
	private Console console;
	
	@EJB
	AgentManagerRemote agentManager;
	
	@EJB
	private ATAgentRest at;
	
	@Override
	public void addNewNode(AgentCenter ac) {
		
		System.out.println(NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE));

		ResteasyClient client = new ResteasyClientBuilder().build();

		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			System.out.println(JSON.g.toJson(ac));
			System.out.println(agentCenterManager.getAgentCenters());
			if (!agentCenterManager.getAgentCenters().contains(ac)) {
				agentCenterManager.addNode(ac);
				System.out.println("CENTRI:");
				System.out.println(agentCenterManager.getAgentCenters());	
				console.echoTextMessage("New Agents center" + JSON.g.toJson(ac));
				
				if (ac.getHost().equals(AgentCenter.MASTER_ADDRESS)) {
					return;
				}
				
				
				ResteasyWebTarget target = client.target("http://" + ac.getHost() + ":8080/ChatWAR/rest/handshake");
				ClasterRest cr = target.proxy(ClasterRest.class);
				List<AgentClass> ret = cr.getAgentTypeFromNewNode();
				//cr.sendClasses(agentManager.getAvailableAgentClasses());
				console.echoTextMessage("New Agents center classes: " + JSON.g.toJson(ret));

				for (AgentCenter a: this.agentCenterManager.getAgentCenters()) {
					if (!a.equals(ac)) {
						 target = client.target("http://" + a.getHost() + ":8080/ChatWAR/rest/handshake");
						 cr = target.proxy(ClasterRest.class);
						 cr.sayOtherNodes(ac);
					}
					 
				}
				
				
				
			}
			
		} else {
			
			ResteasyWebTarget rtarget = client.target("http://" + AgentCenter.MASTER_ADDRESS + ":8080/ChatWAR/rest/handshake");
			ClasterRest cr = rtarget.proxy(ClasterRest.class);
			cr.addNewNode(ac);
			
		}
	
	}

	@Override
	public List<AgentClass> getAgentTypeFromNewNode() {
		return at.getAllClasses();
		
	}


	@Override
	public void sayOtherNodes(AgentCenter ac) {
		console.echoTextMessage("New Agents center in network: ADDRESS: " + ac.getHost() + " PORT:" + ac.getPort());	
	}



	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return NodeManager.getNodeName();
		
	}

	

	@Override
	public void sendClasses(List<AgentClass> classes) {
		console.echoTextMessage("CLASSES from master node: " + JSON.g.toJson(classes));
		
	}

}
