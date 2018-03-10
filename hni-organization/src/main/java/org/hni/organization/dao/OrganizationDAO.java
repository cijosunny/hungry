package org.hni.organization.dao;

import org.hni.common.dao.BaseDAO;
import org.hni.organization.om.Organization;

public interface OrganizationDAO extends BaseDAO<Organization> {

	boolean isAlreadyExists(Organization org);

}
