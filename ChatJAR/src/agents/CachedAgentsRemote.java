package agents;


import java.util.List;

import models.AID;

public interface CachedAgentsRemote {

	boolean containsKey(AID aid);

	void put(AID aid, Agent agent);

	List<AID> getKeySet();

	void deleteAgent(AID aid);

}
