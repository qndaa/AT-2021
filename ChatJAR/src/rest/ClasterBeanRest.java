package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agentcentermanager.AgentCenterManagerRemote;
import agentmanager.AgentManagerRemote;
import agents.AgentClass;
import console.Console;
import messagemanager.MessageManager;
import models.ACLMessage;
import models.AID;
import models.AgentCenter;
import util.JSON;
import util.NodeManager;

@Stateless
@Path("/handshake")
@Remote(ClasterRest.class)
public class ClasterBeanRest implements ClasterRest{
	
	@EJB
	AgentCenterManagerRemote agentCenterManager;
//	
	//private List <AgentCenter> centers = new ArrayList<AgentCenter>();
	@EJB
	MessageManager msm;
	
	@EJB
	Console console;
	
	@EJB
	AgentManagerRemote agentManager;
	
	@EJB
	ATAgentRest at;
	
	public ClasterBeanRest() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void addNewNode(AgentCenter ac) {
		
		System.out.println(NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE));

		ResteasyClient client = new ResteasyClientBuilder().build();

		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			
			try {
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
					cr.sendClasses(agentManager.getAvailableAgentClasses());
					cr.sendRunningAgent(agentManager.getRunningAgents());
					console.echoTextMessage("New Agents center classes: " + JSON.g.toJson(ret));

					for (AgentCenter a: this.agentCenterManager.getAgentCenters()) {
						if (!a.equals(ac)) {
							 target = client.target("http://" + a.getHost() + ":8080/ChatWAR/rest/handshake");
							 cr = target.proxy(ClasterRest.class);
							 cr.sayOtherNodes(ac);
						}
						 
					}
					
					
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("ERROR...");
				System.out.println("CALLBACK...");
				this.agentCenterManager.remove(ac);
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

	@Override
	public String ping() {
		// TODO Auto-generated method stub
		return "OK";
	}
	
	@Schedule(hour = "*", minute = "*", second = "*/10")
	public void pingNodes() {
		// TODO Auto-generated method stub
		System.out.println("START PING...");
		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			
			for (AgentCenter ac: this.agentCenterManager.getAgentCenters()) {
				System.out.println("PING ---> " + ac.getHost());
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget target = client.target("http://" + ac.getHost() + ":8080/ChatWAR/rest/handshake");
				ClasterRest cr = target.proxy(ClasterRest.class);
				
				try {
					String ret = cr.ping();
					if (ret.equals("OK")) {
						System.out.println("PING OK!");
					}
				} catch (Exception e) {
					System.out.println("Address not working first time... " + ac.getHost() );
					try {
						String ret = cr.ping();
						if (ret.equals("OK")) {
							System.out.println("PING OK!");
						}
					} catch (Exception e1) {
						System.out.println("Address not working second time... " + ac.getHost() );
						this.agentCenterManager.remove(ac);
						System.out.println("Node removed!");
					}
						
				}
				
			}
			
		}
		
		
	}

	@Override
	public void sendRunningAgent(List<AID> runningAgents) {
		// TODO Auto-generated method stub
		console.echoTextMessage("Started agents on master: " + runningAgents);
	}
	
	
	@Override
	public List<AID> getRunningAgents() {
		List<AID> ret = new ArrayList<AID>();
		ResteasyClient client = new ResteasyClientBuilder().build();

		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			for(AgentCenter ac: this.agentCenterManager.getAgentCenters()) {
				ResteasyWebTarget target = client.target("http://" + ac.getHost() + ":8080/ChatWAR/rest/agents");
				ATAgentRest cr = target.proxy(ATAgentRest.class);
				ret.addAll(cr.getRunningAgents());
			}
			
		} else {
			ResteasyWebTarget target = client.target("http://" + AgentCenter.MASTER_ADDRESS + ":8080/ChatWAR/rest/handshake");
			ClasterRest cr = target.proxy(ClasterRest.class);
			ret = cr.getRunningAgents();
		}
		
		
		return ret;
	}

	@Override
	public void reload() {
		System.out.println("OVDJE SMO");
		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			ResteasyClient client = new ResteasyClientBuilder().build();
			for (AgentCenter ac : agentCenterManager.getAgentCenters()) {
				if (!ac.getHost().equals(AgentCenter.MASTER_ADDRESS)) {
					ResteasyWebTarget target = client.target("http://" + ac.getHost() + ":8080/ChatWAR/rest/handshake");
					ClasterRest cr = target.proxy(ClasterRest.class);
					cr.reload();
				} else {
					console.echoTextMessage("RELOAD");
				}
				
			}
			
		} else {
			console.echoTextMessage("RELOAD");
		}
		
	}

	@Override
	public void sendACLMessage(ACLMessage message) {
		ResteasyClient client = new ResteasyClientBuilder().build();

		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			for(AgentCenter ac: this.agentCenterManager.getAgentCenters()) {
				ResteasyWebTarget target = client.target("http://" + ac.getHost() + ":8080/ChatWAR/rest/agents");
				ATAgentRest cr = target.proxy(ATAgentRest.class);
				cr.sendACLMessage(message);
			}
			
		} else {
			ResteasyWebTarget target = client.target("http://" + AgentCenter.MASTER_ADDRESS + ":8080/ChatWAR/rest/handshake");
			ClasterRest cr = target.proxy(ClasterRest.class);
			cr.sendACLMessage(message);
		}
	}
	

}
