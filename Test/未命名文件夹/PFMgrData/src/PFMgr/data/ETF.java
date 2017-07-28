package PFMgr.data;

import java.io.Serializable;

public class ETF extends Security implements Serializable {
	@Override
	public String toString() {
		return "ETF [" + (symbol != null ? "symbol=" + symbol : "") + "]";
	}

	/**
	 * @param symbol
	 */
	public ETF(String isin, String symbol) {
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
