package agents;

import java.io.Serializable;
import messagemanager.AgentMessage;
import models.AID;

public interface AgentChatInt extends Serializable {

	public String init(String id);
	public void handleMessage(AgentMessage message);
	public String getAgentId();
}
