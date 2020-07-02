package ch.itenengineering.bookshop.client;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.itenengineering.bookshop.domain.Item;

public class BookshopClient {

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

	public void testOrder() throws Exception {

		// TODO
	}

	private void printBasket(Map<String, Item> basket) {
		for (Iterator<String> iter = basket.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Item item = basket.get(key);

			System.out.println("  " + item.toString());
		}
	}

	public static void main(String[] args) {

		try {
			// run the application
			new BookshopClient().testOrder();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

} // end of class

