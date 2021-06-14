package messagemanager;

import java.util.List;

import javax.jms.MessageConsumer;
import javax.jms.Session;


public interface ChatMessageManager {
	public void post(AgentMessage msg);
	public Session getSession();
	public MessageConsumer getConsumer();
	
	// AT
	public List<String> getPerformatives();
}
