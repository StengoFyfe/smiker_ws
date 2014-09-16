package de.pta.WebServiceSpendTest.services.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface SpendTestService {
	@WebMethod 
	public String getAllStandorte();

}
