package ch.itenengineering.company.client;

import java.rmi.ServerException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.itenengineering.company.domain.Company;
import ch.itenengineering.company.ejb.CompanyManagerRemote;

public class CompanyClient {

	private Context getInitialContext() throws NamingException {

		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY,
			"org.jnp.interfaces.NamingContextFactory");
		env.put(Context.URL_PKG_PREFIXES,
			"org.jboss.naming;org.jnp.interfaces");
		env.put(Context.PROVIDER_URL, "jnp://localhost:1099");

		return new InitialContext(env);
	}

	/**
	 * test Create, Read, Update and Delete operations
	 * 
	 * @throws ServerException
	 */
	public void testCRUD() throws Exception {

		// init
		Context ctx = getInitialContext();

		// TODO
	}


	private void printCompanies(List list) {
		if ((list == null) || (list.isEmpty())) {
			System.out.println("no companies found (empty list)");
		} else {
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Company element = (Company) iter.next();
				System.out.println(element.toString());
			}
		}

	}

	public static void main(String[] args) {

		try {
			// configure log4j (used by jboss classes)
			// BasicConfigurator.configure();

			// run the application
			CompanyClient c = new CompanyClient();

			c.testCRUD();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

} // end of class
