/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.dao.interfaces;

import de.pta.fd.WebServiceSpendTest.model.StTsupplier;

import java.util.List;


/**
 * @author dietricf
 *
 */
public interface ISupplierDao extends IGenericDao<StTsupplier, Long>{

	/**
	 * @return Liefere alle Lieferanten zurück
	 */
	List<StTsupplier> GetAllSuppliers();
	boolean SupplierExists( String NewName, String KredID );

}
