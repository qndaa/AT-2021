package rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agentmanager.AgentManagerRemote;
import agents.AgentClass;
import messagemanager.MessageManager;
import models.ACLMessage;
import models.AID;

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
		return agm.startAgent(type, name);
	}

	@Override
	public void deleteRunningAgent(AID aid) {
		agm.deleteAgent(aid);
	}

	@Override
	public void sendACLMessage(ACLMessage message) {
		msm.post(message);
	}

	@Override
	public List<String> getPerformatives() {
		return msm.getPerformatives();
	}

	
	
	
}
