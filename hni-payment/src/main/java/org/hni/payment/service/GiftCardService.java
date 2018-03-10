package org.hni.payment.service;

import java.util.List;

import org.hni.admin.service.dto.PaymentInstrumentDto;
import org.hni.common.service.BaseService;
import org.hni.payment.om.PaymentInstrument;

public interface GiftCardService extends BaseService<PaymentInstrument> {

	List<PaymentInstrumentDto> getGitCardsFor(Long providerId);
	List<String> getSerialCardNumbers(Long providerId);
}
