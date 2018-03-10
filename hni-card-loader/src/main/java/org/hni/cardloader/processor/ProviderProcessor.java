package org.hni.cardloader.processor;

import org.hni.provider.om.Provider;
import org.hni.provider.service.ProviderService;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.util.CsvContext;

public class ProviderProcessor extends CellProcessorAdaptor {

	private ProviderService providerService;
	
	public ProviderProcessor() {}
	public ProviderProcessor(ProviderService providerService) {
		this.providerService = providerService;
	}
	
	public void setProviderService(ProviderService providerService) {
		this.providerService = providerService;
	}
	
	@Override
	public Object execute(Object value, CsvContext context) {
		
		Long id = Long.parseLong(value.toString());
		Provider provider = providerService.get(id);
		if ( null == provider) { // TODO: or throw exception?
			provider = new Provider(id);
		}
		return provider;
	}

}
