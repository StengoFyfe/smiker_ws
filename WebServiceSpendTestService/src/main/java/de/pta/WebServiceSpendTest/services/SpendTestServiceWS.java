package de.pta.WebServiceSpendTest.services;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import de.pta.WebServiceSpendTest.services.interfaces.SpendTestService;
import de.pta.fd.WebServiceSpendTest.dao.StandortDaoJpa;
import de.pta.fd.WebServiceSpendTest.model.StTstandort;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;


@WebService(endpointInterface="de.pta.WebServiceSpendTest.services.interfaces.SpendTestService", 
		serviceName="SpendTestServiceWS")

public class SpendTestServiceWS implements SpendTestService{
	
	/* see http://docs.oracle.com/cd/E17904_01/web.1111/e13734/stateful.htm
	@Resource  // enable stateful Service		
	*/
	private WebServiceContext wsContext;  
	
	public String getAllStandorte() {
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("[" + "\r\n");
		
		StandortDaoJpa sodao = new StandortDaoJpa();
		int i=0;
		List<StTstandort> lista = sodao.getAllStandorte(); 
		for( StTstandort s : lista) {
			jsonBuilder.append("{\"ID\": \"");
			jsonBuilder.append(s.getId());
			jsonBuilder.append("\", \"NAME\": \"");
			jsonBuilder.append(s.getName());
			jsonBuilder.append("\", \"NUMMER\": \"");
			jsonBuilder.append(s.getNummer());
			jsonBuilder.append("\" )");
			i++;
			if(i < lista.size())
				jsonBuilder.append(", \r\n");
		}
		
		jsonBuilder.append("]" + "\r\n");

		String txtResponse = jsonBuilder.toString();
		System.out.println(txtResponse);
		
		return jsonBuilder.toString();
	}
	
	
	
	public static void main(String[] args) {
		

	}


	
}
