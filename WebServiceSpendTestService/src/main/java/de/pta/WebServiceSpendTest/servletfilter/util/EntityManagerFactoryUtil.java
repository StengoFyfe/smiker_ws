package de.pta.WebServiceSpendTest.servletfilter.util;

import javax.persistence.EntityManager;

/**
 * Hilfsklasse f�r die Ablage des EntityManager.
 * Es wird die ThreadLocal-Variable genutzt, die f�r jeden Thread (Request) existiert.
 * 
 * @author Training.
 *
 */
public class EntityManagerFactoryUtil {

    public static final ThreadLocal<EntityManager> 
        ENTITY_MANAGERS = new ThreadLocal<EntityManager>();
    
    /**
     * 
     * @return current entity manager
     */
    public static EntityManager getCurrentEntityManager() {
        return ENTITY_MANAGERS.get();
    }
}