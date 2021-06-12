package rest;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;

@Stateless
@LocalBean
@Path("/agents")
public class ATAgentBeanRest implements ATAgentRest {

	@Override
	public void getAllClasses(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getRunningAgents(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runningAgent(HttpServletRequest request, String type, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRunningAgent(HttpServletRequest request, String aid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendACLMessage(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMessages(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
