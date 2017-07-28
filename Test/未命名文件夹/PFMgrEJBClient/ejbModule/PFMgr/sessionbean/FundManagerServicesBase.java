package PFMgr.sessionbean;

import PFMgr.data.FundManager;
import PFMgr.data.Portfolio;

public interface FundManagerServicesBase {
	FundManager createFundManager(String fName, String lName, String email, String tel);
	void addPortfolio(Portfolio portfolio);
	Portfolio getPortfolio(String name);
	Portfolio removePortfolio(String name);
	
	public String getFirstName(); 
	public void setFirstName(String firstName);
	public String getLastName();
	public void setLastName(String lastName);
	public String getEmail();
	public void setEmail(String email);
	public String getTelephone();
	public void setTelephone(String telephone);
}
