package org.hni.payment.dao;

import java.util.Collection;

import org.hni.common.dao.BaseDAO;
import org.hni.payment.om.PaymentInstrument;
import org.hni.provider.om.Provider;

public interface PaymentInstrumentDAO extends BaseDAO<PaymentInstrument> {

	Collection<PaymentInstrument> with(Provider provider, String state);
}
