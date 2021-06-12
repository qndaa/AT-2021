package models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


public class ACLMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Performative performative;
	
	private AID sender;
	
	private List<AID> receivers;
	
	private AID replyTo;
	
	private String content;
	
	private Object contentObj;
	
	private HashMap<String, Serializable> userArg;
	
	private String language;
	
	private String encoding;
	
	private String ontology;
	
	private String protocol;
	
	private String conversationId;
	
	private String replyWith;
	
	public String inReplyTo;	
	
	public long replyBy;
	
	

	
}
