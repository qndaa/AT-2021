package rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import agents.AgentClass;
import models.ACLMessage;
import models.AID;
import models.AgentCenter;


public interface ClasterRest {

	@POST
	@Path("/node")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addNewNode(AgentCenter ac);
	
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public  List<AgentClass> getAgentTypeFromNewNode();
	
	
	
	@POST
	@Path("/nodes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sayOtherNodes(AgentCenter ac);
	
	@POST
	@Path("/agents/classes")
	@Consumes(value =  MediaType.APPLICATION_JSON)
	public void sendClasses(List<AgentClass> classes);


	
	@GET
	@Path("/getHost")
	@Produces(MediaType.TEXT_PLAIN)
	public String getHost();
	
	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	public String ping();

	@POST
	@Path("/agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendRunningAgent(List<AID> runningAgents);
	
	@GET
	@Path("/agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<AID> getRunningAgents();

	
	@GET
	@Path("/reload")
	public void reload();
	
	@POST
	@Path("/messages")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void sendACLMessage(ACLMessage message);
	
	
	
	
	
}
