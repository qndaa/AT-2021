package messagemanager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import agents.CachedAgentsRemote;
import models.ACLMessage;
import models.AID;
import models.Performative;

@Stateless
@Remote(MessageManager.class)
@LocalBean
public class MessageManagerBean implements MessageManager{
	
	@EJB
	private JMSFactory factory;

	private Session session;
	private MessageProducer defaultProducer;
	
	@EJB
	CachedAgentsRemote car;
	
	@PostConstruct
	public void postConstruct() {
		session = factory.getSession();
		defaultProducer = factory.getProducer(session);
	}

	@PreDestroy
	public void preDestroy() {
		try {
			session.close();
		} catch (JMSException e) {
		}
	}
	
	
	
	@Override
	public List<String> getPerformatives() {
		Performative[] performatives = Performative.values();
		List<String> ret = new ArrayList<String>(performatives.length);
		for (Performative p : performatives) {
			ret.add(p.toString());
		}
		return ret;
	}

	@Override
	public void post(ACLMessage message) {
		for (int i = 0; i < message.getReceivers().size(); i++) {
			if(message.getReceivers().get(i) != null && car.containsKey(message.getReceivers().get(i))) {
				AID aid = message.getReceivers().get(i);
				try {
					ObjectMessage om = session.createObjectMessage(message);
					
					om.setIntProperty("index", i);
					defaultProducer.send(om);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
	}

	
	
	
	
	
}
