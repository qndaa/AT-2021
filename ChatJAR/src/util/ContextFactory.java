package util;


import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

import models.AgentCenter;

public abstract class ContextFactory {
	private static final Logger logger = Logger.getLogger(ContextFactory.class.getName());
	private static Context context;
	private static Context remoteContext;

	/*
	 * java.naming.factory.url.pkgs=org.jboss.ejb.client.naming
	 * java.naming.factory.initial=org.jboss.naming.remote.client.
	 * InitialContextFactory java.naming.provider.url=http-remoting://maja:8080
	 */

	static {
		try {
			Hashtable<String, Object> jndiProps = new Hashtable<>();
			jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			context = new InitialContext(jndiProps);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Context initialization error.", ex);
		}
	}

	public static Context get(AgentCenter remote) {
		if (remote != AgentCenter.LOCAL) {
			try {
				if (remoteContext == null || !remoteContext.getEnvironment().get(Context.PROVIDER_URL).toString()
						.equals("http-remoting://" + remote.getHost() + ":" + remote.getPort())) {
					Hashtable<String, Object> jndiProps = new Hashtable<>();
					jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
					jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,
							"org.jboss.naming.remote.client.InitialContextFactory");
					jndiProps.put(Context.PROVIDER_URL, "http-remoting://" + remote.getHost() + ":" + remote.getPort());
					remoteContext = new InitialContext(jndiProps);
				}
			} catch (Exception e) {
				e.printStackTrace();
				remoteContext = null;
			}
			return remoteContext;
		}
		return context;
	}
}
