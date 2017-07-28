package PFMgr.sessionbean;

import java.time.LocalDate;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import PFMgr.data.Portfolio;
import PFMgr.data.Position;
import PFMgr.data.Price;
import PFMgr.data.Security;

/**
 * Session Bean implementation class PositionServices
 */
@Stateful
@Local(PositionServicesLocal.class)
@Remote(PositionServicesRemote.class)
public class PositionServices implements PositionServicesRemote, PositionServicesLocal {

	private Position position;
	
    /**
     * Default constructor. 
     */
    public PositionServices() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public Position createPosition(Security sec, int qty, Price price) {
		position = new Position(sec, qty, price);
		return position;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}

}
