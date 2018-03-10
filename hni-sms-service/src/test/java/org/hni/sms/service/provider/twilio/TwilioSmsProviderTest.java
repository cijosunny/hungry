/**
 * 
 */
package org.hni.sms.service.provider.twilio;

import org.hni.sms.service.model.SmsMessage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * @author Rahul
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:test-applicationContext.xml" })
//@Transactional
public class TwilioSmsProviderTest {

	private String TWILIO_TOKEN = "5a66ce9bc9e951392b9ffea23d56a88a";
	private String TWILIO_USER = "AC72a84815a5c06c04e3003c1985e70ca4";

	public TwilioSmsProviderTest() {
	}

	@Ignore
	public void testSms() {
		Twilio.init(TWILIO_USER, TWILIO_TOKEN);

		SmsMessage message = new SmsMessage();
		message.setFromNumber("2153020127");
		message.setToNumber("+13143000305");
		message.setText("MEAL");
		
		Message messageReply = Message.creator(new PhoneNumber(message.getToNumber()), new PhoneNumber(message.getFromNumber()), message.getText()).create();
		
		System.out.println(messageReply.getBody());
		System.out.println(" : " + messageReply.getSid());

	}
}
