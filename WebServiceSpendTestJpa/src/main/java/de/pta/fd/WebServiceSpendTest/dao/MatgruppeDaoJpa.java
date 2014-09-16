/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.dao;


import java.util.List;

import javax.persistence.EntityManager;

import de.pta.fd.WebServiceSpendTest.dao.interfaces.IMatGruppeDao;
import de.pta.fd.WebServiceSpendTest.model.StTmatgruppe;


/**
 * @author dietricf
 *
 */
public class MatgruppeDaoJpa extends GenericDaoJpa<StTmatgruppe, Long> implements IMatGruppeDao 
{

	public MatgruppeDaoJpa() {
		super();
	}
	
	
	/**
	 * 
	 * Set EntityManager from View (OSIV).
	 * 
	 * @param entityManager - EntityManager.
	 */
	public MatgruppeDaoJpa(final EntityManager entityManager) {
		
		super(entityManager);
	}
	
	/* (non-Javadoc)
	 * @see de.pta.fd.dao.interfaces.IStandortDao#getAllStandorte()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<StTmatgruppe> GetMaterialgruppenListe( Long ParentId ) {
		EntityManager em = getEm();
		
		em.getTransaction().begin();
		String query = "from StTmatgruppe";
		if( ParentId != null ) {
			query += " where ID is not null and FK_PARENT = " + ParentId.toString();
		} else {
			query +=  " WHERE FK_PARENT is NULL ";
		}
		
		
		query += " order by Matcode";
	
				
		List<StTmatgruppe> result =  (List<StTmatgruppe>)em.createQuery(query).getResultList();
	
		LOGGER.info("MatgruppenList with " + result.size() + " entries found!");
		em.getTransaction().commit();
	
		return result;
	}

}
