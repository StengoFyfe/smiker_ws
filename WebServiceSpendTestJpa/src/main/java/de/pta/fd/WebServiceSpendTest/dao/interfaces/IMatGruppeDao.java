/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.dao.interfaces;

import de.pta.fd.WebServiceSpendTest.model.StTmatgruppe;

import java.util.List;

/**
 * @author dietricf
 *
 */
public interface IMatGruppeDao extends IGenericDao<StTmatgruppe, Long>{ 
	/**
	 * @ParentId can be null
	 * 
	 * @return - Liste welche den kompletten Materialgruppen-Baum enthält
	 */
	List<StTmatgruppe> GetMaterialgruppenListe( Long ParentId );

}
