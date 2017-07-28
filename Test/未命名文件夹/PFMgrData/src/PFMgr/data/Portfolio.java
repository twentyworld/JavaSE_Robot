package PFMgr.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Portfolio implements Serializable {
	/**
	 * @param name
	 * @param positions
	 */
	public Portfolio(String name) {
		super();
		this.name = name;
		this.positions = new ArrayList<Position>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Position> getPositions() {
		return positions;
	}
	public void addPosition(Position position) {
		this.positions.add(position);
	}
	private String name;
	private List<Position> positions;
}
