package PFMgr.sessionbean;

import PFMgr.data.Position;
import PFMgr.data.Price;
import PFMgr.data.Security;

public interface PositionServicesBase {
	public Position createPosition(Security sec, int qty, Price price);
	public Position getPosition();
	public void setPosition(Position position);
}
