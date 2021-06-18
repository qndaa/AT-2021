package rest;

import javax.ejb.Remote;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import models.AgentCenter;

@Remote
public interface ClasterRest {

	@POST
	@Path("/node")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addNewNode(AgentCenter ac);
	
	@GET
	@Path("/agents/classes")
	public void getAgentTypeFromNewNode(@Context HttpServletRequest request);
	
	@POST
	@Path("/agents/classes")
	public void sendAgentTypeToAllNodes(@Context HttpServletRequest request);

	@POST
	@Path("/nodes")
	public void sendNodesToNewNode(@Context HttpServletRequest request);
	
	@GET
	@Path("/getHost")
	@Produces(MediaType.TEXT_PLAIN)
	public String getHost();
}
