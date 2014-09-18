package de.pta.WebServiceSpendTest.services;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

import de.pta.WebServiceSpendTest.jsonmodel.AbteilungJson;
import de.pta.WebServiceSpendTest.jsonmodel.MaterialgruppeJson;
import de.pta.WebServiceSpendTest.jsonmodel.StandortJson;
import de.pta.WebServiceSpendTest.jsonmodel.SupplierJson;
import de.pta.WebServiceSpendTest.jsonmodel.UmsatzJson;
import de.pta.WebServiceSpendTest.util.SessionManager;
import de.pta.fd.WebServiceSpendTest.dao.MatgruppeDaoJpa;
import de.pta.fd.WebServiceSpendTest.dao.StandortDaoJpa;
import de.pta.fd.WebServiceSpendTest.dao.SupplierDaoJpa;
import de.pta.fd.WebServiceSpendTest.dao.UmsatzDaoJpa;
import de.pta.fd.WebServiceSpendTest.model.StTabteilung;
import de.pta.fd.WebServiceSpendTest.model.StTmatgruppe;
import de.pta.fd.WebServiceSpendTest.model.StTstandort;
import de.pta.fd.WebServiceSpendTest.model.StTsupplier;
import de.pta.fd.WebServiceSpendTest.model.additionalBeans.UmsatzResult;




@Path(value="/SpendTestServices")
public class SpendTestRESTJSONService {


	
	public static final Logger LOGGER = Logger.getLogger(SpendTestRESTJSONService.class
			.getName());	
	
	private class comparator implements Comparator<StTabteilung> {

		@Override
		public int compare(StTabteilung arg0, StTabteilung arg1) {
			
			return arg0.getAbtNummer().compareTo(arg1.getAbtNummer());
		}
	}
	
	private StTabteilung[] getSortedList( List<StTabteilung> srcSet) {
		StTabteilung[] arrList = new StTabteilung[srcSet.size()];
		arrList = srcSet.toArray(arrList);
		Arrays.sort(arrList, new comparator());
		
		return arrList;
	}	
	
	private boolean notAuthenticated( String SessionID ) {
		SessionManager sessione = SessionManager.getInstance();		
		
		return !sessione.refreshSessionIsAlive(SessionID ); 
	}
	
	
	@POST

	@Path("/loginService/{name}")
	public Response loginServ( 	@PathParam("name") String name,
									@FormParam("USR") String usr,
									@FormParam("PWD") String pwd,
									@CookieParam(value="XSRF-TOKEN") String SessionID) {
		
		String resp = "";
		NewCookie nc = null;
		SessionManager sessione = SessionManager.getInstance();
		String sessionToken = null;
		
		// look if we're backing up an existant session
		if( (usr == null || usr.isEmpty() ) && (pwd == null || pwd.isEmpty()) ) {
			if( SessionID != null ) {
				if( sessione.refreshSessionIsAlive(SessionID) ) {
					sessionToken = SessionID; // stays the same... still ok
					resp = "OK";
				}
				else { 
					resp = "NOK";
				}
			}
				
		} 
		else {
		
			if( usr.toString().equals( pwd.toString() ) ) {
				resp = "OK";
				sessionToken = sessione.registerNewSession();
	
				nc = new NewCookie("XSRF-TOKEN", sessionToken , "", null, 1, "", 24 * 3600, false);
			}
			else {
				resp = "NOK";
			}
		}
		
		StringBuilder bbq = new StringBuilder("{ \"ret\": \""+resp+"\" }");

		ByteArrayOutputStream bos = new ByteArrayOutputStream( );
		
		try {
			
			bos.write(bbq.toString().getBytes());

			ResponseBuilder rb =  Response.status(200).entity(bbq.toString().getBytes("UTF-8"))
							.header("Content-Type", "application/json; charset=utf-8")
							.header("Accept", "application/json; charset=utf-8")
							.header("Accept-Charset", "charset=utf-8");
							//.header("Access-Control-Allow-Origin", "")
							
			
			if( sessionToken != null )  {
				rb.header("X-XSRF-TOKEN", sessionToken);
			}
			if( nc != null )
				rb.cookie(nc);
			
			
			Response ret = rb.build();
			
			return ret;
		
		}
		catch (Exception e) 
		{
			return Response.status(500).build();
		}
		finally {
			/*
			if( em != null && em.isOpen())
				em.close();
				*/
		}
	}	
	

	
	

