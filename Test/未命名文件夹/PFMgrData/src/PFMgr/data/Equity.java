package PFMgr.data;

import java.io.Serializable;

public class Equity extends Security implements Serializable {


	@Override
	public String toString() {
		return "Equity [" + (symbol != null ? "symbol=" + symbol + ", " : "")
				+ (getIsin() != null ? "getIsin()=" + getIsin() + ", " : "")
				+ (getClass() != null ? "getClass()=" + getClass() + ", " : "") + "hashCode()=" + hashCode() + ", "
				+ (super.toString() != null ? "toString()=" + super.toString() : "") + "]";
	}

	/**
	 * @param symbol
	 */
	public Equity(String isin, String symbol) {
		super(isin);
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	private String symbol;
}
