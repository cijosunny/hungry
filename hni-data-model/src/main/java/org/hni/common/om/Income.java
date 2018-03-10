package org.hni.common.om;

// Generated Apr 19, 2017 11:39:46 PM by Hibernate Tools 3.6.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Income generated by hbm2java
 */
@Entity
@Table(name = "income")
public class Income implements java.io.Serializable {

	private Integer id;
	private String incomeDesc;

	public Income() {
	}

	public Income(String incomeDesc) {
		this.incomeDesc = incomeDesc;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "income_desc", nullable = false, length = 100)
	public String getIncomeDesc() {
		return this.incomeDesc;
	}

	public void setIncomeDesc(String incomeDesc) {
		this.incomeDesc = incomeDesc;
	}

}