	/**
	 * @param name (nicht relevanter Parameter)
	 * @param SessID (Session-ID, welche vom Client als Header Info mitgegeben werden muss
	 * @return Response als JSon-Result
	 */
	@POST
	@Path("/standorte/{name}")
	//@Produces("application/json")


	public Response produceJSONStandorte( 
					@PathParam("name") String name, 
					@HeaderParam(value="X-XSRF-TOKEN") String SessID) {
		
		SessionManager sessione = SessionManager.getInstance();		
		
		if( notAuthenticated(SessID) ) {
			LOGGER.warn("abteilungen: unauthenticated access!");
			return Response.status(401).build();
		}

		StandortDaoJpa sodao = new StandortDaoJpa();
		EntityManager em = sodao.getEm();
		List<StTstandort> lista = sodao.getAllStandorte();
		
		StringBuilder bbq = new StringBuilder("[");
		int i=0;
		for( StTstandort so : lista) {
			bbq.append(new StandortJson(so).toString() );
			i++;
			if( i < lista.size())
				bbq.append(",\r\n");
		}
		bbq.append("]");

		ByteArrayOutputStream bos = new ByteArrayOutputStream( );
		
		try {
			
			bos.write(bbq.toString().getBytes());
		

			Response ret =  Response.status(200).entity(bbq.toString().getBytes("UTF-8"))
							.header("Content-Type", "application/json; charset=utf-8")
							.header("Accept", "application/json; charset=utf-8")
							.header("Accept-Charset", "charset=utf-8")
							.header("Access-Control-Allow-Origin", "")
							
							.build();
			/*
			Response ret =  Response.status(200).entity(bbq.toString())
					.header("Content-Type", "application/json")
					.header("Access-Control-Allow-Origin", "")
					.build();
					*/
			
			return ret;
		
		}
		catch (Exception e) 
		{
			return Response.status(500).build();
		}
		finally {
			if( em != null && em.isOpen())
				em.close();
		}

	}
	

	@GET
	@Path("/abteilungen/{name}")
	//@Produces("application/json")


	public Response produceJSONAbteilungen( @PathParam("name") String name, 
											@HeaderParam(value="X-XSRF-TOKEN") String SessionID ) {
		
		if( notAuthenticated(SessionID) ) {
			LOGGER.warn("abteilungen: unauthenticated access!");
			return Response.status(401).build();
		}
		
		StandortDaoJpa sodao = new StandortDaoJpa();
		EntityManager em = sodao.getEm();
		List<StTstandort> lista = sodao.getAllStandorte();
		List<AbteilungJson> listab = new LinkedList<AbteilungJson> ();
		
		for( StTstandort so : lista ) {
			// Abteilungen holen
			List<StTabteilung> aList = so.getStTabteilungs();
			StTabteilung[] arrList = getSortedList(aList);

			for( StTabteilung var : arrList) {
				if( var.getStTabteilung() == null )  { // not a childnode
					listab.add( new AbteilungJson(var, so.getId(), null, 1));
					LoadTreeRecursive( listab, so.getId(), var, 1 );
				}
			}
		}
		
		StringBuilder bbq = new StringBuilder("[");
		int i=0;
		for( AbteilungJson aj : listab) {
			bbq.append(aj.toString() );
			i++;
			if( i < listab.size())
				bbq.append(",\r\n");
		}
		bbq.append("]");

		ByteArrayOutputStream bos = new ByteArrayOutputStream( );
		
		try {
			
			bos.write(bbq.toString().getBytes());
		

			Response ret =  Response.status(200).entity(bbq.toString().getBytes("UTF-8"))
							.header("Content-Type", "application/json; charset=utf-8")
							.header("Accept", "application/json; charset=utf-8")
							.header("Accept-Charset", "charset=utf-8")
							.header("Access-Control-Allow-Origin", "")
							
							.build();
			/*
			Response ret =  Response.status(200).entity(bbq.toString())
					.header("Content-Type", "application/json")
					.header("Access-Control-Allow-Origin", "")
					.build();
					*/
			
			return ret;
		
		}
		catch (Exception e) 
		{
			return Response.status(500).build();
		}
		finally {
			if( em != null && em.isOpen())
				em.close();
		}

	}
	

