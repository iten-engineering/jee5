package ch.itenengineering.naming.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.itenengineering.naming.ejb.DescriptorNamingRemote;

public class NamingClient {

	private Context getInitialContext() throws NamingException {

		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY,
			"org.jnp.interfaces.NamingContextFactory");
		env.put(Context.URL_PKG_PREFIXES,
			"org.jboss.naming;org.jnp.interfaces");
		env.put(Context.PROVIDER_URL, "jnp://localhost:1099");

		return new InitialContext(env);
	}

	public void testLookups() throws NamingException {

		// get initial context
		Context ctx = getInitialContext();

		// test lookups
		DescriptorNamingRemote descNaming = (DescriptorNamingRemote) ctx.lookup("ejb/DescriptorNaming");
		System.out.println( descNaming.echo("test3")  );
	}


	public static void main(String[] args) {

		try {

			// run the application
			new NamingClient().testLookups();

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

} // end of class
