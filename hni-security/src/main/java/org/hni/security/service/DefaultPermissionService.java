package org.hni.security.service;

import javax.inject.Inject;

import org.hni.common.service.AbstractService;
import org.hni.security.dao.PermissionDAO;
import org.hni.security.om.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultPermissionService extends AbstractService<Permission> implements PermissionService {
	private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);
	private PermissionDAO permissionDao;

	@Inject
	public DefaultPermissionService(PermissionDAO permissionDao) {
		super(permissionDao);
		this.permissionDao = permissionDao;
	}

}