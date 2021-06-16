package messagemanager;

import java.util.List;

import models.ACLMessage;

public interface MessageManager {

	List<String> getPerformatives();
	void post(ACLMessage message);
}
