package com.localization.manager;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import localization.data.entity.LocationDatabaseObject;

import com.localization.util.ServerConfiguration;

public class LocalizationDataManager {
	private static final String PERSISTENCE_UNIT_NAME = "Localization Database Management";
	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager entityManager = null;
	
	public static void forceReloadSingleton() {
		entityManagerFactory = null;
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			Map props = new HashMap();
			props.put("javax.persistence.jdbc.url", ServerConfiguration.load().url);
			props.put("javax.persistence.jdbc.user", ServerConfiguration.load().username);
			props.put("javax.persistence.jdbc.driver", ServerConfiguration.load().driver);
			props.put("javax.persistence.jdbc.password", ServerConfiguration.load().password);
				entityManagerFactory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, props);

		}
		return entityManagerFactory;
	}
	
	public static void refreshCache(Class className){
		entityManagerFactory.getCache().evict(className);
	}
	
	public static void refreshAllCache(){
		entityManagerFactory.getCache().evictAll();
	}
	
	/**
	 * create entity manager sigleton pattern
	 * 
	 * @return
	 */
	public static EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}

	/**
	 * begin transaction
	 */
	public static void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	/**
	 * commit transaction
	 */
	public static void commitTransaction() {
		getEntityManager().getTransaction().commit();
	}

}
