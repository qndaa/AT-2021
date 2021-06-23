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
		
		
		List<Game> data = new ArrayList<Game>();
		
		if (NodeManager.getNodeName().equals(AgentCenter.MASTER_NODE)) {
			for (AgentCenter ac: this.acmr.getAgentCenters()) {
				ResteasyWebTarget target = client.target("http://" + ac.getHost() + ":8080/ChatWAR/rest/games");
				GameRest gr = target.proxy(GameRest.class);
				
				List<Game> ret = gr.startSpider();
				
				if (ret != null) {
					data.addAll(ret);
				}
				
			}	
			
			System.out.println(data);

			
			
			
			Agent master = JNDILookup.lookUp(JNDILookup.MasterAgentLookup, Agent.class);
			ACLMessage message = new ACLMessage();
			message.setPerformative(Performative.INFORM);
			message.setContent("GAME: " + team1 + " VS " + team2 + " WINNER IS: ");
			master.handleMessage(message);
			
			
		}

	}

	@Override
	public List<Game> startSpider() {
		
		System.out.println("Starting spider...");
		
		Agent agent = JNDILookup.lookUp(JNDILookup.SpiderAgentLookup, Agent.class);
			
		ACLMessage message = new ACLMessage();
		message.setReceivers(new ArrayList<AID>());
		message.setPerformative(Performative.REQUEST);
		message.setContent("GAME");
		agent.handleMessage(message);
		
		return agent.getGames();			
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
