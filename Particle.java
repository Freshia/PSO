package pso;

public class Particle {
private Location location;
private Velocity velocity;
private double fitnessValue;

public Particle(){
	super();
}
public Particle(double fitnessValue,Velocity velocity, Location location){
	super();
	this.fitnessValue=fitnessValue;
	this.velocity=velocity;
	this.location=location;
}


public Location getLocation(){
	return location;
}
public void setLocation(Location location){
	this.location=location;
}
public Velocity getVelocity(){
	return velocity;
}
public void setVelocity(Velocity velocity){
	this.velocity= velocity;
}
public double getFitnessValue(){
	fitnessValue=ProblemSet.evaluate(location);
	return fitnessValue;
}
	
}
