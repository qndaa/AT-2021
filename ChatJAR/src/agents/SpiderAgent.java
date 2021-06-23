package agents;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


import javax.ejb.Remote;
import javax.ejb.Stateful;

import game.Game;
import game.Team;
import models.ACLMessage;
import models.Performative;


@Stateful
@Remote(Agent.class)
public class SpiderAgent extends XjafAgent {
	
	
	public List<Game> games = new ArrayList<Game>();


	@Override
	public void handleMessage(ACLMessage msg) {
		if (msg.getPerformative().equals(Performative.REQUEST) && msg.getContent().equals("GAME")) {
			ObjectInputStream in = null;
			Game game = null;
			
			
			for (int i = 0; i < 50; i++) {
				Team team = Team.values()[(int) ((Math.random()*100) % Team.values().length)];
				games.add(new Game(team, (int)(Math.random()*(120-80+1)+80)));
			}
			
			System.out.println(games);
		
			
		}
		
	}


	@Override
	public String toString() {
		return "SpiderAgent [games=" + games + "]";
	}


	@Override
	public List<Game> getGames() {
		// TODO Auto-generated method stub
		return this.games;
	}
	
	

}
