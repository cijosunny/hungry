package org.hni.payment.service;

import java.util.List;

import org.hni.admin.service.dto.PaymentInstrumentDto;
import org.hni.common.service.AbstractService;
import org.hni.payment.dao.GiftCardDAO;
import org.hni.payment.om.PaymentInstrument;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultGiftCardService extends AbstractService<PaymentInstrument>
		implements GiftCardService {
	
	private GiftCardDAO giftCardDao;

	public DefaultGiftCardService(GiftCardDAO giftCardDao) {
		super(giftCardDao);
		this.giftCardDao = giftCardDao;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<PaymentInstrumentDto> getGitCardsFor(Long providerId) {
		return giftCardDao.getGitCardsFor(providerId);
	}

	@Override
	public List<String> getSerialCardNumbers(Long providerId) {
		return giftCardDao.getSerialCardNumbers(providerId);
	}

}