	@GET
	@Path("/materialgruppen/{name}")
	//@Produces("application/json")
	public Response produceJSONMaterialgruppen( @PathParam("name")  String name, 
												@HeaderParam(value="X-XSRF-TOKEN") String SessionID ) {
		
		if( notAuthenticated(SessionID)) {
			LOGGER.warn("materialgruppen: unauthenticated access!");
			return Response.status(401).build();
		}
		
		
		MatgruppeDaoJpa sodao = new MatgruppeDaoJpa();
		EntityManager em = sodao.getEm();
		List<StTmatgruppe> lista = sodao.GetMaterialgruppenListe(null);
		
		StringBuilder bbq = new StringBuilder("[");
		int i=0;
		for( StTmatgruppe ma : lista) {
			bbq.append(new MaterialgruppeJson(ma).toString() );
			i++;
			if( i < lista.size())
				bbq.append(",\r\n");
		}
		bbq.append("]");

		ByteArrayOutputStream bos = new ByteArrayOutputStream( );
		
		try {
			
			bos.write(bbq.toString().getBytes());
		

			Response ret =  Response.status(200).entity(bbq.toString().getBytes("UTF-8"))
							.header("Content-Type", "application/json; charset=utf-8")
							.header("Accept", "application/json; charset=utf-8")
							.header("Accept-Charset", "charset=utf-8")
							.header("Access-Control-Allow-Origin", "")
							.build();
			
			return ret;
		
		}
		catch (Exception e) 
		{
			return Response.status(500).build();
		}
		finally {
			if( em != null && em.isOpen() )
				em.close();
		}

	}
	

	@GET
	@Path("/supplier/{name}")
	//@Produces("application/json")
	public Response produceJSONSupplier( @PathParam("name") String name, 
										 @HeaderParam(value="X-XSRF-TOKEN") String SessionID ) {
		
		if( notAuthenticated(SessionID) ) {
			LOGGER.warn("supplier: unauthenticated access!");
			return Response.status(401).build();
		}
		
		
		SupplierDaoJpa sudao = new SupplierDaoJpa();
		EntityManager em = sudao.getEm();
		List<StTsupplier> lista = sudao.GetAllSuppliers();
		
		StringBuilder bbq = new StringBuilder("[");
		int i=0;
		for( StTsupplier su : lista) {
			bbq.append(new SupplierJson(su).toString() );
			i++;
			if( i < lista.size())
				bbq.append(",\r\n");
		}
		bbq.append("]");

		ByteArrayOutputStream bos = new ByteArrayOutputStream( );
		
		try {
			
			bos.write(bbq.toString().getBytes());
		

			Response ret =  Response.status(200).entity(bbq.toString().getBytes("UTF-8"))
							.header("Content-Type", "application/json; charset=utf-8")
							.header("Accept", "application/json; charset=utf-8")
							.header("Accept-Charset", "charset=utf-8")
							.header("Access-Control-Allow-Origin", "")
							.build();
			
			return ret;
		
		}
		catch (Exception e) 
		{
			return Response.status(500).build();
		}
		finally {
			if( em != null && em.isOpen())
				em.close();
		}
		

	}	
	

