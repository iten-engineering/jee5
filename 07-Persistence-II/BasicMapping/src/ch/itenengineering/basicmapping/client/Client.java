package ch.itenengineering.basicmapping.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.itenengineering.basicmapping.domain.Company;
import ch.itenengineering.basicmapping.ejb.ManagerRemote;

public class Client {

	private Context getInitialContext() throws NamingException {

		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		env
				.put(Context.URL_PKG_PREFIXES,
						"org.jboss.naming;org.jnp.interfaces");
		env.put(Context.PROVIDER_URL, "jnp://localhost:1099");

		return new InitialContext(env);
	}

	public void test() throws Exception {

		int companyId = 1;

		// init
		Context ctx = getInitialContext();
		ManagerRemote manager = (ManagerRemote) ctx
				.lookup("ejb/BasicMappingManager");

		// delete existing data (if available)
		manager.remove(Company.class, companyId);

		// add company
		Company company = new Company(companyId);
		manager.persist(company);

		// find and show company
		System.out.println("find and show company:");
		System.out.println(manager.find(Company.class, companyId));
	}

	public static void main(String[] args) {

		try {
			// run the application
			new Client().test();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

} // end of class
