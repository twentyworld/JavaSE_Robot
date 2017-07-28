package PFMgr.data;

import java.io.Serializable;

public abstract class Security implements Serializable {
	/**
	 * @param isin
	 */
	public Security(String isin) {
		super();
		this.isin = isin;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	private String isin;
}
