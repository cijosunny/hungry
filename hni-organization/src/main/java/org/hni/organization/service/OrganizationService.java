package org.hni.organization.service;

import org.hni.common.service.BaseService;
import org.hni.organization.om.Organization;

public interface OrganizationService extends BaseService<Organization> {

	boolean isAlreadyExists(Organization org);

	
}
