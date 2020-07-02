package ch.itenengineering.transactiontest.ejb;

import javax.ejb.Remote;

@Remote
public interface TransactionTestRemote {

	public boolean isBeanAlive();

	public void testAppException() throws Exception;

}
