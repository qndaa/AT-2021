package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agentcentermanager.AgentCenterManagerRemote;
import agentmanager.AgentManagerRemote;
import agents.Agent;
import agents.CachedAgentsRemote;
import agents.SpiderAgent;
import game.Game;
import game.GameManager;
import messagemanager.MessageManager;
import models.ACLMessage;
import models.AID;
import models.AgentCenter;
import models.Performative;
import util.JNDILookup;
import util.JSON;
import util.NodeManager;

@Stateless
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
	
	@EJB
	CachedAgentsRemote car;
	
	public GameRestBean() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void getResult(String team1, String team2) {
		gm.reset();

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget rtarget = client.target("http://" + AgentCenter.MASTER_ADDRESS + ":8080/ChatWAR/rest/handshake");
		ClasterRest cr = rtarget.proxy(ClasterRest.class);
		cr.addNewNode(new AgentCenter(AgentCenter.MASTER_ADDRESS, 8080));
		
		List<AID> aids = new ArrayList<AID>();
		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			for (AgentCenter ac: this.acmr.getAgentCenters()) {
				ResteasyWebTarget target = client.target("http://" + ac.getHost() + ":8080/ChatWAR/rest/games");
				GameRest gr = target.proxy(GameRest.class);
//				AID aid = gr.runningAgent("SpiderAgent", "GAME@"+ ac.getHost());
//				System.out.println(aid);
//				aids.add(aid);
				
				List<Game> ret = gr.startSpider();
				
				
				System.out.println(ret);
				
				
				
				
			}	
//			ACLMessage message = new ACLMessage();
//			message.setReceivers(aids);
//			message.setPerformative(Performative.REQUEST);
//			message.setContent("GAME");
//			cr.sendACLMessage(message);
//			
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//			for (AgentCenter ac: this.acmr.getAgentCenters()) {
//		
//			
//				
//				ResteasyClient c = new ResteasyClientBuilder().build();
//				ResteasyWebTarget tar = c.target("http://" + AgentCenter.MASTER_ADDRESS + ":8080/ChatWAR/rest/games");
//				GameRest r = tar.proxy(GameRest.class);
//				
//				for (AID a: aids) {
//					List<Game> ret = r.getData(a);
//					System.out.println(ret);
//
//				}
//			}
			
		}

	}

	@Override
	public List<Game> startSpider() {
		
		System.out.println("Starting spider...");
		
		Agent agent = JNDILookup.lookUp(JNDILookup.SpiderAgentLookup, Agent.class);
		
		
		
		
		
		
//		AID aidSpider = amr.startAgent("SpiderAgent", "Game");
//		
		ACLMessage message = new ACLMessage();
		message.setReceivers(new ArrayList<AID>());
		message.setPerformative(Performative.REQUEST);
		message.setContent("GAME");
		agent.handleMessage(message);
		
		return agent.getGames();
//		//System.out.println(JSON.g.toJson(message));
//		msm.post(message);
//		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(gm.getGames());
//		
//		if (!NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
//			
//			ResteasyClient c = new ResteasyClientBuilder().build();
//			ResteasyWebTarget tar = c.target("http://" + AgentCenter.MASTER_ADDRESS + ":8080/ChatWAR/rest/games");
//			GameRest r = tar.proxy(GameRest.class);
//			r.saveSearch(gm.getGames());
//		
//		} 
		
		
			
	}
	
	@Override
	public void startPrediction() {
		// TODO Auto-generated method stub
		AID aid = amr.startAgent("PredicitonAgent", "GamePrediction");
	}
	
	

	@Override
	public void saveSearch(List<Game> games) {
		System.out.println("DJOLEEEEEE" + games.toString());
		
		
		
		
		//gm.concanate(games);
	}

	@Override
	public List<Game> getData(AID aid) {
		
		System.out.println(car.getSpiderAgents());
				//		if (car.containsKey(aid)) {
//			SpiderAgent sa = (SpiderAgent) car.getAgent(aid);
//			return sa.games;
//		}
//		
//		return new ArrayList<Game>();
		
		return null;
	}

	

}
