/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.dao;

import java.util.List;

import javax.persistence.EntityManager;

import de.pta.fd.WebServiceSpendTest.dao.interfaces.IAbteilungDao;
import de.pta.fd.WebServiceSpendTest.model.StTabteilung;
import de.pta.fd.WebServiceSpendTest.model.StTumsatz;

/**
 * @author dietricf
 *
 */
public class AbteilungDaoJpa extends GenericDaoJpa<StTabteilung, Long> implements IAbteilungDao  {

	public AbteilungDaoJpa() {
		super();
	}
	
	public AbteilungDaoJpa(final EntityManager entityManager) {
		super( entityManager );
	}

	@Override
	public List<StTumsatz> getUmsaetzeFuerAbteilung() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StTabteilung> getAllAbteilungen() {
		EntityManager em = getEm();
		em.getTransaction().begin();
		
		List<StTabteilung> result =  (List<StTabteilung>)em.createQuery("SELECT T from StTabteilung T").getResultList();

		LOGGER.info("AbteilungList with " + result.size() + " entries found!");
		em.getTransaction().commit();

		return result;
	}

	
}
