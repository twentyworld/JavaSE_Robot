package PFMgr.sessionbean;

import java.util.List;

import PFMgr.data.Portfolio;
import PFMgr.data.Position;

public interface PortfolioServicesBase {
	Portfolio createPortfolio(String name);
	void addPosition(Position position);
	List<Position> getPositions(String name);
	Portfolio removePosition(String name);
	String getName();
	void setName(String name);
}
