package org.hni.security.dao;

import org.hni.common.dao.AbstractDAO;
import org.hni.common.om.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultRoleDAO extends AbstractDAO<Role> implements RoleDAO {
	private static final Logger logger = LoggerFactory.getLogger(RoleDAO.class);
	public DefaultRoleDAO() {
		super(Role.class);		
	}
	
	
}
