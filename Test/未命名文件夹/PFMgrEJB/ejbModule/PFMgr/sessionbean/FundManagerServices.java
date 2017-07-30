package PFMgr.sessionbean;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import PFMgr.data.FundManager;
import PFMgr.data.Portfolio;

import java.util.ArrayList;

/**
 * Session Bean implementation class FundManagerServices
 */
@Stateful
@Local(FundManagerServicesLocal.class)
@Remote(FundManagerServicesRemote.class)
public class FundManagerServices implements FundManagerServicesRemote, FundManagerServicesLocal {

	FundManager fundManager;
    /**
     * Default constructor. 
     */
    public FundManagerServices() {
    }
    
    
    public FundManager createFundManager(String fName, String lName, String email, String tel) {
    		this.fundManager = new FundManager(fName, lName, email, tel);
    		return fundManager;
    }
    
    public void addPortfolio(Portfolio portfolio) {
    		fundManager.addPortfolio(portfolio);
    }
    
    public Portfolio getPortfolio(String name) {
    	
    		ArrayList<Portfolio> portfolios = (ArrayList<Portfolio>) fundManager.getPortfolios();
    		
    		for (Portfolio p : portfolios) {
    			if (p.getName().equals(name))
    				return p;
    		}
    		
    		return null;
    }
    
    public Portfolio removePortfolio(String name) {
    	
		ArrayList<Portfolio> portfolios = (ArrayList<Portfolio>) fundManager.getPortfolios();
		
		int idx = 0;
		for (Portfolio p : portfolios) {
			idx++;
			if (p.getName().equals(name)) {
				portfolios.remove(idx);
				return p;
			}		
		}
		
		return null;
    }
    
	public String getFirstName() {
		return this.fundManager.getFirstName();
	}
	
	public void setFirstName(String firstName) {
		this.fundManager.setFirstName(firstName); 
	}
	
	public String getLastName() {
		return this.fundManager.getLastName();
	}
	
	public void setLastName(String lastName) {
		this.fundManager.setLastName(lastName);
	}
	
	public String getEmail() {
		return this.fundManager.getEmail();
	}
	public void setEmail(String email) {
		this.fundManager.setEmail(email);
	}
	public String getTelephone() {
		return this.fundManager.getTelephone();
	}
	public void setTelephone(String telephone) {
		this.fundManager.setTelephone(telephone);
	}

}
