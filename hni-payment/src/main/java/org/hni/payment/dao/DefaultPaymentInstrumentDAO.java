package org.hni.payment.dao;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.payment.om.PaymentInstrument;
import org.hni.provider.om.Provider;
import org.springframework.stereotype.Component;

@Component
public class DefaultPaymentInstrumentDAO extends AbstractDAO<PaymentInstrument> implements PaymentInstrumentDAO {

	protected DefaultPaymentInstrumentDAO() {
		super(PaymentInstrument.class);
	}

	@Override
	public Collection<PaymentInstrument> with(Provider provider, String state) {
		try {
			Query q = em.createQuery("SELECT x FROM PaymentInstrument x WHERE x.provider.id = :providerId AND x.balance > 0 AND x.status = 'A' AND x.stateCode = :state ORDER BY x.lastUsedDatetime ASC")
				.setParameter("providerId", provider.getId())
				.setParameter("state", state);
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}

	}

}
