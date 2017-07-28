package PFMgr.data;

import java.io.Serializable;

public class Position implements Serializable {
	/**
	 * @param security
	 * @param quantity
	 * @param price
	 */
	public Position(Security security, int quantity, Price price) {
		super();
		this.security = security;
		this.quantity = quantity;
		this.price = price;
	}
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security security) {
		this.security = security;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	private Security security;
	private int quantity;
	private Price price;
}
