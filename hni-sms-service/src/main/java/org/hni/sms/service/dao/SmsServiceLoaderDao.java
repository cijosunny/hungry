package org.hni.sms.service.dao;


import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.sms.service.provider.om.SmsProvider;
import org.springframework.stereotype.Component;


/**
 * The DAO for SmsServiceLoader
 */
@Component
public class SmsServiceLoaderDao extends AbstractDAO<SmsProvider> {
    

	public SmsServiceLoaderDao() {
		super(SmsProvider.class);		
	}


	public List<SmsProvider> getAllProvider() {
			try {
			Query q = em.createQuery("SELECT x FROM SmsProvider x order by x.stateCode");
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	
	}


	public List<SmsProvider> getAllProviderBySate(String stateCode) {
		try {
			Query q = em.createQuery("SELECT x FROM SmsProvider x where x.stateCode =:stateCode").setParameter("stateCode", stateCode);
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}
    
    
}
