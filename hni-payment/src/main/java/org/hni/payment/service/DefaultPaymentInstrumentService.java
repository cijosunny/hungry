package org.hni.payment.service;

import java.util.Collection;
import java.util.HashSet;

import javax.inject.Inject;

import org.hni.common.service.AbstractService;
import org.hni.payment.dao.PaymentInstrumentDAO;
import org.hni.payment.om.PaymentInstrument;
import org.hni.provider.om.Provider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultPaymentInstrumentService extends AbstractService<PaymentInstrument> implements PaymentInstrumentService {

	private PaymentInstrumentDAO paymentInstrumentDao;
	
	@Inject
	public DefaultPaymentInstrumentService(PaymentInstrumentDAO paymentInstrumentDao) {
		super(paymentInstrumentDao);
		this.paymentInstrumentDao = paymentInstrumentDao;
	}

	/**
	 * Return CARD, amount to use on this card
	 */
	@Override
	public Collection<PaymentInstrument> with(Provider provider, Double amount) {
		//TODO: change the MO to the respective order state value
		Collection<PaymentInstrument> providerCards = paymentInstrumentDao.with(provider, "MO");
		
		Collection<PaymentInstrument> paymentCards = new HashSet();
		Double cardTotal = 0.0;
		// this is horribly crude, but will work for v1
		for(PaymentInstrument paymentInstrument : providerCards) {
			cardTotal += paymentInstrument.getBalance();
			paymentCards.add(paymentInstrument);
			if (cardTotal >= amount) {
				break;
			}
		}
		//TODO: mark these cards used and add to the order
		return paymentCards;
	}

	/**
	 * This ORDER, this CARD, this amount
	 * for the OrderPayment
	 */
}
