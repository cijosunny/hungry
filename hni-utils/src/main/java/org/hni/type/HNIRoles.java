/**
 * 
 */
package org.hni.type;

/**
 * @author Rahul
 *
 */
public enum HNIRoles {

	SUPER_ADMIN(1L),
	ADMINISTRATOR(2L),
	VOLUNTEERS(3L),
	CLIENT(4L),
	USER(5L),
	NGO_ADMIN(6L),
	NGO(7L),
	ORGANIZATION(8L);
	
	Long role;
	
	HNIRoles(Long role) {
		this.role = role;
	}
	public Long getRole() {
		return role;
	}
}
