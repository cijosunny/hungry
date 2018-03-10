package org.hni.organization.service;

import javax.inject.Inject;

import org.hni.common.service.AbstractService;
import org.hni.organization.dao.OrganizationDAO;
import org.hni.organization.om.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultOrganizationService extends AbstractService<Organization> implements OrganizationService {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);
	private OrganizationDAO organizationDao;
	
	@Inject
	public DefaultOrganizationService(OrganizationDAO organizationDao) {
		super(organizationDao);
		this.organizationDao = organizationDao;
	}

	@Override
	public boolean isAlreadyExists(Organization org) {
		return organizationDao.isAlreadyExists(org);
	}
	
	
}
