package de.pta.fd.WebServiceSpendTest.dao;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import de.pta.fd.WebServiceSpendTest.dao.GenericDaoJpa;
import de.pta.fd.WebServiceSpendTest.dao.interfaces.IUmsatzDao;
import de.pta.fd.WebServiceSpendTest.model.StTumsatz;
import de.pta.fd.WebServiceSpendTest.model.additionalBeans.UmsatzResult;

public class UmsatzDaoJpa extends GenericDaoJpa<StTumsatz, Long> implements
		IUmsatzDao {


	@Override
	@SuppressWarnings("unchecked")	
	public List<StTumsatz> GetUmsaetze(Long AbteilungId, Long MatgruppeId,
			Long SupplierId) {

		getEm().getTransaction().begin();
			/*	
		//List<StTumsatz> result =  (List<StTumsatz>)ss.createQuery("from ST_TUMSATZ WHERE ID IN ( SELECT ID from TABLE(GETUMSATZ( "+AbteilungId+", "+MatgruppeId+", "+SupplierId+" )) )").list();
		List<StTumsatz> result =  (List<StTumsatz>)ss.createSQLQuery("from ST_TUMSATZ WHERE ID IN ( SELECT ID from TABLE(GETUMSATZ( "+AbteilungId+", "+MatgruppeId+", "+SupplierId+" ) ) )").list();
		*/
		Query sqlquery = getEm().createNativeQuery("SELECT s.* from ST_TUMSATZ s WHERE ID IN ( SELECT ID from TABLE(GETUMSATZ( "+AbteilungId+", "+MatgruppeId+", "+SupplierId+" ) ) )", StTumsatz.class );
		List<StTumsatz> result = (List<StTumsatz>)sqlquery.getResultList();

		LOGGER.info("UmsatzList with " + result.size() + " entries found!");
		getEm().getTransaction().commit();

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UmsatzResult> GetUmsaetzeDirekt(Long AbteilungsId,
			Long MatgruppeId, Long SupplierId) {

		getEm().getTransaction().begin();
		
		String Query = "SELECT a.ID as Id, a.BUCHUNGSDATUM as buchungsdatum, a.BETRAG as betrag, b.UMSATZ_TYP as RefUmsatztyp "+
						"FROM ST_TUMSATZ a JOIN ST_RUMSATZTYP b ON (b.ID = a.FK_REF_UMSATZTYP) "+
						"WHERE a.FK_ABTEILUNG = :fkAbteilung AND a.FK_MATGRUPPE = :fkMatgruppe AND a.FK_SUPPLIER=:fkSupplier ORDER BY a.BUCHUNGSDATUM asc";
		Query sqlQuery = getEm().createNativeQuery(Query);
		sqlQuery.setParameter("fkAbteilung", AbteilungsId);
		sqlQuery.setParameter("fkMatgruppe", MatgruppeId);
		sqlQuery.setParameter("fkSupplier", SupplierId);
		

		
		List<Object> resultList  = sqlQuery.getResultList();
//				.addScalar("Id", StandardBasicTypes.LONG)
//				.addScalar("buchungsdatum", StandardBasicTypes.DATE)
//				.addScalar("betrag", StandardBasicTypes.BIG_DECIMAL)
//				.addScalar("RefUmsatztyp", StandardBasicTypes.STRING)
//				.setParameter("fkAbteilung", AbteilungsId)
//				.setParameter("fkMatgruppe", MatgruppeId)
//				.setParameter("fkSupplier", SupplierId)
//				.setResultTransformer(  Transformers.aliasToBean(UmsatzResult.class)      )
//				.list();		
//		
		LOGGER.info("UmsatzList with " + resultList.size() + " entries found!");
		getEm().getTransaction().commit();
		
		List<UmsatzResult> ListCopy = new LinkedList<UmsatzResult> ();
		for( Object result : resultList) {
			Object [] resultArr = (Object[]) result;
			UmsatzResult NewUms = new UmsatzResult();
		    NewUms.setId(new Long((int)resultArr[0]));
		    NewUms.setBuchungsdatum((java.util.Date)resultArr[1]);
		    NewUms.setBetrag((BigDecimal)resultArr[2]);
		    NewUms.setRefUmsatztyp((String)resultArr[3]);
			ListCopy.add(NewUms);
		}

		return ListCopy; 

	}

}
