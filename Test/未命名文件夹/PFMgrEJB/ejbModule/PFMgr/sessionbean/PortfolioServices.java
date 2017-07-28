package PFMgr.sessionbean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import PFMgr.data.Portfolio;
import PFMgr.data.Position;

/**
 * Session Bean implementation class PortfolioServices
 */
@Stateful
@Local(PortfolioServicesLocal.class)
@Remote(PortfolioServicesRemote.class)
public class PortfolioServices implements PortfolioServicesRemote, PortfolioServicesLocal {

	Portfolio portfolio;
	
    /**
     * Default constructor. 
     */
    public PortfolioServices() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public Portfolio createPortfolio(String name) {
		// TODO Auto-generated method stub
		portfolio = new Portfolio(name);
		return portfolio;
	}
	
	@Override
	public List<Position> getPositions(String name) {
		return portfolio.getPositions();
	}

	@Override
	public void addPosition(Position position) {
		portfolio.addPosition(position);
	}

	@Override
	public Portfolio removePosition(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

}
