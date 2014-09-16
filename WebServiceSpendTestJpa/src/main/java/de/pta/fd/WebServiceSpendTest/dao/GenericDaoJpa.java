/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import de.pta.fd.WebServiceSpendTest.dao.interfaces.IGenericDao;

/**
 * 
 * @author Training.
 * 
 * @param <T>
 *            - persistent Class.
 * @param <ID>
 *            - primary Key.
 */
public abstract class GenericDaoJpa<T, ID extends Serializable> implements
		IGenericDao<T, ID> {

	public static final Logger LOGGER = Logger.getLogger(GenericDaoJpa.class
			.getName());

	// Create Entity Manager Factory
	private EMProvider emp = new EMProvider();
	private EntityManager em;

	/**
	 * Standard Constructor.
	 */
	public GenericDaoJpa() {
		DOMConfigurator.configureAndWatch("log4j.xml");
	}
	
	/**
	 * 
	 * Set EntityManager from View (OSIV).
	 * 
	 * @param entityManager - EntityManager.
	 */
	public GenericDaoJpa(final EntityManager entityManager) {
		if (this.em == null || (!em.isOpen())) {
			em = entityManager;
		}
		DOMConfigurator.configureAndWatch("log4j.xml");
	}

	/**
	 * Get EntityManager. Create EntityManager if not exists.
	 * 
	 * @return em - EntityMaanger
	 */
	public EntityManager getEm() {
		if (em == null  || (!em.isOpen())) {
			em = emp.getEntityManager();
		}
		return em;
	}

	/**
	 * {@inheritDoc}
	 */
	public T findById(final Class<T> persistentClass, final ID id) {
		final T entity = getEm().find(persistentClass, id);
		LOGGER.info("Entity found: " + entity.toString());
		em.close();
		return entity;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public T findByIdInTx(final Class<T> persistentClass, final ID id) {
		final T entity = getEm().find(persistentClass, id);
		LOGGER.info("Entity found: " + entity.toString());
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(final T entity, final ID id) throws Exception {
		getEm();
		try {
			em.getTransaction().begin();
			try {
				em.getReference(entity.getClass(), id);
			} catch (EntityNotFoundException ex) {
				String errorText = "Entity " + entity.toString() + " with id "
						+ id + " no longer exists! - " + ex.getMessage();
				LOGGER.error(errorText);
				throw new Exception(errorText);
			}

			em.remove(entity);
			em.getTransaction().commit();
			LOGGER.info("Entity with Id " + id + " removed!");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			String errorText = "Error removing " + entity + "; "
					+ ex.getMessage();
			LOGGER.error(errorText);
			ex.printStackTrace();
			throw new Exception(errorText);
		} finally {
			LOGGER.info("GenericDao.remove.finally");
			em.close();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void removeInTx(final T entity, final ID id) throws Exception {
		getEm();
		try {
			try {
				em.getReference(entity.getClass(), id);
			} catch (EntityNotFoundException ex) {
				String errorText = "Entity " + entity.toString() + " with id "
						+ id + " no longer exists! - " + ex.getMessage();
				LOGGER.error(errorText);
				throw new Exception(errorText);
			}

			em.remove(entity);
			LOGGER.info("Entity with Id " + id + " removed!");
		} catch (Exception ex) {
			String errorText = "Error removing " + entity + "; "
					+ ex.getMessage();
			LOGGER.error(errorText);
			ex.printStackTrace();
			throw new Exception(errorText);
		} 
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final boolean all, final int maxResult,
			final int firstResult) {
		List<T> entityList = null;
		// Returns the persistent class associated wit T.
		Class<T> persistentClass = getPersistentClass();
		String persistentClassName = persistentClass.getSimpleName();
		String selectString = "select t from " + persistentClassName + " t order by name";
		Query q = getEm().createQuery(selectString);
		if (!all) {
			q.setMaxResults(maxResult);
			q.setFirstResult(firstResult);
		}
		entityList = q.getResultList();
		LOGGER.info("EntityList with " + entityList.size() + " entries found!");
		em.close();
		return entityList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> findInTx(final boolean all, final int maxResult,
			final int firstResult) {
		List<T> entityList = null;
		// Returns the persistent class associated wit T.
		Class<T> persistentClass = getPersistentClass();
		String persistentClassName = persistentClass.getSimpleName();
		String selectString = "select t from " + persistentClassName + " t order by name";
		Query q = getEm().createQuery(selectString);
		if (!all) {
			q.setMaxResults(maxResult);
			q.setFirstResult(firstResult);
		}
		entityList = q.getResultList();
		LOGGER.info("EntityList with " + entityList.size() + " entries found!");
		return entityList;
	}

	/**
	 * {@inheritDoc}
	 */
	public T store(final T entity) throws Exception {
		T entityNew = null;
		
		if (isInsert(entity)) {
			persist(entity);
		} else {
			entityNew = merge(entity);
		}
		return entityNew;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public T storeInTx(final T entity) throws Exception {
		T entityNew = null;
		
		if (isInsert(entity)) {
			persistInTx(entity);
		} else {
			entityNew = mergeInTx(entity);
		}
		return entityNew;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void persist(final T entity) throws Exception {
		getEm();
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			LOGGER.info("Entity " + entity + " persisted!");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			String errorText = "Error persisting " + entity + ": "
					+ ex.getMessage();
			LOGGER.error(errorText);
			ex.printStackTrace();
			throw new Exception(errorText);
		} finally {
			LOGGER.info("GenericDao.persist.finally");
			em.close();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void persistInTx(final T entity) throws Exception {
		getEm();
		try {
			em.persist(entity);
			LOGGER.info("Entity " + entity + " persisted!");
		} catch (Exception ex) {
			String errorText = "Error persisting in Transaction " + entity + ": "
					+ ex.getMessage();
			LOGGER.error(errorText);
			ex.printStackTrace();
			throw new Exception(errorText);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public T merge(final T entity) throws Exception {
		getEm();
		T entityMerged = null;
		try {
			em.getTransaction().begin();
			entityMerged = em.merge(entity);
			em.getTransaction().commit();
			LOGGER.info("Entity " + entity + " merged!");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			String errorText = "Error merging " + entity + ": "
					+ ex.getMessage();
			LOGGER.error(errorText);
			ex.printStackTrace();
			throw new Exception(errorText);
		} finally {
			LOGGER.info("GenericDao.merge.finally");
			em.close();
		}

		return entityMerged;
	}

	/**
	 * {@inheritDoc}
	 */
	public T mergeInTx(final T entity) throws Exception {
		getEm();
		T entityMerged = null;
		try {
			entityMerged = em.merge(entity);
			LOGGER.info("Entity " + entity + " merged!");
		} catch (Exception ex) {
			String errorText = "Error merging in Transaction " + entity + ": "
					+ ex.getMessage();
			LOGGER.error(errorText);
			ex.printStackTrace();
			throw new Exception(errorText);
		}
		return entityMerged;
	}

	/**
	 * {@inheritDoc}
	 */
	public void startTransaction() {
		getEm();
		em.getTransaction().begin();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void commitTransaction() {
		getEm();
		em.getTransaction().commit();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void rollbackTransaction() {
		getEm();
		em.getTransaction().rollback();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void endTransaction() {
		getEm();
		em.getTransaction().begin();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void closeEm() {
		getEm();
		em.close();
	}

	/**
	 * Get persistent Class from Parameter.
	 * 
	 * @return Class<T> - persistent Class.
	 */
	private Class<T> getPersistentClass() {
		@SuppressWarnings("unchecked")
		Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass().
				getGenericSuperclass()).getActualTypeArguments() [0];
		return persistentClass;
	}
	
	/**
	 * Check for insert or update.
	 * If id == 0, Insert, else Update.
	 * @param entity T - concrete Enbtity.
	 * @return boolean - true is insert, false is update.
	 * @throws Exception ex.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private boolean isInsert(final T entity) throws Exception {
		Class[] noparams = {};
		Object[] noObjParams = {};
		Class<T> persistentClass = (Class<T>) entity.getClass();
		Method m = persistentClass.getDeclaredMethod("getId", noparams);
		Integer result = (Integer) m.invoke(entity, noObjParams);
		return result == 0;
	}
}
