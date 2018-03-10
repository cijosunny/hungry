package org.hni.payment.service;

import org.hni.common.service.AbstractService;
import org.hni.payment.dao.HniAuditDAO;
import org.hni.payment.om.HniAudit;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultHniAuditService extends AbstractService<HniAudit> 
	implements HniAuditService {

	private HniAuditDAO hniAuditDao;
	
	public DefaultHniAuditService(HniAuditDAO hniAuditDao) {
		super(hniAuditDao);
		this.hniAuditDao = hniAuditDao;
	}

	
}
