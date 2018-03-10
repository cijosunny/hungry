package org.hni.payment.dao;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.order.om.Order;
import org.hni.payment.om.OrderPayment;
import org.springframework.stereotype.Component;

@Component
public class DefaultOrderPaymentDAO extends AbstractDAO<OrderPayment> implements OrderPaymentDAO {

	protected DefaultOrderPaymentDAO() {
		super(OrderPayment.class);
	}

	@Override
	public Collection<OrderPayment> paymentsFor(Order order) {
		try {
			Query q = em.createQuery("SELECT x FROM OrderPayment x WHERE x.id.order.id = :orderId")
				.setParameter("orderId", order.getId());
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}

	}
}
