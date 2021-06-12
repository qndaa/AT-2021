package rest;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;

@Stateless
@LocalBean
@Path("/")
public class ClasterBeanRest implements ClasterRest{

	@Override
	public void addNewNode(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAgentTypeFromNewNode(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newNodeForOthers(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendAgentTypeToAllNodes(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNodesToNewNode(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
