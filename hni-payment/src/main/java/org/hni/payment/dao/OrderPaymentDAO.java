package org.hni.payment.dao;

import java.util.Collection;

import org.hni.common.dao.BaseDAO;
import org.hni.order.om.Order;
import org.hni.payment.om.OrderPayment;

public interface OrderPaymentDAO extends BaseDAO<OrderPayment> {

	Collection<OrderPayment> paymentsFor(Order order);

}
