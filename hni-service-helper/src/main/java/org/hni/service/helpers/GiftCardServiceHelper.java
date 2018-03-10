package org.hni.service.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hni.admin.service.dto.PaymentInstrumentDto;
import org.hni.common.Constants;
import org.hni.common.HNIUtils;
import org.hni.payment.om.HniAudit;
import org.hni.payment.om.PaymentInstrument;
import org.hni.payment.service.GiftCardService;
import org.hni.payment.service.HniAuditService;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class GiftCardServiceHelper extends AbstractServiceHelper {
	
	private static final Logger _LOGGER = LoggerFactory.getLogger(GiftCardServiceHelper.class);
	
	@Inject
	private GiftCardService giftCardService;
	
	@Inject
	private HniAuditService hniAuditService;

	public Map<String, Object> getGiftCardsFor(Long providerId){
		_LOGGER.info("Invoking method to retrieve gift cards, provider: "+providerId);
		Map<String, Object> response = new HashMap();
		try{
			List<PaymentInstrumentDto> exisitingGiftCards = (List<PaymentInstrumentDto>) giftCardService.getGitCardsFor(providerId);
			_LOGGER.info("Successfully retrieved giftCards.");
			response.put("headers",HNIUtils.getReportHeaders(100, true));
			response.put("data", exisitingGiftCards);
			response.put(Constants.MESSAGE,Constants.SUCCESS);
		} catch(Exception e){
			_LOGGER.info("Failed to retrieve giftCards. "+e.getMessage());
			response.put(Constants.MESSAGE, "Error occured while retrieval");
		}
		
		return response;
	}
	
	public Map<String,Object> deactivateGiftCard(Long giftCardId){
		Map<String, Object> response = new HashMap();
		_LOGGER.info("Invoking method to deactivate giftCard : "+ giftCardId);
		try{
			PaymentInstrument paymentInstrument = giftCardService.get(giftCardId);
			if(paymentInstrument != null){
				_LOGGER.info("Giftcard found :  "+ giftCardId);
				paymentInstrument.setStatus("I");
				giftCardService.update(paymentInstrument);
				_LOGGER.info("Gift card deactivated successfully ");
				response.put(Constants.MESSAGE,"Gift card deactivated successfully.");
			} else {
				_LOGGER.info("Giftcard not found :  "+ giftCardId);
				response.put(Constants.MESSAGE,"No information found.");
			}
		} catch(Exception e){
			_LOGGER.info("Failed to deactivate gift card: "+ e);
			response.put(Constants.MESSAGE, "Failed to deactivate gift card.");
		}
		
		return response;
	}
	
	public Map<String,Object> activateGiftCard(Long giftCardId){
		Map<String, Object> response = new HashMap();
		_LOGGER.info("Invoking method to activate giftCard : "+ giftCardId);
		try{
			PaymentInstrument paymentInstrument = giftCardService.get(giftCardId);
			if(paymentInstrument != null){
				_LOGGER.info("Giftcard found :  "+ giftCardId);
				paymentInstrument.setStatus("A");
				giftCardService.update(paymentInstrument);
				_LOGGER.info("Gift card activated successfully ");
				response.put(Constants.MESSAGE,"Gift card Activated successfully.");
			} else {
				_LOGGER.info("Giftcard not found :  "+ giftCardId);
				response.put(Constants.MESSAGE,"No information found.");
			}
		} catch(Exception e){
			_LOGGER.info("Failed to deactivate gift card: "+ e);
			response.put(Constants.MESSAGE, "Failed to Activate gift card.");
		}
		
		return response;
	}
	
	public Map<String, Object> updateGiftCards(List<PaymentInstrumentDto> newCards){
		Map<String, Object> response = new HashMap();
		_LOGGER.info("Invoking method to update giftCards : "+ newCards);
		try{
			for(PaymentInstrumentDto newCard : newCards){
				PaymentInstrument paymentInstrument = giftCardService.get(newCard.getId());
				paymentInstrument.setCardNumber(newCard.getCardNumber());
				paymentInstrument.setCardSerialId(newCard.getCardSerialId());
				paymentInstrument.setOriginalBalance(newCard.getOriginalBalance());
				paymentInstrument.setBalance(newCard.getBalance());
				paymentInstrument.setStateCode(newCard.getStateCode());
				giftCardService.update(paymentInstrument);
			}
			_LOGGER.info("Gift card(s) updated.");
			response.put("data", "Gift card(s) updated.");
			response.put(Constants.MESSAGE, Constants.SUCCESS);
			
		} catch(Exception e){
			_LOGGER.info("Failed to update gift card(s).");
			response.put(Constants.MESSAGE, Constants.ERROR);
			response.put("data", "Failed to update gift card(s).");
		}
		
		return response;
	}
	
	public Map<String, Object> saveGiftCards(PaymentInstrument paymentInstrument, User user){
		_LOGGER.info("Invoking method to save new giftCard "+paymentInstrument);
		Map<String, Object> response = new HashMap();
		try{
			Double balance = paymentInstrument.getOriginalBalance();
			paymentInstrument.setAllowTopup(true);
			paymentInstrument.setBalance(balance);
			paymentInstrument.setCardType("gift");
			paymentInstrument.setCreatedBy(user);
			paymentInstrument.setCreatedDate(new Date());
			paymentInstrument.setStatus("A");
			
			if(paymentInstrument.getCardSerialId() == null || paymentInstrument.getCardSerialId().equals("")){
				_LOGGER.info("Generating new serial number");
				Long providerId = paymentInstrument.getProvider().getId();
				Long newSerialNumber = getNewSerialNumber(providerId);
				paymentInstrument.setCardSerialId(newSerialNumber.toString());
				_LOGGER.info("New serial number generated : "+ newSerialNumber);
			}
			
			_LOGGER.info("Saving new gift card...");
			giftCardService.save(paymentInstrument);
			
			HniAudit hniAudit = new HniAudit();
			hniAudit.setPaymentInstrument(paymentInstrument);
			hniAudit.setAction(Constants.GIFTCARD_CREATE);
			hniAudit.setActionAmount(paymentInstrument.getOriginalBalance());
			hniAudit.setNetBalance(paymentInstrument.getOriginalBalance());
			hniAudit.setPreviousAmount(0D);
			hniAudit.setCreatedBy(user);
			hniAudit.setCreatedDate(new Date());
			hniAudit.setModifiedBy(user);
			hniAudit.setModifiedDate(new Date());
			
			hniAuditService.save(hniAudit);
			_LOGGER.info("Giftcard saved and Audit table updated.");
			
			response.put("data", "Gift card added.");
			response.put(Constants.MESSAGE, Constants.SUCCESS);
			
		} catch(Exception e){
			response.put(Constants.MESSAGE, Constants.ERROR);
			response.put("data", "Failed to add new gift card.");
		}
		
		
		return response;
	}
	
	private Long getNewSerialNumber(Long providerId){
		List<String> existingCards = giftCardService.getSerialCardNumbers(providerId);
		Long newSerialNumber = new Long(0);
		for(String giftCard : existingCards){
			Long cardSerialId = Long.valueOf(giftCard);
			if(cardSerialId > newSerialNumber){
				newSerialNumber = cardSerialId;
			}
		}
		return newSerialNumber+1;
	}
	
	public Map<String, Object> getGiftCards(Long giftCardId){
		Map<String, Object> response = new HashMap();
		_LOGGER.info("Invoking method to retrieve giftCard : "+giftCardId);
		try{
			PaymentInstrument paymentInstrument = giftCardService.get(giftCardId);
			_LOGGER.info("Giftcard found");
			response.put("data", toGiftCardJson(paymentInstrument));
			response.put(Constants.MESSAGE, Constants.SUCCESS);
			
		} catch(Exception e){
			_LOGGER.info("Failed to add new gift card."+e);
			response.put(Constants.MESSAGE, "Failed to retrieve gift card.");
		}
		
		return response;
	}
	
	private PaymentInstrumentDto toGiftCardJson(PaymentInstrument paymentInstrument){
		PaymentInstrumentDto paymentInstrumentDto = new PaymentInstrumentDto();
		
		paymentInstrumentDto.setId(paymentInstrument.getId());
		paymentInstrumentDto.setCardNumber(paymentInstrument.getCardNumber());
		paymentInstrumentDto.setCardSerialId(paymentInstrument.getCardSerialId());
		paymentInstrumentDto.setStateCode(paymentInstrument.getStateCode());
		
		return paymentInstrumentDto;
	}
	
	public Map<String, Object> rechargeGiftCard(Long id, Long amount, User user){
		Map<String, Object> response = new HashMap();
		_LOGGER.info("Invoking method to recharge giftCard : "+id+", amount : "+amount);
		try{

			PaymentInstrument paymentInstrument = giftCardService.get(id);
			if(paymentInstrument!=null){
				_LOGGER.info("GiftCard found.");
				Double newOriginalBalance = amount + paymentInstrument.getOriginalBalance();
				Double newBalance = amount + paymentInstrument.getBalance();
				Double originalBalance  = paymentInstrument.getOriginalBalance();
				
				paymentInstrument.setOriginalBalance(newOriginalBalance);
				paymentInstrument.setBalance(newBalance);
				
				giftCardService.update(paymentInstrument);
				
				HniAudit hniAudit = new HniAudit();
				hniAudit.setPaymentInstrument(paymentInstrument);
				hniAudit.setAction(Constants.GIFTCARD_RECHARGE);
				hniAudit.setActionAmount(Double.valueOf(amount.toString()));
				hniAudit.setNetBalance(paymentInstrument.getBalance());
				hniAudit.setPreviousAmount(originalBalance);
				hniAudit.setModifiedBy(user);
				hniAudit.setModifiedDate(new Date());
				
				hniAuditService.save(hniAudit);
				
				_LOGGER.info("Audit saved and GiftCard recharged.");
				response.put("data", "Recharge Successfull.");
				response.put(Constants.MESSAGE, Constants.SUCCESS);
			} else {
				_LOGGER.info("GiftCard not found.");
				response.put("data", "No card found.");
				response.put(Constants.MESSAGE, Constants.ERROR);
			}
			
		} catch(Exception e){
			response.put("data", "Recharge Failed.");
			response.put(Constants.MESSAGE, Constants.ERROR);
		}
		
		return response;
	}
}
