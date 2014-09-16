package de.pta.fd.WebServiceSpendTest.dao.interfaces;

import de.pta.fd.WebServiceSpendTest.model.StTabteilung;
import de.pta.fd.WebServiceSpendTest.model.StTumsatz;

import java.util.List;
/**
 * @author dietricf
 *
 */
public interface IAbteilungDao extends IGenericDao<StTabteilung, Long> {
	
	/**
	 * @return Liste aller Umsätze, welche zu der Abteilung gehören
	 */
	List<StTumsatz> getUmsaetzeFuerAbteilung();
	List<StTabteilung> getAllAbteilungen();
	

}
