package agents;

import java.util.List;

import javax.ejb.Remote;

import game.Game;
import models.ACLMessage;
import models.AID;

@Remote
public interface Agent {
	
	String SIEBOG_MODULE = "siebog-jar";
	String SIEBOG_EAR = "siebog-ear";
	String SIEBOG_WAR = "siebog-war";

	public void init(AID aid);
	public void handleMessage(ACLMessage msg);
	public AID getAID();
	public List<Game> getGames();
	public String getWinner();
}
