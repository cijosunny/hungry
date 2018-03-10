package org.hni.order.om;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hni.common.om.Persistable;
import org.hni.provider.om.MenuItem;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the actual item being created in an Order.  It must be related
 * back to a MenuItem.
 * 
 * @author j2parke
 *
 */
@Entity
@Table(name = "order_items")
public class OrderItem implements Persistable, Serializable {

	private static final long serialVersionUID = 1497593633752929478L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	//@Column(name="order_id") private Long order_id;
	@Column(name="quantity") private Long quantity;
	@Column(name="amount") private Double amount;

	@ManyToOne
	@JoinColumn(name="menu_item_id", referencedColumnName = "id")
	private MenuItem menuItem;

	@ManyToOne
	@JoinColumn(name="order_id", referencedColumnName = "id")
	private Order order;
	
	public OrderItem() {}
	public OrderItem(Long id) {
		this.id = id; 
	}
	public OrderItem(Long quantity, Double amount, MenuItem menuItem) {
		this.quantity = quantity;
		this.amount = amount;
		this.menuItem = menuItem;
	}
	
	@Override
	public Long getId() {
		return this.id;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	@JsonIgnore
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
