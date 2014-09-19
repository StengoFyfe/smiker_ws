/**
 * 
 */


import java.util.List;

import org.jboss.logging.Logger;
import org.junit.Ignore;

import de.pta.fd.WebServiceSpendTest.dao.MatgruppeDaoJpa;
import de.pta.fd.WebServiceSpendTest.dao.StandortDaoJpa;
import de.pta.fd.WebServiceSpendTest.dao.SupplierDaoJpa;
import de.pta.fd.WebServiceSpendTest.dao.UmsatzDaoJpa;
import de.pta.fd.WebServiceSpendTest.model.StRumsatztyp;
import de.pta.fd.WebServiceSpendTest.model.StTabteilung;
import de.pta.fd.WebServiceSpendTest.model.StTmatgruppe;
import de.pta.fd.WebServiceSpendTest.model.StTstandort;
import de.pta.fd.WebServiceSpendTest.model.StTsupplier;
import de.pta.fd.WebServiceSpendTest.model.StTumsatz;
import de.pta.fd.WebServiceSpendTest.model.additionalBeans.UmsatzResult;
import junit.framework.TestCase;

/**
 * @author dietricf
 *
 */
public class ModelTest extends TestCase {
	
	
	private static final Logger LOGGER = Logger.getLogger(ModelTest.class.getName());
	
	public static void main(String[] args) {
		new ModelTest("Outside Test").testModel();
	}
	/**
	 * @param testName
	 */
	public ModelTest( String testName ) {
		super( testName );
		

	}
	
	
	
	public void testModel( ) {
		// Test Model Bean
		LOGGER.info("Test StRumsatztyp");
		StRumsatztyp rum = new StRumsatztyp();
		rum.setId(123);
		rum.setUmsatzTyp("Killefitz");
		assertEquals( rum.getId(), 123);
		assertEquals(rum.getUmsatzTyp(), "Killefitz");
		
		// Test der Listen
		LOGGER.info("Test Standortliste");
		StandortDaoJpa sojpa = new StandortDaoJpa();
		List<StTstandort> soList = sojpa.getAllStandorte();
		
		assertEquals(soList.size() > 0, true);
		
		LOGGER.info("Test Abteilungsliste");
		List<StTabteilung> aset = soList.get(0).getStTabteilungs();
		
		assertEquals( aset.size() > 0, true);
		
		
		LOGGER.info("Test Materialgruppen");
		MatgruppeDaoJpa majpa = new MatgruppeDaoJpa();
		List<StTmatgruppe> malist = majpa.GetMaterialgruppenListe(null);
		
		assertEquals( malist.size() > 0, true);
		
		
		LOGGER.info("Lieferanten testen");
		SupplierDaoJpa sujpa = new SupplierDaoJpa();
		List<StTsupplier> sulist = sujpa.GetAllSuppliers();
		
		assertEquals( sulist.size() > 0,  true);
		
		
		
		
		// Test Umsatz-Funktion
		LOGGER.info("Umsatz-Funktion testen");
		UmsatzDaoJpa umsdao = new UmsatzDaoJpa();
		Long aid, mid, sid;
		aid = aset.iterator().next().getId();
		mid = malist.get(0).getId();
		sid = sulist.get(0).getId();
		
		List<StTumsatz> umslist = umsdao.GetUmsaetze( aid, mid, sid);
		assertEquals("Umsatztabelle leer", umslist.size() > 0, true); 
		
		UmsatzDaoJpa umdao2 = new UmsatzDaoJpa();
		List<UmsatzResult> umslist2 = umdao2.GetUmsaetzeDirekt(aid, mid, sid);
		assertEquals("Umsatztabelle2 leer", umslist2.size() > 0, true);
		
		assertEquals("Umsatzfunktionen liefern unterschiedliche Ergebnisse", umslist.size(), umslist2.size());
		

		
	}

}
