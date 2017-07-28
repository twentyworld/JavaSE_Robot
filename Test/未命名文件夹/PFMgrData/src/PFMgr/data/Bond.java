package PFMgr.data;

import java.io.Serializable;
import java.time.LocalDate;

public class Bond extends Security implements Serializable {
	
	@Override
	public String toString() {
		return "Bond [" + (issuer != null ? "issuer=" + issuer + ", " : "") + "coupon=" + coupon + ", "
				+ (maturityDate != null ? "maturityDate=" + maturityDate + ", " : "")
				+ (getIsin() != null ? "getIsin()=" + getIsin() + ", " : "")
				+ (getClass() != null ? "getClass()=" + getClass() + ", " : "") + "hashCode()=" + hashCode() + ", "
				+ (super.toString() != null ? "toString()=" + super.toString() : "") + "]";
	}
	/**
	 * @param issuer
	 * @param coupon
	 * @param maturityDate
	 */
	public Bond(String isin, String issuer, double coupon, LocalDate maturityDate) {
		super(isin);
		this.issuer = issuer;
		this.coupon = coupon;
		this.maturityDate = maturityDate;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public double getCoupon() {
		return coupon;
	}
	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}
	public LocalDate getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}
	private String issuer;
	private double coupon;
	private LocalDate maturityDate;
}
