/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.dao.interfaces;

import de.pta.fd.WebServiceSpendTest.model.StTstandort;

import java.util.List;



/**
 * @author dietricf
 *
 */
public interface IStandortDao extends IGenericDao<StTstandort, Long>{
	
	
	/**
	 * @return - Liste mit allen Standorten
	 */
	List<StTstandort> getAllStandorte();

}
