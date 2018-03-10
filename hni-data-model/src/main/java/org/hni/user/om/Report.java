package org.hni.user.om;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

@Entity
@Table(name = "reports")
public class Report implements Persistable, Serializable {
	private static final long serialVersionUID = 7553475738921092329L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "report_path")
	private String reportPath;
	@Column(name = "role")
	private Long role;
	@Column(name = "label")
	private String label;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	public Long getRole() {
		return role;
	}
	public void setRole(Long role) {
		this.role = role;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	
}
