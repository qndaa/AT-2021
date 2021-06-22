package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agentcentermanager.AgentCenterManagerRemote;
import agentmanager.AgentManagerRemote;
import agents.SpiderAgent;
import game.Game;
import game.GameManager;
import messagemanager.MessageManager;
import models.ACLMessage;
import models.AID;
import models.AgentCenter;
import models.Performative;
import util.JSON;
import util.NodeManager;

@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Remote(GameRest.class)
@Path("/games")
public class GameRestBean implements GameRest {

	
	@EJB
	AgentCenterManagerRemote acmr;
	
	@EJB
	AgentManagerRemote amr;
	
	@EJB
	MessageManager msm;
	
	@EJB
	GameManager gm;
	
	@Override
	public void getResult(String team1, String team2) {
		gm.reset();

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget rtarget = client.target("http://" + AgentCenter.MASTER_ADDRESS + ":8080/ChatWAR/rest/handshake");
		ClasterRest cr = rtarget.proxy(ClasterRest.class);
		cr.addNewNode(new AgentCenter(AgentCenter.MASTER_ADDRESS, 8080));
		
		
		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			for (AgentCenter ac: this.acmr.getAgentCenters()) {
				ResteasyWebTarget target = client.target("http://" + ac.getHost() + ":8080/ChatWAR/rest/games");
				GameRest gr = target.proxy(GameRest.class);
				gr.startSpider();
			}
		
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println("DJOLELEEEEE" + gm.getGames());

			
		
		}

	}

	@Override
	public void startSpider() {
		AID aidSpider = amr.startAgent("SpiderAgent", "Game");
		
		ACLMessage message = new ACLMessage();
		message.setReceivers(new ArrayList<AID>());
		message.getReceivers().add(aidSpider);
		message.setPerformative(Performative.REQUEST);
		message.setContent("GAME");
		//System.out.println(JSON.g.toJson(message));
		msm.post(message);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(gm.getGames());
		
		if (!NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			
			ResteasyClient c = new ResteasyClientBuilder().build();
			ResteasyWebTarget tar = c.target("http://" + AgentCenter.MASTER_ADDRESS + ":8080/ChatWAR/rest/games");
			GameRest r = tar.proxy(GameRest.class);
			r.saveSearch(gm.getGames());
		
		} 
		
		
			
	}
	
	@Override
	public void startPrediction() {
		// TODO Auto-generated method stub
		AID aid = amr.startAgent("PredicitonAgent", "GamePrediction");
	}
	
	

	@Override
	public void saveSearch(List<Game> games) {
		//System.out.println("DJOLEEEEEE" + games.toString());
		gm.concanate(games);
	}

	

}
