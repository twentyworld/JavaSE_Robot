package PFMgr.data;

import java.io.Serializable;

public class FRA extends Security implements Serializable {
	@Override
	public String toString() {
		return "FRA [start=" + start + ", duration=" + duration + ", rate=" + rate + "]";
	}
	/**
	 * @param start
	 * @param duration
	 * @param rate
	 */
	public FRA(String isin, int start, int duration, double rate) {
		super(isin);
		this.start = start;
		this.duration = duration;
		this.rate = rate;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	private int start;
	private int duration;
	private double rate;
}