	@POST
	@Path("/supplierStore/{name}")
	public Response storeSupplier( 	@PathParam("name") String name,
									@FormParam("ID") Long id,
									@FormParam("FIRMA") String firma, 
									@FormParam("KRED_ID") String kred_id,
									@HeaderParam(value="X-XSRF-TOKEN") String SessionID) {
		
		if( notAuthenticated(SessionID)) {
			LOGGER.warn("supplierStore: unauthenticated access!");
			return Response.status(401).build();
		}
		
		

		SupplierDaoJpa suppDaoJpa = new SupplierDaoJpa();
		EntityManager em = suppDaoJpa.getEm();		

		
		if( id == 0 ) {
			// We have a new Supplier
			boolean SuppExists = suppDaoJpa.SupplierExists(firma, kred_id);
			
			if (!SuppExists) {
				
				StTsupplier newSupplier = new StTsupplier();
				newSupplier.setFirma(firma);
				newSupplier.setKredId(kred_id);
				
				try {
					suppDaoJpa.persist(newSupplier);
				}
				catch(Exception e ) {
					LOGGER.error("Save Supplier to DB failed!" + e.getMessage());
					return Response.status(500).build();
				}
			} else {
				//UIController.getInstance().ShowMessageDialog("There is already such a supplier" );
			}
			
			
		} else {
			// Change the Supplier with Update in the Database
			StTsupplier sepp = suppDaoJpa.findByIdInTx(StTsupplier.class, id);
			if( sepp != null ) {
				sepp.setFirma(firma);
				sepp.setKredId(kred_id);
				try {
					suppDaoJpa.merge(sepp);
				}
				catch( Exception e ) {
					LOGGER.error("Update Supplier to DB failed!" + e.getMessage());
					return Response.status(500).build();
				}
			}
		}
		
		StringBuilder bbq = new StringBuilder("{ \"ret\": \"OK\" }");

		ByteArrayOutputStream bos = new ByteArrayOutputStream( );
		
		try {
			
			bos.write(bbq.toString().getBytes());
		

			Response ret =  Response.status(200).entity(bbq.toString().getBytes("UTF-8"))
							.header("Content-Type", "application/json; charset=utf-8")
							.header("Accept", "application/json; charset=utf-8")
							.header("Accept-Charset", "charset=utf-8")
							.header("Access-Control-Allow-Origin", "")
							.build();
			
			return ret;
		
		}
		catch (Exception e) 
		{
			return Response.status(500).build();
		}
		finally {
			if( em != null && em.isOpen())
				em.close();
		}
	}	
	

	@GET
	@Path("/umsatz/{idAbt}/{idMatgruppe}/{idSupplier}")
	//@Produces("application/json")
	public Response produceJSONUmsatz(  @PathParam("idAbt") Long idAbt, 
										@PathParam("idMatgruppe") Long idMatgruppe, 
										@PathParam("idSupplier") Long idSupplier, 
										@HeaderParam(value="X-XSRF-TOKEN") String SessionID ) {
		
		if( notAuthenticated(SessionID)) {
			LOGGER.warn("umsatz: unauthenticated access!");
			return Response.status(401).build();
		}
		
		
		UmsatzDaoJpa umdao = new UmsatzDaoJpa();
		EntityManager em = umdao.getEm();
		
		List<UmsatzResult> lista = umdao.GetUmsaetzeDirekt(idAbt,  idMatgruppe,  idSupplier);
  
		StringBuilder bbq = new StringBuilder("[");
		int i=0;
		for( Object o : lista) {
			UmsatzResult um = (UmsatzResult)o;
			bbq.append(new UmsatzJson(um).toString() );
			i++;
			if( i < lista.size())
				bbq.append(",\r\n");
		}
		bbq.append("]");

		ByteArrayOutputStream bos = new ByteArrayOutputStream( );
		
		try {
			
			bos.write(bbq.toString().getBytes());
		

			Response ret =  Response.status(200).entity(bbq.toString().getBytes("UTF-8"))
							.header("Content-Type", "application/json; charset=utf-8")
							.header("Accept", "application/json; charset=utf-8")
							.header("Accept-Charset", "charset=utf-8")
							.header("Access-Control-Allow-Origin", "")
							.build();
			
			return ret;
		
		}
		catch (Exception e) 
		{
			return Response.status(500).build();
		}
		finally {
			if( em != null && em.isOpen())
				em.close();
		}
		

	}	
		
	
	private void LoadTreeRecursive( List<AbteilungJson> retList, long StandortId, StTabteilung abteilungParent, int Depth) {
		List<StTabteilung> childSet = abteilungParent.getStTabteilungs();
		StTabteilung[] arrList = getSortedList(childSet);
		
		for(StTabteilung var : arrList ) {
			retList.add( new AbteilungJson( var, StandortId, abteilungParent.getId(), Depth + 1) );
			
			LoadTreeRecursive( retList, StandortId, var, Depth + 1);
		}
	}
	

}

