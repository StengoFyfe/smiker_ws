package de.pta.WebServiceSpendTest.servletfilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.Semaphore;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;



//import de.jsf.bean.util.JsfUtil;
import de.pta.WebServiceSpendTest.servletfilter.util.EntityManagerFactoryUtil;
import de.pta.fd.WebServiceSpendTest.dao.EMProvider;

/**
 * Filter / Interceptor f�r JSF-Servlet. Requests/Responses f�r JSF-Servlet
 * werden abgefangen. Vor dem Prozessieren der Resources bzw. des Targets wird
 * der EntityManager (die Session) erzeugt und danach geschlossen. Beispielhafte
 * Implementierung des Open Session in View-Pattern (OSIV).
 * 
 * @author Training
 * 
 */
public class EntityManagerRequestFilter implements Filter, IConstraintException {

	private final int maxConnections = 8;
	private static Integer CritObj = 0;
	private final Semaphore available =  new Semaphore(maxConnections, true);
	private EMProvider emp = null;
	private static final Logger LOGGER = Logger
			.getLogger(EntityManagerRequestFilter.class);

	/**
	 * Wird beim Heruntgerfahren des Application Servers aufgerufen. Zerst�rt
	 * die EntityManagerFactory.
	 */
	public void destroy() {
		if (emp.isOpen()) {
			emp.close();
			LOGGER.info("EntityManagerRequestFilter.destroy() - EMProvider (EM-Factory) closed!");
		}
	}

	/**
	 * Methode wird bei jedem Request aufgerufen.
	 * Sie erlaubt Aktionen vor der eigentlichen Abarbeitung des Requests (chain.doFilter())
	 * und nach der Abarbeitung des Requests.
	 * In diesem Fall wird vor dem Prozessieren des Request der EntityManager ge�ffnet
	 * und die Transaktion gestartet.
	 * Nach der Abarbeitung des Requests wird die Transaktion committed und der
	 * EntityManager geschlossen.
	 * @param request - der aktuelle Request.
	 * @param response - die Resoonse.
	 * @param chain - die aktuelle Chain mit dem zu prozessierenden Request.
	 * @throws IOException - any Exception.
	 * @throws ServletException - any Exception.
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		EntityManager em = null;
		try {
			ServletRequest sr = request;
			HttpServletRequest hsr = null;
			if (request instanceof HttpServletRequest) {
				hsr = (HttpServletRequest) request;
				String url = hsr.getRequestURL().toString();
				LOGGER.info("URL: " + url);
			}

			String paramName;
			for (Enumeration<String> en = sr.getParameterNames(); en
					.hasMoreElements();) {
				paramName = (String) en.nextElement();
				LOGGER.info("Param: " + paramName);
			}
			// Synchronize via Semaphore
			available.acquire();

			synchronized(CritObj) {
				em = emp.getEntityManager();
				LOGGER.info("EntityManager using uses EM: " + em.toString());
				LOGGER.info("EntityManager created!");
				em.getTransaction().begin();
				LOGGER.info("EntityManager Transaction started!");
				EntityManagerFactoryUtil.ENTITY_MANAGERS.set(em);
				LOGGER.info("EntityManagerRequestFilter.doFilter() - Thread local with EntityManager set!");

			}
			chain.doFilter(request, response);
			//LOGGER.info("EntityManagerRequestFilter.doFilter() - Before GT!");
			//EntityTransaction et = em.getTransaction(); 
			LOGGER.info("EntityManagerRequestFilter.doFilter() - Before COMMIT!");
			
			synchronized(CritObj) {
				em.getTransaction().commit();
				LOGGER.info("EntityManager Transaction commit!");
				EntityManagerFactoryUtil.ENTITY_MANAGERS.remove();
				LOGGER.info("EntityManagerRequestFilter.doFilter() - EntityManager from Thread local removed!");
				if (em != null && em.isOpen()) {
					em.close();
					
					LOGGER.info("EntityManagerRequestFilter.doFilter() - EntityManager closed! (in)");
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Exception EntityManagerRequestFilter: "
					+ ex.getMessage());
			em.getTransaction().rollback();
			
			try {
				throwException(ex);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				available.release();
				if (em != null && em.isOpen()) {
					em.close();
					
					LOGGER.info("EntityManagerRequestFilter.doFilter() - EntityManager closed!");
				}

			} catch (Throwable t) {
				LOGGER.fatal("EntityManagerRequestFilter.doFilter() - Error closing EntityManager!");
				t.printStackTrace();
				Exception ex = new Exception(t.getLocalizedMessage());
				//JsfUtil.addErrorMessage("msgStoreFailed", ex);
				throw new ServletException(t.getLocalizedMessage());
			}

		}
	}

	/**
	 * Wir beim Hochfahren des Application Servers aufgerufen. Erzeugt eine
	 * EntityManagerFactory, die f�r die laufzeit des Application Servers
	 * erhalten bleibt.
	 * 
	 * @param arg0
	 *            - Filterparameter.
	 * @throws ServletException
	 *             - beliebige Exception.
	 */
	public void init(final FilterConfig arg0) throws ServletException {
		LOGGER.info("EntityManagerRequestFilter.init() - EntityManagerProvider (EM-Factory) B4initialized!");		
		emp = new EMProvider();
		LOGGER.info("EntityManagerRequestFilter.init() - EntityManagerProvider (EM-Factory) initialized!");
	}

	@Override
	public void throwException(final Exception ex) throws Exception {
		throw ex;
		
	}

}