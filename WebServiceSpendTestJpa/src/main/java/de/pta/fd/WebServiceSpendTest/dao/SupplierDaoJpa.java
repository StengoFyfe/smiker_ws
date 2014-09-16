/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.dao;

import java.util.List;








import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.type.StandardBasicTypes;

import de.pta.fd.WebServiceSpendTest.dao.interfaces.ISupplierDao;
import de.pta.fd.WebServiceSpendTest.model.StTsupplier;
import de.pta.fd.WebServiceSpendTest.model.additionalBeans.CountResult;

/**
 * @author dietricf
 *
 */
public class SupplierDaoJpa extends GenericDaoJpa<StTsupplier, Long> implements ISupplierDao 
{

	public SupplierDaoJpa() {
		super();
	}
	
	
	/**
	 * 
	 * Set EntityManager from View (OSIV).
	 * 
	 * @param entityManager - EntityManager.
	 */
	public SupplierDaoJpa(final EntityManager entityManager) {
		
		super(entityManager);
	}
	
	/* (non-Javadoc)
	 * @see de.pta.fd.dao.interfaces.IStandortDao#getAllStandorte()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<StTsupplier> GetAllSuppliers() {
		EntityManager em = null;
		em = getEm();
		em.getTransaction().begin();
		LOGGER.info("GetAllSuppliers() uses EM: " + getEm().toString());
		String query = "from StTsupplier";
		query += " order by Firma";

				
		List<StTsupplier> result =  (List<StTsupplier>)getEm().createQuery(query).getResultList();

		LOGGER.info("SupplierList with " + result.size() + " entries found!");
		em.getTransaction().commit();
		
		return result;
	}



	@Override
	public boolean SupplierExists( String NewName, String KredID ) {
		boolean ret;
		
		EntityManager em = getEm();
		
		try {
			getEm().getTransaction().begin();
			String query = "SELECT COUNT(*) as Count from ST_TSUPPLIER WHERE UPPER( FIRMA ) = :firm or KRED_ID = :kred_id";
			Query qu = getEm().createNativeQuery(query);
			qu.setParameter("firm", NewName.toUpperCase());
			qu.setParameter("kred_id", KredID);
			java.math.BigDecimal o  = (java.math.BigDecimal)qu.getSingleResult();
			
			if( o != null && o.longValue() > 0)
				ret = true;
			else
				ret = false;
	//		if( result != null)
	//			LOGGER.info("Found Supplier");
			
			em.getTransaction().commit();
			
			
			return ret;
		}
		finally {
			if( em != null && em.isOpen())
				em.close();
		}
	}
		

}
