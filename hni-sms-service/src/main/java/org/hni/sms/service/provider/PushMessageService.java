/**
 * 
 */
package org.hni.sms.service.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.hni.admin.service.dto.CustomSMSMessageDto;
import org.hni.admin.service.dto.CustomSMSMessageResponseDto;
import org.hni.admin.service.dto.VolunteerDto;
import org.hni.common.Constants;
import org.hni.order.om.Order;
import org.hni.order.om.OrderItem;
import org.hni.sms.service.model.SmsMessage;
import org.hni.sms.service.provider.om.SmsProvider;
import org.hni.user.dao.UserDAO;
import org.hni.user.om.User;
import org.hni.user.service.VolunteerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Rahul
 *
 */
@Component
public class PushMessageService {

	@Inject
	private SMSProvider provider;

	@Inject
	@Named("defaultVolunteerService")
	private VolunteerService volunteerService;

	@Value("#{hniProperties['hni.homepage']}")
	private String hniHomepage;

	@Inject
	private UserDAO userDAO;

	public void createPushMessageAndSend(Order order) {

		String state = order.getProviderLocation().getAddress().getState();

		List<VolunteerDto> volunteersList = volunteerService.getVolunteerByState(state, true);

		SmsProvider smsProvider = SmsServiceLoader.getProviders().get(state.toUpperCase());
		if(volunteersList != null){
			if (!volunteersList.isEmpty()) {
				for (VolunteerDto volunteer : volunteersList) {
					provider.get(smsProvider.getProviderName()).sendMessage(prepareMessage(volunteer, order, smsProvider));
				}
			}
		}
	}

	private SmsMessage prepareMessage(VolunteerDto voulunteer, Order order, SmsProvider smsProvider) {
		SmsMessage message = new SmsMessage();

		message.setToNumber("+1" + voulunteer.getPhoneNumber());
		// TODO : Set volunteer name in message
		message.setFromNumber("+1" + smsProvider.getLongCode());
		message.setText(getMessageText(order));

		return message;
	}

	private SmsMessage createMessageObject(String message, String from, String to) {
		SmsMessage messageObject = new SmsMessage();
		messageObject.setToNumber("+1" + to);
		messageObject.setFromNumber("+1" + from);
		messageObject.setText(message);
		
		return messageObject;
	}
	private String getMessageText(Order order) {
		StringBuilder builder = new StringBuilder();
		builder.append("Please place the order for ");
		String userName = order.getUser().getFirstName() + " " + order.getUser().getLastName().substring(0, 1).toUpperCase() + ".";
		builder.append(userName);	
		builder.append(" Order ID : HNI");
		builder.append(order.getId());
		builder.append(String.valueOf(order.getProviderLocation().getAddress().getState()).toUpperCase());
		builder.append(" Order Item : ");
		OrderItem orderItem = order.getOrderItems().iterator().next();
		builder.append(orderItem.getMenuItem().getName());
		builder.append(" Total Price : $");
		builder.append(order.getSubTotal());
		// Below items to be checked before final changes
		builder.append(" www.hungernotimpossible.com");
		
		return builder.toString();
	}
	
	public boolean sendMessage(String message, String from, String to) {
		
		provider.get(ServiceProvider.TWILIO).sendMessage(createMessageObject(message, from, to));
		
		return false;
	}
	
	public String getProviderNumberForState(String stateCode) {
		SmsProvider smsProvider = SmsServiceLoader.getProviders().get(stateCode.toUpperCase());
		if (smsProvider == null) {
			return SmsServiceLoader.getProviders().get("MO").getLongCode();
		} else {
			return smsProvider.getLongCode();
		}
	}
	
	public CustomSMSMessageResponseDto sendCustomMessage(CustomSMSMessageDto customSMSMessage){
		
		List<Long> userIds = customSMSMessage.getUserId();
		String messageContent = customSMSMessage.getMessage();
		String toPhoneNumber = new String();
		CustomSMSMessageResponseDto customResponse = new CustomSMSMessageResponseDto();
		Long totalSuccess = 0L;
		Long totalFailed = 0L;
		
		Map<String, String> messageStatus = new HashMap<>();
		for(Long userId : userIds){
			try{
				User user = userDAO.get(userId);
				toPhoneNumber = user.getMobilePhone();
				if(user != null){
					String state = user.getAddresses().iterator().next().getState();
					SmsProvider smsProvider = SmsServiceLoader.getProviders().get(state);
					
					if(smsProvider != null){
						provider.get(smsProvider.getProviderName()).sendMessage(createMessageObject(messageContent, smsProvider.getLongCode(), toPhoneNumber));
						++totalSuccess;
						messageStatus.put(toPhoneNumber, Constants.SUCCESS);
					}else {
						++totalFailed;
						messageStatus.put(toPhoneNumber, Constants.ERROR);
					}
				}
			}catch(Exception e){
				++totalFailed;
				messageStatus.put(toPhoneNumber, Constants.ERROR);
			}
		}
		customResponse.setTotalSuccess(totalSuccess);
		customResponse.setTotalFailed(totalFailed);
		customResponse.setDetails(messageStatus);
		return customResponse;
	}
	
}
