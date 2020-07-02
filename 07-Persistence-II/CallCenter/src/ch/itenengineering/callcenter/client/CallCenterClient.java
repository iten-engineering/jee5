package ch.itenengineering.callcenter.client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.itenengineering.callcenter.domain.Address;
import ch.itenengineering.callcenter.domain.Call;
import ch.itenengineering.callcenter.domain.Customer;
import ch.itenengineering.callcenter.ejb.CallCenterRemote;

public class CallCenterClient {

	CallCenterRemote cc;

	public CallCenterClient() throws Exception {

		Context ctx = getInitialContext();

		cc = (CallCenterRemote) ctx.lookup("ejb/CallCenter");
	}

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

	public void init() throws Exception {
		int id;
		List<Call> calls;
		Address adr;
		Customer cust;

		// init
		cc.clearAll();

		// customer 1
		id = 1;

		calls = new ArrayList<Call>();
		calls.add(new Call(id, "Bildschirm flimmert", toDate("12.02.2007")));
		calls.add(new Call(id, "Maus defekt", toDate("25.03.2007")));
		calls.add(new Call(id, "Passwort vergessen", toDate("27.05.2007")));

		adr = new Address(id, "Gartenweg", "2a", "3007", "Bern", "Schweiz");

		cust = new Customer(1, "Peter", "Kohler", adr, calls);

		cc.addCustomer(cust);

		// customer 2
		id = 2;

		calls.clear();
		calls.add(new Call(id, "Bildschirm flimmert", toDate("02.07.2007")));

		adr = new Address(id, "Schlosshaldenstrasse", "75", "3005", "Bern",
				"Schweiz");

		cust = new Customer(id, "Sandra", "Schweizer", adr, calls);

		cc.addCustomer(cust);

		// customer 3
		id = 3;

		calls.clear();
		calls.add(new Call(id, "Harddisk defekt", toDate("05.01.2007")));
		calls.add(new Call(id, "Passwort vergessen", toDate("01.03.2007")));
		calls.add(new Call(id, "Zuwenig Berechtigungen", toDate("03.03.2007")));
		calls.add(new Call(id, "Bildschirm flimmert", toDate("02.07.2007")));

		cust = new Customer(id, "Richard", "Gerber", calls);

		cc.addCustomer(cust);
	}

	public void runQueries() throws Exception {

		// get customer (for a given id)
		System.out.println(toString("getCustomer", "1"));
		Object result = cc.getCustomer(1);
		System.out.println(toString(result));

		// Liefert ein Customer Objekt
		getSingleResult("select c from Customer c where c.customerId = 2");

		// Liefert eine List mit Customer Objekten
		getResultList("select c from Customer c");

		// named queries
		getNamedQueryResultList("getCalls");

		// named queries with parameter
		getNamedQueryResultList("getCallsByDate", toDate("02.07.2007"));
	}

	private void getSingleResult(String ejbQuery) throws Exception {

		System.out.println(toString("getSingleResult", ejbQuery));

		Object result = cc.getSingleResult(ejbQuery);

		System.out.println(toString(result));
	}

	private void getResultList(String ejbQuery) throws Exception {

		System.out.println(toString("getResultList", ejbQuery));

		List result = cc.getResultList(ejbQuery);

		System.out.println(toString(result));
	}

	private void getNamedQueryResultList(String queryName) throws Exception {

		System.out.println(toString("getNamedQueryResultList", queryName));

		List result = cc.getNamedQueryResultList(queryName);

		System.out.println(toString(result));
	}

	private void getNamedQueryResultList(String queryName, Date date)
			throws Exception {
		System.out.println(toString("getNamedQueryResultList", queryName));

		List result = cc.getNamedQueryResultList(queryName, date);

		System.out.println(toString(result));
	}

	public String toString(String title, String value) {
		StringBuilder buf = new StringBuilder();

		buf.append("\n");
		buf.append("# \n");
		buf.append("# " + title + ": " + value + " \n");
		buf.append("#");

		return buf.toString();
	}

	public String toString(Object object) {
		if (object instanceof List) {

			return toString((List) object);

		} else if (object instanceof Object[]) {

			return toString((Object[]) object);

		} else {

			return object.toString();
		}
	}

	public String toString(List list) {
		StringBuilder buf = new StringBuilder();

		if (list.isEmpty()) {

			buf.append("empty list (result set)\n");

		} else {

			for (Iterator iter = list.iterator(); iter.hasNext();) {
				buf.append(toString(iter.next()));
				buf.append("\n");
			}

		}

		return buf.toString();
	}

	private String toString(Object[] objects) {

		StringBuilder buf = new StringBuilder();
		boolean first = true;

		for (Object object : objects) {
			if (first) {
				first = false;
			} else {
				buf.append(", ");
			}

			buf.append(object);
		}

		return buf.toString();
	}

	/**
	 * return date object for the given input string in the form "dd.MM.yyyy"
	 * 
	 * @param dateAsString
	 * @return converted String
	 * @throws ParseException
	 */
	private Date toDate(String dateAsString) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		Date convertedDate = null;

		// Invalid dates (e.g. 31.12.2007) must cause a ParseException
		format.setLenient(false);

		if (dateAsString != null && dateAsString.trim().length() > 0) {
			convertedDate = format.parse(dateAsString);
		}

		return convertedDate;
	}

	public static void main(String[] args) {

		try {
			// run the application
			CallCenterClient client = new CallCenterClient();

			client.init();
			client.runQueries();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

} // end of class
