package pso;

import java.util.Random;
import java.util.Vector;

//for 2D
public class PSOProcess implements PSOConstants{
	private Vector<Particle> swarm = new Vector <Particle>();
	private double []pBest = new double[SWARM_SIZE];
	private Vector<Location> pBestLocation = new Vector <Location>();
	private double gBest;
	private Location gBestLocation;
	private double [] fitnessValueList = new double[SWARM_SIZE];
	
	Random generator = new Random();
	public void execute(){
		initializeSwarm();
		updateFitnessList();
		
		for(int i=0; i<SWARM_SIZE;i++){
			pBest[i]=fitnessValueList[i];
			pBestLocation.add(swarm.get(i).getLocation());
		}
		int t=0;
		double w;
		double err=9999;
		//&&err>ProblemSet.ERR_TOLERANCE
		
		while(t<MAX_ITERATION){
			//UPDATE PBest
			for(int i=0;i<SWARM_SIZE;i++){
				if(fitnessValueList[i]<pBest[i]){
					pBest[i]=fitnessValueList[i];
					pBestLocation.set(i,swarm.get(i).getLocation());
				}
			}
			//update gBest
			int bestParticleIndex = PSOUtility.getMinPos(fitnessValueList);
			if(t==0|| fitnessValueList[bestParticleIndex]<gBest){
				gBest = fitnessValueList[bestParticleIndex];
				gBestLocation = swarm.get(bestParticleIndex).getLocation();
				
			}
			w= W_UPPERBOUND - (((double)t)/MAX_ITERATION)*(W_UPPERBOUND-W_LOWERBOUND);
			for(int i=0;i<SWARM_SIZE;i++){
				double r1=generator.nextDouble();
				double r2 = generator.nextDouble();
				
				Particle p = swarm.get(i);
				
				//update velocity
				double []newVel = new double[PROBLEM_DIMENSION];
				newVel[0]=(w*p.getVelocity().getPos()[0])+
						(r1*C1)*(pBestLocation.get(i).getLoc()[0]-p.getLocation().getLoc()[0])+
						(r2*C2)*(gBestLocation.getLoc()[0]-p.getLocation().getLoc()[0]);
				newVel[1]=(w*p.getVelocity().getPos()[1])+
				(r1*C1)*(pBestLocation.get(i).getLoc()[1]-p.getLocation().getLoc()[1])+
				(r2*C2)*(gBestLocation.getLoc()[1]-p.getLocation().getLoc()[1]);
				
				//added
				newVel[2]=(w*p.getVelocity().getPos()[2])+
						(r1*C1)*(pBestLocation.get(i).getLoc()[2]-p.getLocation().getLoc()[2])+
						(r2*C2)*(gBestLocation.getLoc()[2]-p.getLocation().getLoc()[2]);
				//Velocity vel= new Velocity(newVel);
				//p.setVelocity(vel);
				
				//update location and minimize bias
				double[] newLoc = new double[PROBLEM_DIMENSION];
				newLoc[0]=p.getLocation().getLoc()[0]+newVel[0];
				newLoc[1]=p.getLocation().getLoc()[1]+newVel[1];
				
				newLoc[2]=p.getLocation().getLoc()[2]+newVel[2];
				
				if(((p.getLocation().getLoc()[0]+newVel[0])<ProblemSet.LOC_X_LOW)){
					newLoc[0]=ProblemSet.LOC_X_LOW;
					newVel[0]=-(generator.nextDouble()*newVel[0]);
				}
				else if(((p.getLocation().getLoc()[0]+newVel[0])>ProblemSet.LOC_X_HIGH)){
					newLoc[0]=ProblemSet.LOC_X_HIGH;
					newVel[0]=-(generator.nextDouble()*newVel[0]);
				}
				
				if(((p.getLocation().getLoc()[1]+newVel[1])<ProblemSet.LOC_Y_LOW)){
					newLoc[1]=ProblemSet.LOC_Y_LOW;
					newVel[1]=-(generator.nextDouble()*newVel[1]);
				}
				else if(((p.getLocation().getLoc()[1]+newVel[1])>ProblemSet.LOC_Y_HIGH)){
					newLoc[1]=ProblemSet.LOC_Y_HIGH;
					newVel[1]=-(generator.nextDouble()*newVel[1]);
				}
				
				if(((p.getLocation().getLoc()[2]+newVel[2])<ProblemSet.LOC_Z_LOW)){
					newLoc[2]=ProblemSet.LOC_Z_LOW;
					newVel[2]=-(generator.nextDouble()*newVel[2]);
				}
				else if(((p.getLocation().getLoc()[2]+newVel[2])>ProblemSet.LOC_Z_HIGH)){
					newLoc[2]=ProblemSet.LOC_Z_HIGH;
					newVel[2]=-(generator.nextDouble()*newVel[2]);
				}
				Velocity vel= new Velocity(newVel);
				p.setVelocity(vel);
				
				Location loc= new Location(newLoc);
				p.setLocation(loc);
			}
			err=ProblemSet.evaluate(gBestLocation)-0; //minimizing the functions means its getting closer to 0
			
			System.out.println("ITERATION"+t+":");
			System.out.println("Best X"+gBestLocation.getLoc()[0]);
			System.out.println("Best Y"+ gBestLocation.getLoc()[1]);
			//added
			System.out.println("Best Z"+ gBestLocation.getLoc()[2]);
			System.out.println("Value"+ProblemSet.evaluate(gBestLocation));
				
			t++;
			updateFitnessList();
		
						
			}
		System.out.println("\nSolution found at iteration"+(t-1)+",the solution is:");
		System.out.println("Best X:"+gBestLocation.getLoc()[0]);
		System.out.println("Best Y:"+gBestLocation.getLoc()[1]);
		//added
		System.out.println("Best Z:"+gBestLocation.getLoc()[2]);
		System.out.println("Value"+ProblemSet.evaluate(gBestLocation));
	}
		public void initializeSwarm(){
			Particle p;
			for(int i=0;i<SWARM_SIZE;i++){
				p= new Particle();
				//randomize location inside a space defined in problem set
				double[]loc=new double[PROBLEM_DIMENSION];
				loc[0]=ProblemSet.LOC_X_LOW +generator.nextDouble()*(ProblemSet.LOC_X_HIGH-ProblemSet.LOC_X_LOW);
				loc[1]=ProblemSet.LOC_Y_LOW +generator.nextDouble()*(ProblemSet.LOC_Y_HIGH-ProblemSet.LOC_Y_LOW);
				//Added
				loc[2]=ProblemSet.LOC_Z_LOW +generator.nextDouble()*(ProblemSet.LOC_Z_HIGH-ProblemSet.LOC_Z_LOW);
				Location location = new Location(loc);
				
				//randomize velocity inside a space defined in problem set
				double[]vel=new double[PROBLEM_DIMENSION];
				vel[0]=ProblemSet.VEL_LOW +generator.nextDouble()*(ProblemSet.VEL_HIGH-ProblemSet.VEL_LOW);
				vel[1]=ProblemSet.VEL_LOW +generator.nextDouble()*(ProblemSet.VEL_HIGH-ProblemSet.VEL_LOW);
				//Added
				vel[2]=ProblemSet.VEL_LOW +generator.nextDouble()*(ProblemSet.VEL_HIGH-ProblemSet.VEL_LOW);
				Velocity velocity = new Velocity(vel);
				
				p.setLocation(location);
				p.setVelocity(velocity);
				swarm.add(p);
				
				
			}
		}
		 public void updateFitnessList(){
			for(int i=0;i<SWARM_SIZE;i++){
				fitnessValueList[i]=swarm.get(i).getFitnessValue();
			}
		}
	}


