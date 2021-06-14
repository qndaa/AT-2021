package agents;

import models.AID;

public abstract class XjafAgent implements Agent {
	
	private AID aid;
	
	
	@Override
	public void init(AID aid) {
		this.aid = aid;
	}

	@Override
	public AID getAID() {
		return this.aid;
	}
}
