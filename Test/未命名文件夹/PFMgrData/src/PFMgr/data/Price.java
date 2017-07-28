package PFMgr.data;

import java.io.Serializable;
import java.time.LocalDate;

public class Price implements Serializable {
	/**
	 * @param bid
	 * @param offer
	 * @param dt
	 * @param ccy
	 */
	public Price(double bid, double offer, LocalDate dt, String ccy) {
		super();
		this.bid = bid;
		this.offer = offer;
		this.dt = dt;
		this.ccy = ccy;
	}
	public double getBid() {
		return bid;
	}
	public void setBid(double bid) {
		this.bid = bid;
	}
	public double getOffer() {
		return offer;
	}
	public void setOffer(double offer) {
		this.offer = offer;
	}
	public LocalDate getDt() {
		return dt;
	}
	public void setDt(LocalDate dt) {
		this.dt = dt;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	private double bid;
	private double offer;
	private LocalDate dt;
	private String ccy;
}
