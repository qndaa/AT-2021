package agents;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import models.ACLMessage;



@Stateful
@Remote(Agent.class)
@LocalBean
public class PredictionAgent extends XjafAgent {

	@Override
	public void handleMessage(ACLMessage msg) {
		// TODO Auto-generated method stub
		
	}

}