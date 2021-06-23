package agents;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import game.Game;
import models.ACLMessage;



@Stateful
@Remote(Agent.class)
@LocalBean
public class PredictionAgent extends XjafAgent {

	@Override
	public void handleMessage(ACLMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Game> getGames() {
		// TODO Auto-generated method stub
		return null;
	}

}
