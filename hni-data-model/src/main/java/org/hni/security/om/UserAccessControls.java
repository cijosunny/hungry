package org.hni.security.om;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserAccessControls implements Serializable {

	private static final long serialVersionUID = 7635725951514871569L;

	private Set<String> permissions;
	private Set<String> roles;
	
	public UserAccessControls() {
		permissions = new HashSet<>();
		roles = new HashSet<>();
	}
	
	public Set<String> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
}
