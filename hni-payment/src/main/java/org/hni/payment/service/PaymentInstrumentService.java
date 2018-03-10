package org.hni.payment.service;

import java.util.Collection;

import org.hni.common.service.BaseService;
import org.hni.payment.om.PaymentInstrument;
import org.hni.provider.om.Provider;

public interface PaymentInstrumentService extends BaseService<PaymentInstrument > {

	/**
	 * Returns 1 or more PaymentInstrument records for the given provider/amount
	 * that can be used to pay for an order.
	 * @param provider
	 * @param amount
	 * @return
	 */
	Collection<PaymentInstrument> with(Provider provider, Double amount);
}
