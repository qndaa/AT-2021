package rest;

import javax.ejb.Remote;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Remote
public interface ATAgentRest {
	
	@GET
	@Path("/classes")
	public void getAllClasses(@Context HttpServletRequest request);
	
	@GET
	@Path("/running")
	public void getRunningAgents(@Context HttpServletRequest request);
	
	@PUT
	@Path("/running/{type}/{name}")
	public void runningAgent(@Context HttpServletRequest request, @PathParam("type") String type, @PathParam("name") String name);
	
	@DELETE
	@Path("/running/{aid}")
	public void deleteRunningAgent(@Context HttpServletRequest request, @PathParam("aid") String aid);
	
	@POST
	@Path("/messages")
	public void sendACLMessage(@Context HttpServletRequest request);
	
	@GET
	@Path("/messages")
	public void getMessages(@Context HttpServletRequest request);
	

}
