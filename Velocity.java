package pso;

public class Velocity {
	//store velocity in an array to accomodate multi dimensional problem
	private double [] vel;
	
public Velocity(double[] vel){
	super();
	this.vel= vel;
}
public double[] getPos(){
	return vel;
}
public void setPos(double[] vel){
	this.vel=vel;
}

}

