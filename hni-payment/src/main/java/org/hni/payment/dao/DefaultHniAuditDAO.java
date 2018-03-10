package org.hni.payment.dao;


import org.hni.common.dao.AbstractDAO;
import org.hni.payment.om.HniAudit;
import org.springframework.stereotype.Component;

@Component
public class DefaultHniAuditDAO extends AbstractDAO<HniAudit> implements
		HniAuditDAO {

	protected DefaultHniAuditDAO() {
		super(HniAudit.class);
	}
}
