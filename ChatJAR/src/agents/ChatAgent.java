package agents;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import console.Console;
import models.ACLMessage;
import util.JSON;

@Stateful
@Remote(Agent.class)
@LocalBean
public class ChatAgent extends XjafAgent{
	
	@EJB
	Console console;

	@Override
	public void handleMessage(ACLMessage msg) {
		System.out.println("Stigla poruka!");
		console.echoTextMessage(JSON.g.toJson(msg).toString());
	}

}
