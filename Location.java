package pso;

public class Location {
//store the Location in an array to accomodate multi-dimansinal problem space
	private double[]loc;
	public Location(double[]loc){
		super();
		this.loc=loc;
	}
	public double[]getLoc(){
		return loc;
	}
	public void setLoc(double[]Loc){
		this.loc=loc;
	}
}
