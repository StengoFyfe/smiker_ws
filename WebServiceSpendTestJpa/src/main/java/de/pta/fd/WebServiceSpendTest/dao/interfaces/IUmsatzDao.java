package de.pta.fd.WebServiceSpendTest.dao.interfaces;

import java.util.List;

import de.pta.fd.WebServiceSpendTest.model.StTumsatz;
import de.pta.fd.WebServiceSpendTest.model.additionalBeans.UmsatzResult;

public interface IUmsatzDao extends IGenericDao<StTumsatz, Long> {
	List<StTumsatz> GetUmsaetze( Long AbteilungId, Long MatgruppeId, Long SupplierId );
	List<UmsatzResult> GetUmsaetzeDirekt( Long AbteilungsId, Long MatgruppeId, Long SupplierId);

}
