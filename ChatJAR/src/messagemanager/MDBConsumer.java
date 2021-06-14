package messagemanager;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import agents.AgentChatInt;
import agents.CachedChatAgentsRemote;

/**
 * Message-Driven Bean implementation class for: MDBConsumer
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/topic/publicTopic") })
public class MDBConsumer implements MessageListener {


	@EJB
	private CachedChatAgentsRemote cachedAgents;
	
	
	
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
			AgentMessage agentMessage = (AgentMessage) ((ObjectMessage) message).getObject();
			AgentChatInt agent = cachedAgents.getAgent(agentMessage.getSender());
			if (agent != null)
				agent.handleMessage(agentMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
