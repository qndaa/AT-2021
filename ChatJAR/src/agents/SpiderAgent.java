package agents;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import game.Game;
import game.GameManager;
import game.Team;
import models.ACLMessage;
import models.AgentCenter;
import models.Performative;
import rest.GameRest;
import util.NodeManager;


@Stateful
@Remote(Agent.class)
public class SpiderAgent extends XjafAgent {
	
	
	@EJB
	GameManager gm;

	@Override
	public void handleMessage(ACLMessage msg) {
		if (msg.getPerformative().equals(Performative.REQUEST) && msg.getContent().equals("GAME")) {
			ObjectInputStream in = null;
			Game game = null;
			List<Game> games = new ArrayList<Game>();
			
			
			for (int i = 0; i < 50; i++) {
				Team team = Team.values()[(int) ((Math.random()*100) % Team.values().length)];
				games.add(new Game(team, (int)(Math.random()*(120-80+1)+80)));
			}
			
			System.out.println(games);
			
			if (!NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget rtarget = client.target("http://" + AgentCenter.MASTER_ADDRESS + ":8080/ChatWAR/rest/games");
				GameRest cr = rtarget.proxy(GameRest.class);
				cr.saveSearch(games);
			
			} else {
				gm.concanate(games);
			}
			
		}
		
	}

}
