package messagemanager;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.swing.tree.AbstractLayoutCache;

import agents.Agent;
import agents.AgentChatInt;
import agents.CachedAgentsRemote;
import agents.CachedChatAgentsRemote;
import models.ACLMessage;
import models.AID;

/**
 * Message-Driven Bean implementation class for: MDBConsumer
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/topic/publicTopic") })
public class MDBConsumer implements MessageListener {


	@EJB
	private CachedChatAgentsRemote cachedChatAgents;
	
	@EJB
	private CachedAgentsRemote cachedAgents;
	
	/**
	 * Default constructor.
	 */
	public MDBConsumer() {

	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			//AgentMessage agentMessage = (AgentMessage) ((ObjectMessage) message).getObject();
			//AgentChatInt agent = cachedAgents.getAgent(agentMessage.getSender());
			//if (agent != null)
				//agent.handleMessage(agentMessage);
			
			ACLMessage m = (ACLMessage) ((ObjectMessage) message).getObject();
			AID aid = m.getReceivers().get(message.getIntProperty("index"));
			
			Agent agent = cachedAgents.getAgent(aid);
			agent.handleMessage(m);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
