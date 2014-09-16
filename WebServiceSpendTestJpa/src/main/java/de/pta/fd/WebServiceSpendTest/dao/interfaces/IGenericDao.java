/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.dao.interfaces;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * 
 * @author Training.
 *
 * @param <T> - Persistence Class (Entity in JPA).
 * @param <ID> - Primary Key.
 */
public interface IGenericDao<T, ID extends Serializable> {
	
	/**
	 * Find by primary key.
	 * 
	 * @param persistentClass - Class<T>.
	 * @param id - ID.
	 * @return T - Instance of T.
	 */
	T findById(Class<T> persistentClass, ID id);
	
	/**
	 * Find by primary key in Transaction.
	 * 
	 * @param persistentClass - Class<T>.
	 * @param id - ID.
	 * @return T - Instance of T.
	 */
	T findByIdInTx(Class<T> persistentClass, ID id);
	
	/**
	 * Store a <T>.
	 * If id=0, persist, else merge.
	 *  
	 * @param persistentObject - Instance of Persistence Class (T).
	 * @return T - new persistent Object if merged.
	 * @throws Exception - any Exception.
	 */
	T store(T persistentObject) throws Exception;
	
	/**
	 * Store a <T> in a Transaction
	 * If id=0, persist, else merge.
	 *  
	 * @param persistentObject - Instance of Persistence Class (T).
	 * @return T - new persistent Object if merged.
	 * @throws Exception - any Exception.
	 */
	T storeInTx(T persistentObject) throws Exception;
	
	/**
	 * Insert new <T>.
	 *  
	 * @param persistentObject - Instance of Persistence Class (T).
	 * @throws Exception - any Exception.
	 */
	void persist(T persistentObject) throws Exception;
	
	/**
	 * Insert new <T> in running transaction.
	 *  
	 * @param persistentObject - Instance of Persistence Class (T).
	 * @throws Exception - any Exception.
	 */
	void persistInTx(T persistentObject) throws Exception;

	/**
	 * Merge object and return newly created persistent object.
	 * 
	 * @param persistentObject <T>.
	 * @return <T> - persistentObject.
	 * @throws Exception - any Exception.
	 */
	T merge(T persistentObject) throws Exception;
	
	/**
	 * Merge object in running transaction
	 * and return newly created persistent object.
	 * 
	 * @param persistentObject <T>.
	 * @return <T> - persistentObject.
	 * @throws Exception - any Exception.
	 */
	T mergeInTx(T persistentObject) throws Exception;
	
	/**
	 * Remove persistentObject from Database.
	 * @param persistentObject T - Persistent Object.
	 * @param id ID - primary key.
	 * @throws Exception - any Exception.
	 */
	void remove(T persistentObject, ID id) throws Exception;
	
	/**
	 * Remove persistentObject from Database in current transaction.
	 * @param persistentObject T - Persistent Object.
	 * @param id ID - primary key.
	 * @throws Exception - any Exception.
	 */
	void removeInTx(T persistentObject, ID id) throws Exception;

	/**
	 * Return all Persistent Objects (all = true) 
	 * or Persistent Objects between <firstResult> and <lastResult>.
	 * @param all - all Persistent Objects.
	 * @param maxResult int - end.
	 * @param firstResult int - start.
	 * @return List<T> - List of found persistent objects.
	 */
	List<T> find(boolean all, int maxResult, int firstResult);
	
	/**
	 * Return all Persistent Objects (all = true) in Transaction.
	 * or Persistent Objects between <firstResult> and <lastResult>.
	 * @param all - all Persistent Objects.
	 * @param maxResult int - end.
	 * @param firstResult int - start.
	 * @return List<T> - List of found persistent objects.
	 */
	List<T> findInTx(boolean all, int maxResult, int firstResult);

	/**
	 * Return initialized EntityManager.
	 * 
	 * @return em - EntityManager.
	 */
	EntityManager getEm();
	
	/**
	 * Starten einer Transaktion.
	 */
	void startTransaction();
	
	/**
	 * Beenden Transaktion.
	 */
	void endTransaction();
	
	/**
	 * Commit Transaktion.
	 */
	void commitTransaction();
	
	/**
	 * Rollback Transaktion.
	 */
	void rollbackTransaction();
	
	/**
	 * Schlie�en EntityManager.
	 */
	void closeEm();
}
