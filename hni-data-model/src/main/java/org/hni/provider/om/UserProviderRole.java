package org.hni.provider.om;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hni.common.om.Persistable;
import org.hni.common.om.Role;
import org.hni.user.om.User;

/**
 * Provides the mapping between a user a provider where the user must play
 * a specific role to the Provider.  This data is generally used
 * by the security layer when determining the permissions a user has with 
 * the provider.  
 *
 */
@Entity
@Table(name = "user_provider_role")
public class UserProviderRole implements Persistable, Serializable {

	private static final long serialVersionUID = 2755700987714341746L;

	@EmbeddedId private UserProviderRolePK id;

	public UserProviderRole() {}
	public UserProviderRole(UserProviderRolePK id) {
		this.id = id;
	}
	public UserProviderRole(User user, Provider provider, Role role) {
		this(new UserProviderRolePK(user, provider, role));
	}
	@Override
	public Object getId() {
		return this.id;
	}

	public void setId(UserProviderRolePK id) {
		this.id = id;
	}
	
	
}
