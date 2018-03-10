package org.hni.cardloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.inject.Inject;

import org.hni.cardloader.processor.ProviderProcessor;
import org.hni.cardloader.spring.ApplicationContextLoader;
import org.hni.payment.om.PaymentInstrument;
import org.hni.payment.service.PaymentInstrumentService;
import org.hni.provider.om.Provider;
import org.hni.provider.service.ProviderService;
import org.hni.security.EncryptionException;
import org.hni.security.service.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

public class CardLoader {
	private static final Logger logger = LoggerFactory.getLogger(CardLoader.class);
	private static final String CARD_ENCRYPTION_KEYNAME = "signkey";
	
	private static final String[] fieldMapping = {
			 "provider"
			,"cardType"
			,"cardSerialId"
			,"cardNumber"
			,"status"
			,"originalBalance"
			,"balance"
			,"pinNumber"
	};
	private CellProcessor[] processors = {
			 new ProviderProcessor()
			,new Trim()
			,new Trim()
			,new Trim()
			,new Trim()
			,new ParseDouble()
			,new ParseDouble()
			,new Trim()
	};
	
	@Inject private ProviderService providerService;
	@Inject private PaymentInstrumentService paymentInstrumentService;
	@Inject private EncryptionService encryptionService;
	
	public CardLoader() {
	}

	public void init() {
		((ProviderProcessor)processors[0]).setProviderService(providerService);
	}
	
	public static void main(String[] args) {
		if (args.length < 2) {
			printhelp();
			System.exit(0);
		}
		CardLoader loader = new CardLoader();
		// cause Spring to create and inject our dependencies
		new ApplicationContextLoader().load(loader, "applicationContext.xml");
		try {
			loader.init();
			loader.loadCards(args[0], args[1]);
			loader.validate(args[0]);
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			System.exit(0);
		}
	}
	
	public void validate(String keyName) throws EncryptionException {
		for(PaymentInstrument pi : paymentInstrumentService.getAll()) {
			logger.info(String.format("%d - %s -%s", pi.getId(), encryptionService.decrypt(keyName, pi.getCardNumber())
			,encryptionService.decrypt(keyName, pi.getPinNumber())));
		}
	}
	public void loadCards(String keyName, String loadFilename) throws IOException, EncryptionException {
		CsvBeanReader beanReader = null;
		logger.info("Loading cards from "+loadFilename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(loadFilename)));
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);
			
			//beanReader.configureBeanMapping(PaymentInstrument.class, fieldMapping);
			
			PaymentInstrument card;
			while ( (card = beanReader.read(PaymentInstrument.class, fieldMapping, processors)) != null) {
				logger.info(String.format("Got %s with serial %s", card.getCardType(), card.getCardSerialId()));
				card.setCardNumber(encryptionService.encrypt(keyName, card.getCardNumber()));
				card.setPinNumber((encryptionService.encrypt(keyName, card.getPinNumber())));
				
				paymentInstrumentService.save(card);
			}
		} finally {
			beanReader.close();
		}
	}
	
	public static void printhelp() {
		System.out.println("Usage: <keyname> <filename>");
	}

}
