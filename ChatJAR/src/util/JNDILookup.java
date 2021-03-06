package util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agentmanager.AgentManagerBean;
import agentmanager.AgentManagerRemote;
import agents.Agent;
import agents.AgentChatInt;
import agents.MasterAgent;
import agents.PredictionAgent;
import agents.SpiderAgent;
import agents.UserAgent;
import chatmanager.ChatManagerBean;
import chatmanager.ChatManagerRemote;
import messagemanager.ChatMessageManagerBean;
import messagemanager.ChatMessageManager;

public abstract class JNDILookup {

	public static final String JNDIPathChat = "ejb:ChatEAR/ChatJAR//";
	public static final String AgentManagerLookup = JNDIPathChat + AgentManagerBean.class.getSimpleName() + "!"
			+ AgentManagerRemote.class.getName();
	public static final String MessageManagerLookup = JNDIPathChat + ChatMessageManagerBean.class.getSimpleName() + "!"
			+ ChatMessageManager.class.getName();
	public static final String UserAgentLookup = JNDIPathChat + UserAgent.class.getSimpleName() + "!"
			+ AgentChatInt.class.getName() + "?stateful";
	public static final String ChatManagerLookup = JNDIPathChat + ChatManagerBean.class.getSimpleName() + "!" +
			ChatManagerRemote.class.getName();
	//public static final String ChatAgentLookup = JNDIPathChat + ChatAgent.class.getSimpleName() + "!"
	//		+ Agent.class.getName() + "?stateful";
	public static final String SpiderAgentLookup = JNDIPathChat + SpiderAgent.class.getSimpleName() + "!" + Agent.class.getName() + "?stateful";
	public static final String PredictionAgentLookup = JNDIPathChat + PredictionAgent.class.getSimpleName() + "!" + Agent.class.getName() + "?stateful";

	public static final String MasterAgentLookup = JNDIPathChat + MasterAgent.class.getSimpleName() + "!" + Agent.class.getName() + "?stateful";


	public static <T> T lookUp(String name, Class<T> c) {
		T bean = null;
		try {
			Context context = new InitialContext();

			System.out.println("Looking up: " + name);
			bean = (T) context.lookup(name);

			context.close();

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
