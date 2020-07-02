package ch.itenengineering.mex.ejb;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.RemoteBinding;

import ch.itenengineering.mex.domain.Bonus;
import ch.itenengineering.mex.domain.CurrencyType;
import ch.itenengineering.mex.domain.Rate;

@Stateless
@RemoteBinding(jndiBinding = "ejb/MoneyExchange")
public class MoneyExchangeBean implements MoneyExchangeRemote {

	@PersistenceContext(unitName = "MoneyExchangePu")
	private EntityManager em;

	@Resource
	SessionContext ctx;

	public double getRate(CurrencyType from, CurrencyType to) {

		// log pricipal name to system output
		// TODO

		// get rate
		double rate = searchRate(from, to);

		// calculate bonus rate vor VIP's
		// TODO

		return rate;
	}

	public void setRates(List<Rate> rates) {

		em.createQuery("delete from Rate").executeUpdate();

		for (Rate rate : rates) {
			em.persist(rate);
		}
	}

	public int getVIPBonus() {

		Bonus bonus = em.find(Bonus.class, "VIP");
		int result = 5; // default vip bonus in percent

		if (bonus != null) {
			result = bonus.getPercent();
		}

		return result;
	}

	public void setVIPBonus(int percent) {
		Bonus bonus = new Bonus("VIP", percent);

		if (em.find(Bonus.class, bonus.getRole()) == null) {
			em.persist(bonus);
		} else {
			em.merge(bonus);
		}
	}

	private double searchRate(CurrencyType from, CurrencyType to) {

		Rate rate;
		double result;
		Query query = em
				.createQuery("select r from Rate r where r.from=:from and r.to=:to");

		try {

			// search from/to
			query.setParameter("from", from);
			query.setParameter("to", to);
			rate = (Rate) query.getSingleResult();

			result = rate.getRate();

		} catch (NoResultException e) {

			// search to/from and swap rate
			query.setParameter("from", to);
			query.setParameter("to", from);
			rate = (Rate) query.getSingleResult();

			result = 1 / rate.getRate();
		}

		return result;
	}

} // end of class
