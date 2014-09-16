/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.dao;

import java.util.List;

import javax.persistence.EntityManager;

import de.pta.fd.WebServiceSpendTest.dao.interfaces.IStandortDao;
import de.pta.fd.WebServiceSpendTest.model.StTstandort;


/**
 * @author dietricf
 * 
 * Controller Klasse für alle Standort Zugriffe
 *
 */
public class StandortDaoJpa extends GenericDaoJpa<StTstandort, Long> implements
		IStandortDao {

	/**
	 * Standard constructor
	 */
	public StandortDaoJpa() {
		super();
	}
	
	/**
	 * 
	 * Set EntityManager from View (OSIV).
	 * 
	 * @param entityManager - EntityManager.
	 */
	public StandortDaoJpa(final EntityManager entityManager) {
		
		super(entityManager);
	}
	

	/* (non-Javadoc)
	 * @see de.pta.fd.dao.interfaces.IStandortDao#getAllStandorte()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<StTstandort> getAllStandorte() {
		EntityManager em = getEm();
		em.getTransaction().begin();
				
		List<StTstandort> result =  (List<StTstandort>)em.createQuery("SELECT T from StTstandort T").getResultList();

		LOGGER.info("StandortList with " + result.size() + " entries found!");
		em.getTransaction().commit();

		return result;
	}
	
	

}
