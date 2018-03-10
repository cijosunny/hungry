package org.hni.common.email.service;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.annotation.PostConstruct;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.hni.admin.service.converter.HNIConverter;
import org.hni.user.om.Invitation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Component
public class EmailComponent {

	private static final String CLIENT_INVITATION_TEXT_MESSAGE_INFO = "<br /> Once you have completed the registration, you will be able to text to this number everyday from June 1st to September 1st to order meals: <b> %s </b>.<br /><br />To place an order text: <b>HUNGRY</b> <br /><br />For questions email hunger@notimpossiblelabs.com";
	// Constants
	private static final String FORWARD_SLASH = "/";
	private static final String RELATED = "related";
	private static final String TEXT_HTML = "text/html";
	private static final String CONTENT_ID = "Content-ID";
	private static final String HTML_FOOTER_TAG = "<footer>";
	private static final String IMAGE_NOT_IMPOSSIBLE_LOGO_PNG = "image/not_impossible_logo.png";
	private static final String MAIL_IMG_FOOTER = "</p><br><img width=\"100%\" src=\"cid:footer\">";
	private static final String HTML_PARAGRAPH = "<p>";
	private static final String _SPACE = " ";
	private static final String USER = "User";
	private static final String PARTICIPANT = "Participant";
	private static final String CLIENT = "Client";
	private static final String VOLUNTEER = "Volunteer";
	private static final String NGO = "NGO";
	private static final String STR_COMMA = ",";
	private static final String HI = "Hi ";
	private static final String HTML_BR_BR = "<br/><br/>";

	@Value("#{hniProperties['mail.smtp.host']}")
	private String smtpHost;
	@Value("#{hniProperties['mail.smtp.socketFactory.port']}")
	private String smtpSocketFactoryPort;
	@Value("#{hniProperties['mail.smtp.socketFactory.class']}")
	private String smtpSocket;
	@Value("#{hniProperties['mail.smtp.auth']}")
	private String smtpAuth;
	@Value("#{hniProperties['mail.smtp.port']}")
	private String smtpPort;
	@Value("#{hniProperties['mail.auth.email']}")
	private String authEmail;
	@Value("#{hniProperties['mail.auth.password']}")
	private String authPassword;

	@Value("#{hniProperties['mail.from.address']}")
	private String fromAddress;
	@Value("#{hniProperties['mail.from.name']}")
	private String fromName;

	@Value("#{hniProperties['mail.activation.url']}")
	private String activateURL;
	@Value("#{hniProperties['mail.template.sub']}")
	private String emailSubTemplate;
	@Value("#{hniProperties['mail.template.body']}")
	private String emailBodyTemplate;
	@Value("#{hniProperties['mail.template.footer']}")
	private String emailFooterTemplate;

	private final Properties props = new Properties();

	Session session = null;
	DataSource footerDs = null;

	@PostConstruct
	public void initialize() {
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.socketFactory.port", smtpSocketFactoryPort);
		props.put("mail.smtp.socketFactory.class", smtpSocket);
		props.put("mail.smtp.auth", smtpAuth);
		props.put("mail.smtp.port", smtpPort);

		// Creating new session object
		session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(authEmail, authPassword);
			}
		});

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = EmailComponent.class.getClassLoader();
		}

		footerDs = new URLDataSource(classLoader.getResource(IMAGE_NOT_IMPOSSIBLE_LOGO_PNG));
	}

	public boolean sendEmail(Invitation invitation, String role, String phoneNumber)
			throws AddressException, MessagingException, JsonParseException, JsonMappingException, IOException {

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress, fromName));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(invitation.getEmail()));

		message.setSubject(capitalize(getInviteName(role)) + _SPACE + emailSubTemplate);

		String contentText = getEmailText(role, invitation.getInvitationCode(), invitation.getMessage(), phoneNumber);
		MimeMultipart multipart = new MimeMultipart(RELATED);

		// first part (the html)
		BodyPart messageBodyPart = new MimeBodyPart();
		String htmlText = HTML_PARAGRAPH + contentText + MAIL_IMG_FOOTER;
		messageBodyPart.setContent(htmlText, TEXT_HTML);
		multipart.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart();

		messageBodyPart.setDataHandler(new DataHandler(footerDs));
		messageBodyPart.setHeader(CONTENT_ID, HTML_FOOTER_TAG);
		multipart.addBodyPart(messageBodyPart);
		// put everything together
		message.setContent(multipart);
		Transport.send(message);
		return true;
	}

	private String getEmailText(String userType, String code, String invitationMessage, String phoneNumber)
			throws JsonParseException, JsonMappingException, IOException {
		StringBuilder emailTextBuilder = new StringBuilder(50);

		emailTextBuilder.append(HI + getInviteName(userType) + STR_COMMA);

		emailTextBuilder.append(emailBodyTemplate);

		if (invitationMessage != null && !invitationMessage.isEmpty()) {
			emailTextBuilder.append(HTML_BR_BR + invitationMessage);
		}
		emailTextBuilder.append(HTML_BR_BR);
		
		if (userType.equalsIgnoreCase(CLIENT)) {
			emailTextBuilder.append(String.format(CLIENT_INVITATION_TEXT_MESSAGE_INFO,HNIConverter.convertPhoneNumberToUiFormat(phoneNumber)));
			emailTextBuilder.append(HTML_BR_BR);
		}

		emailTextBuilder.append(emailFooterTemplate);
		
		return String.format(emailTextBuilder.toString(), getInviteName(userType),
				activateURL + userType + FORWARD_SLASH + code);
	}

	private String getInviteName(String type) {
		if (NGO.equalsIgnoreCase(type)) {
			return NGO;
		} else if (VOLUNTEER.equalsIgnoreCase(type)) {
			return VOLUNTEER;
		} else if (CLIENT.equalsIgnoreCase(type)) {
			return PARTICIPANT;
		} else {
			return USER;
		}

	}

	private String capitalize(final String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
}
