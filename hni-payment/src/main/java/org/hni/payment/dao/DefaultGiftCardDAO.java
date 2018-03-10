package org.hni.payment.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.admin.service.dto.PaymentInstrumentDto;
import org.hni.common.dao.AbstractDAO;
import org.hni.payment.om.PaymentInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultGiftCardDAO extends AbstractDAO<PaymentInstrument>
		implements GiftCardDAO {
	
	private static final Logger _LOGGER = LoggerFactory.getLogger(DefaultGiftCardDAO.class);

	protected DefaultGiftCardDAO() {
		super(PaymentInstrument.class);
		// TODO Auto-generated constructor stub
	}


	@Override
	public List<PaymentInstrumentDto> getGitCardsFor(Long providerId) {
		try {
			_LOGGER.info("Retrieving giftCards...");
			Query q = em.createQuery("SELECT x.balance, x.cardNumber, x.cardSerialId, x.status, x.originalBalance, x.stateCode, x.id FROM PaymentInstrument x WHERE x.provider.id = :providerId")
				.setParameter("providerId", providerId);
			List<Object[]> existingGiftCardList = q.getResultList();
			_LOGGER.info("GiftCards retrieved successfully.");
			List<PaymentInstrumentDto> newGiftCards = new ArrayList<>();
			if(existingGiftCardList.isEmpty()){
				return null;
			} else{
				for(Object[] u: existingGiftCardList){
					PaymentInstrumentDto paymentInstrumentDto = new PaymentInstrumentDto();
					paymentInstrumentDto.setBalance( (u[0]!=null) ? Double.valueOf(u[0].toString()) : null);
					paymentInstrumentDto.setCardNumber((u[1]!=null) ? u[1].toString() : null);
					paymentInstrumentDto.setCardSerialId((u[2]!=null) ? u[2].toString() : null);
					paymentInstrumentDto.setStatus((u[3]!=null) ? convertStatus(u[3].toString()) : null);
					paymentInstrumentDto.setOriginalBalance((u[4]!=null) ? Double.valueOf(u[4].toString()) : null);
					paymentInstrumentDto.setStateCode((u[5]!=null) ? u[5].toString() : null);
					paymentInstrumentDto.setId(Long.valueOf((u[6]!=null) ? u[6].toString() : null));
					newGiftCards.add(paymentInstrumentDto);
				}
			}
			return newGiftCards;
		} catch(NoResultException e) {
			_LOGGER.info("Failed to retrieve giftCards. "+e.getMessage());
			return Collections.emptyList();
		}

	}
	
	private String convertStatus(String status){
		if(status.equals("A")){
			return "Active";
		}
		return "Not Active";
	}


	@Override
	public List<String> getSerialCardNumbers(Long providerId) {
		try {
			_LOGGER.info("Generating new serial card number...");
			Query q = em.createQuery("SELECT x.cardSerialId FROM PaymentInstrument x WHERE x.provider.id = :providerId")
				.setParameter("providerId", providerId);
			return  q.getResultList();
			
		} catch(NoResultException e) {
			_LOGGER.info("Failed to generate serial number. "+e.getMessage());
			return Collections.emptyList();
		}
	}

}
