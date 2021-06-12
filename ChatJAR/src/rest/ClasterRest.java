package rest;

import javax.ejb.Remote;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Remote
public interface ClasterRest {

	@POST
	@Path("/node")
	public void addNewNode(@Context HttpServletRequest request);
	
	@GET
	@Path("/agents/classes")
	public void getAgentTypeFromNewNode(@Context HttpServletRequest request);
	
	@POST
	@Path("/node")
	public void newNodeForOthers(@Context HttpServletRequest request);
	
	@POST
	@Path("/agents/classes")
	public void sendAgentTypeToAllNodes(@Context HttpServletRequest request);

	@POST
	@Path("/nodes")
	public void sendNodesToNewNode(@Context HttpServletRequest request);
	

}
