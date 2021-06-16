package agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import models.AID;
import ws.WSEndPoint;

@Singleton
@LocalBean
@Remote(CachedAgentsRemote.class)
public class CachedAgents implements CachedAgentsRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<AID, Agent> runningAgents = new HashMap<AID, Agent>();
	
	@EJB
	private WSEndPoint ws;

	/**
	 * Default constructor.
	 */
	public CachedAgents() {

		//this.runningAgents = new HashMap<>();
	}

	@Override
	public boolean containsKey(AID aid) {
		return this.runningAgents.containsKey(aid);
	}

	@Override
	public void put(AID aid, Agent agent) {
		this.runningAgents.put(aid, agent);
		System.out.println(runningAgents.size());
		ws.echoTextMessage("RELOAD");
	}

	@Override
	public List<AID> getKeySet() {
		ArrayList<AID> ret = new ArrayList<AID>();

		if (!this.runningAgents.isEmpty()) {
			for (AID aid : this.runningAgents.keySet()) {
				ret.add(aid);
			}
		}
		return ret; 
	}

	@Override
	public void deleteAgent(AID aid) {
		this.runningAgents.remove(aid);
		System.out.println(runningAgents.size());
		ws.echoTextMessage("RELOAD");
	}

	@Override
	public Agent getAgent(AID aid) {
		return this.runningAgents.get(aid);
	}

	
	
	
	
	
	
}
