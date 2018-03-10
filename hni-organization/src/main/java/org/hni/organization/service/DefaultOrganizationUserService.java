package org.hni.organization.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hni.common.om.Role;
import org.hni.organization.dao.UserOrganizationRoleDAO;
import org.hni.organization.om.HniServices;
import org.hni.organization.om.Organization;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.organization.om.UserOrganizationRolePK;
import org.hni.type.HNIRoles;
import org.hni.user.dao.UserDAO;
import org.hni.user.om.User;
import org.hni.user.service.DefaultUserService;
import org.springframework.stereotype.Component;

@Component("orgUserService")
public class DefaultOrganizationUserService extends DefaultUserService implements OrganizationUserService {

	public static final Long USER = 5L;

	private OrganizationService orgService;
	private UserOrganizationRoleDAO uorDao;
	
	@Inject
	public DefaultOrganizationUserService(UserDAO userDao, OrganizationService orgService, UserOrganizationRoleDAO uorDao) {
		super(userDao);
		this.orgService = orgService;
		this.uorDao = uorDao;
	}

    @Override
    public User save(User user, Organization org, Role role) {
        super.save(user);
        UserOrganizationRole uor = new UserOrganizationRole(user, org, role);
        uorDao.save(uor);
        return user;
    }

	@Override
	public Collection<User> getByRole(Organization org, Role role) {
		Set<User> set = new HashSet<>();
		Collection<UserOrganizationRole> userRolesList = uorDao.getByRole(org, role);
		for (UserOrganizationRole uor : userRolesList) {
			set.add(super.get(((UserOrganizationRolePK) uor.getId()).getUserId()));
		}
		return new ArrayList<User>(set);
	}

	@Override
	public UserOrganizationRole associate(User user, Organization org, Role role) {
		return uorDao.save(new UserOrganizationRole(user, org, role));
	}

	@Override
	public Collection<User> getAllUsers(Organization org) {
		return getByRole(org, Role.get(USER));
	}

	@Override
	public void delete(User user, Organization org) {
		for (UserOrganizationRole uor : uorDao.get(user)) {
			uorDao.delete(uor);
		}
	}

	@Override
	public void delete(User user, Organization org, Role role) {
		uorDao.delete(new UserOrganizationRole(user, org, role));
	}

	@Override
	public void lock(User user) {
		for(UserOrganizationRole uor : uorDao.get(user)) {
			uorDao.delete(uor);
		}
		user.setDeleted(true);
		user.setHashedSecret("LOCKED");
		save(user);
	}
	
	@Override
	public User archive(User user, Organization org) {
		// TODO
		return user;
	}

	@Override
	public Collection<Organization> get(User user) {
		Collection<Organization> orgs = new HashSet<>();
		for (UserOrganizationRole uor : uorDao.get(user)) {
			orgs.add(orgService.get(uor.getId().getOrgId()));
		}
		return orgs;
	}

	@Override
	public Collection<UserOrganizationRole> getUserOrganizationRoles(User user) {
		return uorDao.get(user);
	}

	@Override
	public Collection<User> byRole(Role role) {
		Collection<User> users = new HashSet<User>();
		for (UserOrganizationRole uor : uorDao.byRole(role)) {
			users.add(this.get(uor.getId().getUserId()));			
		}
		
		return users;
	}
	
	@Override
	public Collection<HniServices> getHniServices(Collection<UserOrganizationRole> userOrganizationRoles) {
		Collection<HniServices> hniServicesList = new ArrayList<>();
		for(UserOrganizationRole userRole: userOrganizationRoles){
			Collection<HniServices> hniServices = uorDao.getHniServicesByRole(userRole.getId().getOrgId(), userRole.getId().getRoleId());
			hniServicesList.addAll(hniServices);
		}
		
		return hniServicesList;
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public User register(User user, Long userType) {
		Organization organization = orgService.get(user.getOrganizationId());
		if (organization != null) {
			save(user);
			save(user, organization, createUserRole(organization, userType));
			return user;
		} else {
			return null;
		}
	}
	
	private Role createUserRole(Organization org, Long userType) {
		if (userType.equals(HNIRoles.NGO.getRole())) {
			Collection<UserOrganizationRole> ngoList = uorDao.getByRole(org, Role.get(HNIRoles.NGO_ADMIN.getRole()));
			if (ngoList.isEmpty()) {
				return Role.get(HNIRoles.NGO_ADMIN.getRole());
			} else {
				return Role.get(HNIRoles.NGO.getRole());
			}
		}else if(userType.equals(HNIRoles.VOLUNTEERS.getRole())){
			return Role.get(HNIRoles.VOLUNTEERS.getRole());
		}
		else if(userType.equals(HNIRoles.CLIENT.getRole())){
			return Role.get(HNIRoles.CLIENT.getRole());
		}
		else if(userType.equals(HNIRoles.ORGANIZATION.getRole())){
			return Role.get(HNIRoles.ORGANIZATION.getRole());
		}
		else if(userType.equals(HNIRoles.ADMINISTRATOR.getRole())){
			return Role.get(HNIRoles.ADMINISTRATOR.getRole());
		}
		return Role.get(HNIRoles.USER.getRole());
	}

	@Override
	public boolean getProfileStatus(User user) {
		
		return uorDao.getProfileStatus(user);
	}
}
