package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agentmanager.AgentManagerRemote;
import agents.AgentClass;
import messagemanager.MessageManager;
import models.ACLMessage;
import models.AID;
import models.AgentCenter;
import models.Performative;

@Stateless
@LocalBean
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Remote(ATAgentRest.class)
@Path("/agents")
public class ATAgentBeanRest implements ATAgentRest {
	
	@EJB
	AgentManagerRemote agm;

	@EJB
	MessageManager msm;
	
	public ATAgentBeanRest() {
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public List<AgentClass> getAllClasses() {
		return agm.getAvailableAgentClasses();
		
	}

	@Override
	public List<AID> getRunningAgents() {
		return agm.getRunningAgents();
		
	}

	@Override
	public AID runningAgent(String type, String name) {
		
		AID aid = agm.startAgent(type, name);
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://" + AgentCenter.MASTER_ADDRESS + ":8080/ChatWAR/rest/handshake");
		ClasterRest cr = target.proxy(ClasterRest.class);
		cr.reload();
		
		return aid;
	}

	@Override
	public void deleteRunningAgent(AID aid) {
		agm.deleteAgent(aid);
	}

	@Override
	public void sendACLMessage(ACLMessage message) {
		this.msm.post(message);
	}

	@Override
	public List<String> getPerformatives() {
		Performative[] performatives = Performative.values();
		List<String> ret = new ArrayList<String>(performatives.length);
		for (Performative p : performatives) {
			ret.add(p.toString());
		}
		return ret;
	}

	
	
	
}
