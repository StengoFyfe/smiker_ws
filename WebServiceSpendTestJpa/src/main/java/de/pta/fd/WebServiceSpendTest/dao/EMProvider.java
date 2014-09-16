/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * EMProvider Wrapper for EntityManagerFactory.
 * 
 * @author Training
 *
 */
public final class EMProvider {
    
   
    private static final String PERSISTENCE_UNIT_NAME = "WebAppSpendTestJpa";
    
    private static EntityManagerFactory emf = null;

    /**
     * Erzeugen der Factory fuer EntityManager.
     */
    public EMProvider() {
        //super();
        if (EMProvider.emf == null) {
        	createEntityManagerFactory();
        }
    }

    /**
     * 
     * Ist Factory bereits erzeugt?
     * @return boolean - true, wenn erzeugt.
     */
    public boolean isOpen() {
        return getEmf() != null;
    }
    
    /**
     * Schliessen der Factory.
     */
    public void close() {
        if (isOpen()) {
            getEmf().close();
        }
    }
    
     /**
     * Speichern EntityManagerFactory.
     * @param emf - EntityManagerFactory.
     */
    private void setEmf(final EntityManagerFactory emf) {
        EMProvider.emf = emf;
    }
    
    /**
     * Gibt EntityManagerFactory zurueck.
     * Getter fuer emf.
     * @return EntityManagerFactory emf.
     */
    public EntityManagerFactory getEmf() {
        return EMProvider.emf;
    }

    /**
     * Gibt einen neuen EntityManager zurueck.
     * @return EntityManager - EntityManager.
     */
    public EntityManager getEntityManager() {
         return emf.createEntityManager();
    }
    
    /**
     * Erzeugen der EMFactory.
     */
    private void createEntityManagerFactory() {
        setEmf(Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME));
        
    }
}
