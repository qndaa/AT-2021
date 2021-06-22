package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agents.AgentClass;
import models.ACLMessage;
import models.AID;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ATAgentRest {
	
	@GET
	@Path("/classes")
	public List<AgentClass> getAllClasses();
	
	@GET
	@Path("/running")
	public List<AID> getRunningAgents();
	
	@PUT
	@Path("/running/{type}/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AID runningAgent( @PathParam("type") String type, @PathParam("name") String name);
	
	@DELETE
	@Path("/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteRunningAgent(AID aid);
	
	@POST
	@Path("/messages")
	public void sendACLMessage(ACLMessage message);
	
	@GET
	@Path("/messages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPerformatives();
	

}
