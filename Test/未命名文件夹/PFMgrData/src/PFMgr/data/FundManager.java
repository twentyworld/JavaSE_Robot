package PFMgr.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FundManager implements Serializable {
	/**
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param telephone
	 * @param portfolios
	 */
	public FundManager(String firstName, String lastName, String email, String telephone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.telephone = telephone;
		this.portfolios = new ArrayList<Portfolio>();
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public List<Portfolio> getPortfolios() {
		return portfolios;
	}
	public void addPortfolio(Portfolio portfolio) {
		this.portfolios.add(portfolio);
	}
	private String firstName;
	private String lastName;
	private String email;
	private String telephone;
	private List<Portfolio> portfolios;
}
