package messagemanager;

import java.util.List;

import javax.ejb.Remote;

import models.ACLMessage;

@Remote
public interface MessageManager {

	List<String> getPerformatives();
	void post(ACLMessage message);
}
