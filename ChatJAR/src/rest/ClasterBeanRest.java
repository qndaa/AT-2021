package rest;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
import agents.AgentClass;
import console.Console;
import models.AgentCenter;
import util.JSON;
import util.NodeManager;

@Singleton
@Path("/handshake")
@Remote(ClasterRest.class)
public class ClasterBeanRest implements ClasterRest{
	
//	@EJB
//	AgentCenterManagerRemote agentCenterManager;
//	
	private List <AgentCenter> centers = new ArrayList<AgentCenter>();
	
	
	@EJB
	private Console console;
	
	@EJB
	private ATAgentRest at;
	
	@Override
	public void addNewNode(AgentCenter ac) {
		
		System.out.println(NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE));

		ResteasyClient client = new ResteasyClientBuilder().build();

		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			if (!centers.contains(ac)) {
				centers.add(ac);
				System.out.println("CENTRI:");
				System.out.println(centers);	
				console.echoTextMessage("New Agents center" + JSON.g.toJson(ac));
				
				ResteasyWebTarget target = client.target("http://" + ac.getHost() + ":8080/ChatWAR/rest/handshake");
				ClasterRest cr = target.proxy(ClasterRest.class);
				List<AgentClass> ret = cr.getAgentTypeFromNewNode();
				console.echoTextMessage("New Agents center classes: " + JSON.g.toJson(ret));

				for (AgentCenter a: this.centers) {
					if (!a.equals(ac)) {
						 target = client.target("http://" + a.getHost() + ":8080/ChatWAR/rest/handshake");
						 cr = target.proxy(ClasterRest.class);
						 cr.sendAgentTypeToAllNodes(ac);
					}
					 
				}
				
				
			}
			
		} else {
			
			ResteasyWebTarget rtarget = client.target("http://" + AgentCenter.MASTER_NODE + ":8080/ChatWAR/rest/handshake");
			ClasterRest cr = rtarget.proxy(ClasterRest.class);
			cr.addNewNode(ac);
			
		}
	
	}

	@Override
	public List<AgentClass> getAgentTypeFromNewNode() {
		return at.getAllClasses();
	}


	@Override
	public void sendAgentTypeToAllNodes(AgentCenter ac) {
		console.echoTextMessage("New Agents center in network: ADDRESS: " + ac.getHost() + " PORT:" + ac.getPort());	
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
