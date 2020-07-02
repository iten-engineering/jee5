package ch.itenengineering.env.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.RemoteBinding;

@Stateless
@RemoteBinding(jndiBinding = "ejb/Env")
public class EnvBean implements EnvRemote {

	private String messageA;

	private String messageB;

	public String getMessages() {
		return "TODO";
	}
}
