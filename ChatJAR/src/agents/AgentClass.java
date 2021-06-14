package agents;

import java.io.Serializable;

import util.JSON;

public class AgentClass implements Serializable {
	private static final long serialVersionUID = -3054883960108117356L;
	
	public static final char SEPARATOR = '$';
	private final String module;
	private final String ejbName;
	private final String path;
	public  final AgentInitArgs args;

	public AgentClass() {
		this("", "");
	}

	public AgentClass(String module, String ejbName) {
		this(module, ejbName, "");
	}

	public AgentClass(String module, String ejbName, String path) {
		this.module = module;
		this.ejbName = ejbName;
		this.path = path;
		args = null;
	}

	/**
	 * Receives module and ejbName as a single string, separated by the
	 * SEPARATOR constant.
	 *
	 * @param moduleAndEjbName
	 * @throws IllegalArgumentException
	 */
	public AgentClass(String moduleAndEjbName) {
		if (moduleAndEjbName.startsWith("{")) {
			try {
				AgentClass a = JSON.g.fromJson(moduleAndEjbName, AgentClass.class);
				// {"module":"TestAgent","ejbName":"TestAgent","path":""}
				this.module = a.module;
				this.ejbName = a.ejbName;
				this.path = a.path != null ? a.path : "";
				this.args = a.args;
			} catch (Exception ex) {
				throw new IllegalArgumentException(ex);
			}
		} else {
			int n = moduleAndEjbName.indexOf(SEPARATOR);
			if (n <= 0 || n >= moduleAndEjbName.length() - 1) {
				throw new IllegalArgumentException("Expected module" + SEPARATOR + "ejbName.");
			}
			this.module = moduleAndEjbName.substring(0, n);
			this.ejbName = moduleAndEjbName.substring(n + 1);
			this.path = "";
			this.args = null;
		}
	}

	public static <T extends XjafAgent> AgentClass forSiebogEjb(Class<T> clazz) {
		return new AgentClass(Agent.SIEBOG_MODULE, clazz.getSimpleName());
	}


	public String getModule() {
		return module;
	}

	public String getEjbName() {
		return ejbName;
	}

	@Override
	public String toString() {
		return module + SEPARATOR + ejbName;
	}

	public static AgentClass valueOf(String str) {
		int n = str.lastIndexOf(SEPARATOR);
		return new AgentClass(str.substring(0, n), str.substring(n + 1));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ejbName.hashCode();
		result = prime * result + module.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentClass other = (AgentClass) obj;
		return module.equals(other.module) && ejbName.equals(other.ejbName);
	}

	public String getPath() {
		return path;
	}
	
}
