package agents;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import console.Console;
import game.Game;
import models.ACLMessage;
import models.Performative;

@Stateful
@Remote(Agent.class)
@LocalBean
public class MasterAgent extends XjafAgent {
	
	@EJB
	Console console;
	

	@Override
	public void handleMessage(ACLMessage msg) {

		if (msg.getPerformative().equals(Performative.INFORM)) {
			console.echoTextMessage(msg.getContent());
		}
	}

	@Override
	public List<Game> getGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

}
