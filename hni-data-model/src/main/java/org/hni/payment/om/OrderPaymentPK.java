package org.hni.payment.om;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hni.order.om.Order;

@Embeddable
public class OrderPaymentPK implements Serializable {

	private static final long serialVersionUID = 2751060551106019525L;

	@ManyToOne
	@JoinColumn(name="order_id", referencedColumnName = "id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="payment_instrument_id", referencedColumnName = "id")
	private PaymentInstrument paymentInstrument;
	
	//@Column(name="order_id") private Long orderId;
	//@Column(name="payment_instrument_id") private Long paymentInstrumentId;

	public OrderPaymentPK() {}
	public OrderPaymentPK(Order order, PaymentInstrument paymentInstrument) {
		this.order = order;
		this.paymentInstrument = paymentInstrument;
	}
	
	@ManyToOne
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne
	public PaymentInstrument getPaymentInstrument() {
		return paymentInstrument;
	}
	public void setPaymentInstrument(PaymentInstrument paymentInstrument) {
		this.paymentInstrument = paymentInstrument;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((paymentInstrument == null) ? 0 : paymentInstrument.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderPaymentPK other = (OrderPaymentPK) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (paymentInstrument == null) {
			if (other.paymentInstrument != null)
				return false;
		} else if (!paymentInstrument.equals(other.paymentInstrument))
			return false;
		return true;
	}
	
	
	
}
