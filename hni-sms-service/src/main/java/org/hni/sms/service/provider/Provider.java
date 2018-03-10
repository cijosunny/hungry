/**
 * 
 */
package org.hni.sms.service.provider;

import java.util.List;

import org.hni.sms.service.model.SmsMessage;

/**
 * @author Rahul
 *
 */
public interface Provider {

	SmsMessage receiveMessage();
	String sendMessage(SmsMessage message);
	List<String> sendBulkMessage(List<SmsMessage> messages);
	boolean getSendStatus(String messageId);
	
}
