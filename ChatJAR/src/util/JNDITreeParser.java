package util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

import agents.Agent;
import agents.AgentClass;

@Stateless
@LocalBean
public class JNDITreeParser {
	private static final String INTF = "!" + Agent.class.getName();
	private static final String EXP = "java:jboss/exported/";
	private Context context;
	
	@PostConstruct
	public void postConstruct() {
		context = ContextFactory.get(null);
	}
	
	
	public List<AgentClass> parse() throws Exception {
		List<AgentClass> result = new ArrayList<>();
		NamingEnumeration<NameClassPair> moduleList = context.list(EXP);
		//System.out.println(moduleList.hasMoreElements());
		while (moduleList.hasMore()) {
			NameClassPair ncp = moduleList.next();
			String module = ncp.getName();
			//System.out.println(module);
			processModule("", module, result);
		}
		return result;
	}

	private void processModule(String parentModule, String module, List<AgentClass> result) throws Exception {
		//System.out.println(parentModule + "----->" + module);
		NamingEnumeration<NameClassPair> agentList;
		if (parentModule.equals("")) {
			agentList = context.list(EXP + "/" + module);
		} else {
			try {
				agentList = context.list(EXP + "/" + parentModule + "/" + module);
			} catch (Exception ex) {
				return;
			}
		}
		
		while (agentList.hasMore()) {
			NameClassPair ncp = agentList.next();
			String ejbName = ncp.getName();
			//System.out.println(ejbName);
			if (ejbName.contains("!")) {
				AgentClass agClass = parseEjbNameIfValid(parentModule, module, ejbName);
				if (agClass != null) {
					result.add(agClass);
				}
			} else {
				// perhaps a nested module (jar inside ear)?
				processModule(module, ejbName, result);
			}
		}
	}

	private AgentClass parseEjbNameIfValid(String parentModule, String module, String ejbName) {
		if (ejbName != null && ejbName.endsWith(INTF)) {
			return parseEjbName(parentModule, module, ejbName);
		}
		return null;
	}

	private AgentClass parseEjbName(String parentModule, String module, String ejbName) {
		ejbName = extractAgentName(ejbName);
		String path;
		if (parentModule.equals("")) {
			path = String.format("/%s/agents/xjaf", module);
			return new AgentClass(module, ejbName, path);
		} else {
			path = String.format("/%s/%s/agents/xjaf", parentModule, module);
			return new AgentClass(parentModule + "/" + module, ejbName, path);
		}
		
	}

	private String extractAgentName(String ejbName) {
		int n = ejbName.lastIndexOf(INTF);
		return ejbName.substring(0, n);
	}
	
	
	
}
