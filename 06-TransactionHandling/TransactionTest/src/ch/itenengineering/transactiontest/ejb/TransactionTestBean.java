package ch.itenengineering.transactiontest.ejb;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.ejb3.annotation.RemoteBinding;

import ch.itenengineering.transactiontest.domain.TransactionTest;

@Stateful
@RemoteBinding(jndiBinding = "ejb/TransactionTest")
public class TransactionTestBean implements TransactionTestRemote {

	@PersistenceContext(unitName = "TransactionTestPu")
	private EntityManager em;

	public boolean isBeanAlive() {
		return true;
	}

	public void testAppException() throws Exception {
		em.persist(new TransactionTest("testAppException, commit expected"));
		throw new AppException();
	}

} // end of class
